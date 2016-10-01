package com.roby.demo.zk.curator.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

public class ExampleClientThatRWLocks {
	private  InterProcessReadWriteLock rwLock  ;
    private final InterProcessMutex readLock;
    private final InterProcessMutex writeLock;
    private final FakeLimitedResource resource;
    private final String clientName;

    public ExampleClientThatRWLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        rwLock = new InterProcessReadWriteLock(client, lockPath);
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
    	if (!readLock.acquire(time, unit)) {
            throw new IllegalStateException(clientName + " could not acquire the lock");
        }
        System.out.println(clientName + " has the lock again");
        try {
            System.out.println(clientName + " has the lock");
            resource.use(); //access resource exclusively
        } finally {
            System.out.println(clientName + " releasing the lock");
            readLock.release();
        }
    }
}