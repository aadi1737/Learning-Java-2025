package QuestionOnJava;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

class  Bank{
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rlock = lock.readLock();
    private final Lock wlock = lock.writeLock();
    private static int balance=1000;

    public void withdraw(int n) {
        try {
            wlock.lock();
            System.out.println(Thread.currentThread().getName() + " wants to withdraw money!!");
            if (balance < n) {
                System.out.println("Insufficient balance!");
            } else {
                balance -= n;
                System.out.println(Thread.currentThread().getName() + "Successfully withdrawn!Remaining Balance:" + balance);
            }
        }
        finally {
            wlock.unlock();
        }
    }

    public void checkbalance() {
        try {
            rlock.lock();
            System.out.println(Thread.currentThread().getName() + " wants to check Balance:" + balance);
        }
        finally {
            rlock.unlock();
        }
    }
}

public class RWQ1 {
    public static void main(String[] args) {
        Bank bank= new Bank();
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                bank.checkbalance();
                bank.withdraw(100);
            }
        };
        Runnable t2 = new Runnable() {
            @Override
            public void run() {
                bank.checkbalance();
                bank.withdraw(100);
            }
        };
        Thread th1=new Thread(t1,"Ajay");
        Thread th2 = new Thread(t2,"Aadi");
        th2.start();
        th1.start();
    }
}
