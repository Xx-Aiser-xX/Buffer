import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BoundedBufferTest {
    public static void main(String[] args) throws InterruptedException {

        BoundedBuffer boundedBuffer = new BoundedBuffer(10);
        final int countThread = 4;

        Random random = new Random();
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(countThread);

        Thread[] threads = new Thread[countThread];
        int count = Math.abs(random.nextInt()) % 3 + 100;

        for (int i = 0; i < countThread; i++) {
            final int threadNumber = i + 1;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        for(int j = 0; j < count; j++){
                            int put = random.nextInt() % 10_000;
                            if(threadNumber % 2 == 0) {
                                System.out.println("положил: " + put);
                                boundedBuffer.put(put);
                            }
                            else
                                System.out.println("взяли: " + boundedBuffer.take());
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    finishSignal.countDown();
                }
            });
            threads[i].start();
        }
        startSignal.countDown();
        finishSignal.await();
    }
}
