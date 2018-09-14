package com.learn.zookeeper.watcher;

import com.learn.zookeeper.Configuration.ConnectionInfo;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 */
public class JoinGroup extends ConnectionWatcher{

    public void join(String groupName,String memberName) throws KeeperException, InterruptedException {
        String path = "/"+groupName+"/"+memberName;
        String createdPath = zooKeeper.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Created:"+createdPath);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String groupName = "test";
        String memberName = "members";

        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connection(ConnectionInfo.connectionStr);
        joinGroup.join(groupName,memberName);

        Thread.sleep(Long.MAX_VALUE);
    }

}
