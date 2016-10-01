package com.roby.demo.zk.curator.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

public class ExampleClientThatNoLocks {
    private final InterProcessSemaphoreMutex ulock;//不可重入锁
    private final FakeLimitedResource resource;
    private final String clientName;

    public ExampleClientThatNoLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        ulock = new InterProcessSemaphoreMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
       if (!ulock.acquire(time, unit)) {
            throw new IllegalStateException(clientName + " could not acquire the lock");
        }
        if (!ulock.acquire(time, unit)) {
            throw new IllegalStateException(clientName + " could not acquire the lock again");
        }
        System.out.println(clientName + " has the lock again");
        try {
            System.out.println(clientName + " has the lock");
            resource.use(); //access resource exclusively
        } finally {
            System.out.println(clientName + " releasing the lock");
            ulock.release(); // always release the lock in a finally block
            ulock.release(); // 不可重入锁 需要调用2次!注意我们也需要调用release两次。这和JDK的ReentrantLock用法一致。如果少调用一次release，则此线程依然拥有锁。 
        }
    }
}