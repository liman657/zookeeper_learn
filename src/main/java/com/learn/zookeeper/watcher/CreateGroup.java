package com.learn.zookeeper.watcher;

import com.learn.zookeeper.Configuration.ConnectionInfo;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *      创建zookeeper节点
 */
public class CreateGroup implements Watcher {

    private static final int SESSION_TIMEOUT = 5000;

    private ZooKeeper zooKeeper;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }

    private void close() throws InterruptedException {
        zooKeeper.close();
    }

    private void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/"+groupName;
        if(zooKeeper.exists(path,false) == null){
            /**
             * 创建znode的参数
             * 1、路径
             * 2、字节数组，暂时置空
             * 3、ACL 访问控制列表，权限控制相关的
             * 4、指定znode的类型，临时的和持久的
             */
            String createPath = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Created:"+createPath);
        }

    }

    private void connection() throws InterruptedException, IOException {
        /**
         * 创建一个zookeeper实例，这个实例第一参数是地址，第二个参数是设置的超时时间，第三个参数是指定watcher对象
         * Watcher对象用于接受来自zookeeper的回调，用户获得各种事件通知。
         *
         * 在与zookeeper中心建立连接有一个过程，这个过程需要时间，因此用countDownLatch确保已经与zookeeper中心连接成功
         *
         *
         */
        zooKeeper = new ZooKeeper(ConnectionInfo.connectionStr,SESSION_TIMEOUT,this);
        connectedSignal.await();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String groupName = "test";
        CreateGroup createGroup = new CreateGroup();
        createGroup.connection();
        createGroup.create(groupName);
        createGroup.close();
    }

}
