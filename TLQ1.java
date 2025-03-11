package QuestionOnJava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class RSRTC{
    private final Lock lock = new ReentrantLock();
    private static int seats=10;
    public void bookticket(int n){
        lock.lock();
        try{
            if(seats>n) {
                System.out.println(Thread.currentThread().getName() + " booked " + n + " Seats!");
                seats -= n;
                System.out.println("Remaining seats:" + seats);
            }
            else
                System.out.println(Thread.currentThread().getName()+" Insufficient Seats!");
        }
        finally {
            lock.unlock();
        }
    }

}

public class TLQ1 extends Thread {
    public TLQ1(Runnable r){
        super(r);
    }
    public static void main(String[] args){
        RSRTC obj = new RSRTC();
        TLQ1 t1= new TLQ1(()-> obj.bookticket(5));
        TLQ1 t2= new TLQ1(()-> obj.bookticket(8));
        t1.start();
        t2.start();
    }
}
