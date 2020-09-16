package com.eventxorizons;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

//
//        // Concurrency -> multiple task at the same time
//        // Thread -> sequence of instructions , executes code
//
//        System.out.println(Thread.activeCount());
//        System.out.println(Runtime.getRuntime().availableProcessors());
//
//
//        System.out.println("--------------");
//        // Exploit Parallel hardware
//        //Create and Start Thread
//
//        System.out.println(Thread.currentThread().getName());
//
//        for(int i = 0; i < 10; i++ ) {
//            Thread thread = new Thread(new DownloadFileTask());
//            thread.start();
//        }
//
//        System.out.println("--------------");
//
//
//        // Sleep Thread / Join Thread
//
//        Thread thread2 = new Thread(new DownloadFileTask());
//        thread2.start();
//
//        try {
//            thread2.join();
//            // wait for the completion of another thread
//            // block current thread until download thread has finished
//
//        } catch (InterruptedException e ) {
//           e.printStackTrace();
//        }
//        System.out.println("File is ready to be scanned");


//        System.out.println("--------------");
//
//
//        Thread thread3 = new Thread(new DownloadFileTask());
//        thread3.start();
//
//        try {
//            thread3.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread3.interrupt();
//
//
//        System.out.println("--------------");
//
//        // Race Condition
//
//        List<Thread> threads = new ArrayList<>();
//        List<DownloadFileTask> tasks = new ArrayList<>();
//
//
//        for(var i = 0; i < 10; i++ ){
//            var task  = new DownloadFileTask();
//            tasks.add(task);
//            var thread = new Thread(task);
//            thread.start();
//            threads.add(thread);
//        }
//
//        for(var thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//           var totalBytes = tasks.stream()
//                    .map(t -> t.getStatus().getTotalBytes())
//                    .reduce(Integer::sum);
//            System.out.println(totalBytes   );
//
//        }

//        System.out.println("--------------");

        // Locks

//        var status = new DownloadStatus();
//        List<Thread> threads = new ArrayList<>();
//
//        for(var i =0; i < 10; i++ ){
//
//            var thread = new Thread(new DownloadFileTask(status));
//            thread.start();
//            threads.add(thread);
//
//
//        }
//
//        for(var thread : threads)
//            try {
//                thread.join();
//            }
//        catch (InterruptedException e) {
//                e.printStackTrace();
//        }
//
//        System.out.println(status.getTotalBytes());
//
//
//        System.out.println("------");




        var status = new DownloadStatus();

        var thread1 = new Thread(new DownloadFileTask(status));
//        var thread2 = new Thread(() -> {
//
//            while(!status.isDone()){
//                System.out.println(status.getTotalBytes());
//            }
//        });


        var thread2 = new Thread(() -> {

            while(!status.isDone()){
                synchronized (status) {
                    try {
                        status.wait();
                        // put thread to sleep until another thread wakes it up
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } // end synchronized
            }
            System.out.println(status.getTotalBytes());
        });

        thread1.start();
        thread2.start();


        Collection<Integer> colleciton =
                Collections.synchronizedCollection(new ArrayList<>());
        //wraps a regular colection but makes it synced

        var newthread1 = new Thread(() -> {
            colleciton.addAll(Arrays.asList(1,2,3));
        });
        var newthread2 = new Thread(() -> {
            colleciton.addAll(Arrays.asList(4,5,6));
        });

        newthread1.start();
        newthread2.start();

        try {
            newthread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            newthread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(colleciton);


        Map<Integer, String> map = new ConcurrentHashMap<>();


        map.put(1, "a");
        map.get(1);
        map.remove(1);
        map.put(2, "b");



    }
}
