package com.learn.zookeeper.watcher;

import com.learn.zookeeper.Configuration.ConnectionInfo;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *      列出所有zookeeper节点
 */
public class ListGroup extends ConnectionWatcher{

    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/"+groupName;
        List<String> children = zooKeeper.getChildren(path,false);
        if(children.isEmpty()){
            System.out.printf("no member in group %s\n",groupName);
            System.exit(1);
        }
        for(String child:children){
            System.out.println(child);
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ListGroup listGroup = new ListGroup();

        listGroup.connection(ConnectionInfo.connectionStr);
        listGroup.list("");
        listGroup.close();
    }

}
