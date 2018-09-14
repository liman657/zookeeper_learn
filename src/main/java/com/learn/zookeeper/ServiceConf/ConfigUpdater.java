package com.learn.zookeeper.ServiceConf;

import com.learn.zookeeper.Configuration.ConnectionInfo;
import com.learn.zookeeper.watcher.ConnectionWatcher;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *      这个就是一个更新配置的类，相当于用于测试ActiveKeyValueStore
 */
public class ConfigUpdater {

    public static final String PATH="/config";

    private ActiveKeyValueStore store;

    private Random random = new Random();

    public ConfigUpdater(String hosts) throws IOException {
        store = new ActiveKeyValueStore();
        store.connection(ConnectionInfo.connectionStr);
    }

    /**
     * 这里有个循环，会不断的去写配置信息
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void run() throws KeeperException, InterruptedException {
        while(true){
            String value = random.nextInt(100)+"";
            store.write(PATH,value);
            System.out.printf("Set %s to %s \n",PATH,value);
            TimeUnit.SECONDS.sleep(random.nextInt(100));
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ConfigUpdater configUpdater = new ConfigUpdater(ConnectionInfo.connectionStr);
        configUpdater.run();
    }

}
