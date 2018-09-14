package com.learn.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * author:liman
 * mobile:15528212893
 * email:657271181@qq.com
 * create-time:2018/9/10
 * comment:
 *
 * 客户端的监听对象
 */
public class ZooKeeperWatcher implements Watcher {
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeCreated){
            System.out.println("监听到："+event.getPath()+"被创建了");
        }else if(event.getType() == Event.EventType.NodeDeleted){
            System.out.println("监听到："+event.getPath()+"被删除了");
        }else if(event.getType() == Event.EventType.NodeChildrenChanged){
            System.out.println("监听到："+event.getPath()+"子节点被修改了");
        }else if(event.getType() == Event.EventType.NodeDataChanged){
            System.out.println("监听到："+event.getPath()+"数据被修改了");
        }else if(event.getType() == Event.EventType.None){
            System.out.println("监听到连接事件");
        }
    }
}
