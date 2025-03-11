package QuestionOnJava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ecommerce{
    private final Lock lock = new ReentrantLock();
    private static int price=100;
    public void checkprice(){
        System.out.println(Thread.currentThread().getName()+" Checked Price!(Price:"+price+")");
    }

    public void buy(){
        lock.lock();
        try{
            price-=10;
            System.out.println(Thread.currentThread().getName()+" Is buying Product!After Buying Price:"+price);
        }
        finally {
            lock.unlock();
        }
    }

}
public class TLQ2 extends Thread{
    public TLQ2(Runnable r){
        super(r);
    }
    public static void main(String[] args) {
        Ecommerce obj = new Ecommerce();
        Runnable taskr = ()->obj.checkprice();
        Runnable taskw = ()->obj.buy();
        Thread t1 = new Thread(taskr,"101");
        Thread t2 = new Thread(taskr,"102");
        Thread t3 = new Thread(taskw,"103");
        t1.start();
        t3.start();
        t2.start();
    }
}
