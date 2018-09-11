package com.learn.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * author : liman
 * create time : 2018/9/6
 * QQ:657271181
 * e-mail:liman65727@sina.com
 *
 * 调用zookeeper api
 */
public class ZookeeperClient {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String connectionStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static final int sessionTime = 5000;

    public static void main(String[] args) {
        ZooKeeper zookeeper = getZookeeper();

    }

    /**
     * 创建zookeeper的连接
     * @return
     */
    public static ZooKeeper getZookeeper(){

        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectionStr, sessionTime, new Watcher() {
                public void process(WatchedEvent event) {
                    if(event.getState() == Event.KeeperState.SyncConnected){
                        System.out.println("zookeeper connection established");
                        countDownLatch.countDown();
                    }else if(event.getState().equals(Event.KeeperState.Disconnected)){
                        System.out.println("zookeeper disconnected");
                    }else if(event.getState().equals(Event.KeeperState.Expired)){
                        System.out.println("zookeeper connection expired");
                    }else if(event.getState().equals(Event.KeeperState.AuthFailed)){
                        System.out.println("zookeeper connection authfailed");
                    }else{
                        System.out.println("zookeeper state"+event.getState());
                    }
                }
            });

            System.out.println("zookeeper = "+zooKeeper.getState());
            try {
                countDownLatch.await(5000,TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("zookeeper connection ok, lock release ......");

            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void createNode(ZooKeeper zooKeeper){

    }

}
