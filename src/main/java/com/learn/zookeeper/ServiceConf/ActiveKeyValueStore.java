package com.learn.zookeeper.ServiceConf;

import com.learn.zookeeper.watcher.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.omg.PortableServer.POAPackage.WrongAdapterHelper;

import java.nio.charset.Charset;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *      配置服务实例
 */
public class ActiveKeyValueStore extends ConnectionWatcher {
    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 将一个关键字写到zookeeper
     * 如果为空的话就创建，如果不为空就修改数据
     * @param path
     * @param value
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void write(String path,String value) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path,false);
        if(stat==null){
            zooKeeper.create(path,value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }else{
            zooKeeper.setData(path,value.getBytes(CHARSET),-1);
        }
    }

    /**
     * 读取一个节点的配置属性
     * @param path
     * @param watch
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     *
     * Stat对象相当于是一个对象的元信息，由getData()返回填充
     */
    public String read(String path, Watcher watch) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path,watch,null);
        return new String(data,CHARSET);
    }
}
