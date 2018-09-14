package com.learn.zookeeper.ServiceConf;

import com.learn.zookeeper.Configuration.ConnectionInfo;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.util.Random;

/**
 * author:liman
 * createtime:2018/9/14
 * mobile:15528212893
 * email:657271181@qq.com
 * comment:
 *
 */
public class ConfigWatcher implements Watcher {

    private ActiveKeyValueStore store;

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
            try {
                displayConfig();
            } catch (KeeperException e) {
                System.out.printf("KeeperExceptions.Existing.\n",e);
            } catch (InterruptedException e) {
                System.err.println("Interrupted. exiting.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public ConfigWatcher(String hosts) throws IOException {
        store = new ActiveKeyValueStore();
        store.connection(hosts);
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        String value = store.read(ConfigUpdater.PATH,this);
        System.out.printf("Read %s as %s\n",ConfigUpdater.PATH,value);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ConfigWatcher configWatcher = new ConfigWatcher(ConnectionInfo.connectionStr);
        configWatcher.displayConfig();
        Thread.sleep(new Random().nextInt(100));
    }
}
