package com.eventxorizons;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadFileTask implements Runnable {


    private DownloadStatus status;
    private Lock lock = new ReentrantLock();


// For Race COndition
//    public DownloadFileTask() {
//        this.status = new DownloadStatus();
//    }

    public DownloadFileTask(DownloadStatus status){
        this.status = status;
    }

    @Override
    public void run() {
        System.out.println("Downloading File..." + Thread.currentThread().getName());

//        try {
//            Thread.sleep(5000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        for(var i = 0; i < 1_000_000; i++ ){
            if(Thread.currentThread().isInterrupted()) return ;
            //System.out.println("Downloading byte "+ i);
            status.incrementTotalBytes();
        }
        status.done();
        synchronized (status) {status.notifyAll();}
        System.out.println("Download Complete..." + Thread.currentThread().getName());
    }

    public DownloadStatus getStatus() {
        return status;
    }



}
