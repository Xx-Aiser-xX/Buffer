
class BankAccount {
    private int balance = 1_000_000;

    public void deposit(int amount) {
        this.balance += amount;
        // TODO: Реализуйте логику внесения денег на счет
    }
    public void withdraw(int amount) {
        if(this.balance - amount >= 0)
            this.balance -= amount;
        // TODO: Реализуйте логику снятия денег со счета
    }
    public synchronized int getAndSetBalance(boolean number,boolean prov, int amount) {
        if(number)
            return this.balance;
        else if(prov)
            deposit(amount);
        else
            withdraw(amount);
        return -1;
    }
}
