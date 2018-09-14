package com.learn.zookeeper.watcher;

import com.learn.zookeeper.Configuration.ConnectionInfo;

import java.io.IOException;
import java.util.List;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 */
public class DeleteGroup extends ConnectionWatcher{
    public void delete(String groupName){
        String path = "/"+groupName;
        List<String> children;
        try{
            children = zooKeeper.getChildren(path,false);
            for(String child:children){
                zooKeeper.delete(path+"/"+child,-1);
            }
            zooKeeper.delete(path,-1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connection(ConnectionInfo.connectionStr);
        deleteGroup.delete("test");
        deleteGroup.close();
    }
}
