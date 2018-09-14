package com.learn.zookeeper.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *      实现自己的watcher监听机制
 */
public class ConnectionWatcher implements Watcher {

    //超时时间设置
    private static final int SESSION_TIMEOUT=5000;

    protected ZooKeeper zooKeeper;

    CountDownLatch connectSignal = new CountDownLatch(1);

    public void connection (String host) throws IOException {
        zooKeeper = new ZooKeeper(host,SESSION_TIMEOUT,this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectSignal.countDown();
        }
    }

    public void close()throws InterruptedException{
        zooKeeper.close();
    }
}
