import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread implements Runnable {

    private java.lang.Thread t;
    private String output;
    private JTextArea textArea;

    Lock lock = new ReentrantLock();
    Condition cond = lock.newCondition();

    private JButton button;
    boolean toLock = false;
    int r;

    public void setLock(boolean toLock) {

        this.toLock = toLock;

        lock.lock();

        if (toLock) {
            button.setText("GO");
        } else {
            button.setText("SUSP");
        }

        cond.signalAll();

        lock.unlock();
    }


    @Override
    public void run() throws IllegalMonitorStateException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        while (true) {
            lock.lock();
            try {
                while (!toLock)
                    cond.await();


            } catch (Exception e) {
                e.printStackTrace();
            }


            r = (int) (Math.random() * (1500 - 500)) + 500;

            output = dateFormat.format(new Date().getTime()) + "\n";

            textArea.append(output);


            try {
                t.sleep(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public MyThread(JTextArea textArea, JButton button) {

        this.button = button;
        this.textArea = textArea;
        t = new java.lang.Thread(this);
        t.start();
    }
}
