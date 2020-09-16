package com.eventxorizons;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadStatus {
        // Atomic -> implementing counters
        // LongAdder, DoubleAdder -> multiple threads updating a value frequently prefer this
//    private AtomicInteger totalBytes = new AtomicInteger();
    private LongAdder totalBytes = new LongAdder();
    private int totalFiles;
    private Lock lock = new ReentrantLock();
    private Object totalBytesLock = new Object();
    private Object totalFilesLock = new Object();
    private volatile boolean isDone;
    // volatile always read it from the main memory
    // , unstable ,
    // changes are visible
    // across threads

     public int getTotalBytes() {
         // Atomic
//        return totalBytes.get();
         return totalBytes.intValue();

    }

    public void incrementTotalBytes(){
//        lock.lock();
//        try {
//            totalBytes++;
//
//        } finally {
//            lock.unlock();
//        }
//        //thread enters -> locked -> other thread have to wait for this thread to come out
//        synchronized (totalBytesLock) {
//            totalBytes++.;
//        }
//        totalBytes.incrementAndGet();
        totalBytes.increment();
    }

    public void incrementTotalFiles() {
        synchronized (totalFilesLock) {
            totalFiles++;
        }
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public boolean isDone() {
        return isDone;
    }

    public void done() {
        isDone = true;
    }


}
