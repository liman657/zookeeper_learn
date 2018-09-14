package com.learn.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * author : liman
 * create time : 2018/9/6
 * QQ:657271181
 * e-mail:liman65727@sina.com
 * <p>
 * 调用zookeeper api
 */
public class ZookeeperClient {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String connectionStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static final int sessionTime = 5000;

    public static void main(String[] args) {
        ZooKeeper zookeeper = getZookeeper();
        createNode(zookeeper);
        isNodeExists(zookeeper);
        update(zookeeper);
        deleteNode(zookeeper);
    }

    /**
     * 创建zookeeper的连接
     *
     * @return
     */
    public static ZooKeeper getZookeeper() {

        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectionStr, sessionTime, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        System.out.println("zookeeper connection established");
                        countDownLatch.countDown();
                    } else if (event.getState().equals(Event.KeeperState.Disconnected)) {
                        System.out.println("zookeeper disconnected");
                    } else if (event.getState().equals(Event.KeeperState.Expired)) {
                        System.out.println("zookeeper connection expired");
                    } else if (event.getState().equals(Event.KeeperState.AuthFailed)) {
                        System.out.println("zookeeper connection authfailed");
                    } else {
                        System.out.println("zookeeper state" + event.getState());
                    }
                }
            });

            System.out.println("zookeeper = " + zooKeeper.getState());
            try {
                countDownLatch.await(5000, TimeUnit.MILLISECONDS);
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

    /**
     * 创建节点
     *
     * @param zooKeeper
     */
    public static void createNode(ZooKeeper zooKeeper) {
        try {
            zooKeeper.create("/test", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("/test 节点创建成功");
        } catch (Exception e) {
            System.out.println("节点创建失败");
            e.printStackTrace();
        }
    }

    /**
     * 判断节点是否存在
     *
     * @param zooKeeper
     */
    public static void isNodeExists(ZooKeeper zooKeeper) {
        String path = "/test";
        try {
            //这里将watch设置成false
            Stat stat = zooKeeper.exists(path, false);
            if (stat == null) {
                System.out.println("节点 path = " + path + "不存在");
            } else {
                System.out.println("节点 path = " + path + " 存在，stat=" + stat);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改节点数据
     *
     * @param zooKeeper
     */
    public static void update(ZooKeeper zooKeeper) {
        try {
            zooKeeper.setData("/test", "update-version2".getBytes(), 0);
            String data = new String(zooKeeper.getData("/test", false, null), "utf-8");

            System.out.println("/test节点的值为"+data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点数据
     * @param zooKeeper
     */
    public static void deleteNode(ZooKeeper zooKeeper){
        try {
            Stat stat = zooKeeper.exists("/test",false);
            zooKeeper.delete("/test",stat.getVersion());
            System.out.println("删除/test成功");
        } catch (Exception e) {
            System.out.println("删除/test节点失败");
            e.printStackTrace();
        }
    }

    /**
     * 连接需要权限认证的节点
     */
    public static void authNode(){

    }

}
