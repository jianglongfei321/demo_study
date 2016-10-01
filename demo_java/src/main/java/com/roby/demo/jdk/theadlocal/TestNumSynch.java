package com.roby.demo.jdk.theadlocal;


public class TestNumSynch {  
    // ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值  
    private volatile static int seqNum = 0;
  
    // ②获取下一个序列值  
    public synchronized int  getNextNum() {  
        return ++seqNum;
    }  
  
    public static void main(String[] args) {  
        TestNumSynch sn = new TestNumSynch();  
        // ③ 3个线程共享sn，各自产生序列号  
        TestClient t1 = new TestClient(sn);  
        TestClient t2 = new TestClient(sn);  
        TestClient t3 = new TestClient(sn);  
        t1.start();  
        t2.start();  
        t3.start();  
    }  
  
    private static class TestClient extends Thread {  
        private TestNumSynch sn;  
  
        public TestClient(TestNumSynch sn) {  
            this.sn = sn;  
        }  
  
        public void run() {  
            for (int i = 0; i < 3; i++) {  
                // ④每个线程打出3个序列值  
                System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["  
                         + sn.getNextNum() + "]");  
            }  
        }  
    }  
}  