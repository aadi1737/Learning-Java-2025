package QuestionOnJava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SbcStack{
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rlock = lock.readLock();
    private final Lock wlock = lock.writeLock();
    private static int price=108;
    public void checkprice(){
        rlock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" Wants to check Price........");
            System.out.println("Price of SBC Stock is:"+price);
        }finally {
            rlock.unlock();
        }
    }

    public void buystock(int n){
        wlock.lock();
        try {
            price-=n%10;
            System.out.println(Thread.currentThread().getName()+" Bought "+n+" Stock!");
            System.out.println("After Buying SBC Price:"+price);
        }
        finally {
            wlock.unlock();
        }
    }
}

public class RWQ2 {
    public static void main(String[] args) {
        SbcStack stock = new SbcStack();
        Runnable rt = new Runnable() {
            @Override
            public void run() {
                stock.checkprice();
            }
        }        ;

        Runnable wt = new Runnable() {
            @Override
            public void run() {
                stock.checkprice();
                stock.buystock(5);
                stock.checkprice();
            }
        };
        Thread t1 = new Thread(rt);
        Thread t2 = new Thread(wt);
        Thread t3 = new Thread(rt);
        t1.start();
        t2.start();
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        t3.start();
    }
}
