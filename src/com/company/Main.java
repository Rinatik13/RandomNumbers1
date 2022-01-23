package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Randoms_num randoms_num = new Randoms_num();
        AddNumbers addNumbers = new AddNumbers(randoms_num);
        DeteleNum deteleNum = new DeteleNum(randoms_num);
        Thread thread1 = new Thread(addNumbers);
        thread1.setPriority(Thread.MAX_PRIORITY);
        Thread thread2 = new Thread(deteleNum);
        thread1.start();
        thread2.start();

    }

    static class Randoms_num{
        List<Integer> list = new ArrayList<>(1);

        public synchronized void getNum() throws InterruptedException {
            while (list.isEmpty()){
                int result = (int) (1 + (Math.random()*1000));
                list.add(result);
                wait();
                notify();
            }

        }
        public synchronized void deletNum() throws InterruptedException {
            while (!list.isEmpty()) {
                System.out.println(list.get(0));
                list.remove(0);
                notify();
            }
            wait();
        }
    }

    static class AddNumbers implements Runnable{
        Randoms_num randoms_num;

        public AddNumbers(Randoms_num randoms_num) {
            this.randoms_num = randoms_num;
        }

        @Override
        public void run() {
            try {while (true) {
                randoms_num.getNum();
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class DeteleNum implements Runnable {
        Randoms_num randoms_num;

        public DeteleNum(Randoms_num randoms_num) {
            this.randoms_num = randoms_num;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    randoms_num.deletNum();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

