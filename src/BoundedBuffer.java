class BoundedBuffer<T> {
    private final T[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int size) {
        buffer = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        if (count == buffer.length)
            wait();
        buffer[in] = item;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (count == 0)
            wait();
        T temp = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return temp;
    }
}