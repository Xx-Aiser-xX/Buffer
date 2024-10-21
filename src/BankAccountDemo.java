import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BankAccountDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        Random random = new Random();
        final int bankAccountCount = 4;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(bankAccountCount);

        Thread[] threads = new Thread[bankAccountCount];
        final int res = Math.abs(random.nextInt()) % 100_000;
        for (int i = 0; i < bankAccountCount; i++) {
            final int threadNumber = i + 1;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        for (int j = 0; j < res; j++) {
                            if (j % 2 != 0)
                                account.getAndSetBalance(false, true, 9999);
                            else
                                account.getAndSetBalance(false, false, 9888);
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
        // TODO: Создайте и запустите потоки, выполняющие случайные операциисо счетом
        // TODO: Дождитесь завершения всех операций
        int testBalance = 1_000_000;
        for (int i = 0; i < bankAccountCount; i++){
            for (int j = 0; j < res; j++) {
                if (j % 2 != 0)
                    testBalance += 9999;
                else if (testBalance - 9888 >= 0)
                    testBalance -= 9888;

            }
        }
        System.out.println("Ожидаемный баланс: " + testBalance);
        System.out.println("Финальный баланс: " + account.getAndSetBalance(true,true,0));
    }
}