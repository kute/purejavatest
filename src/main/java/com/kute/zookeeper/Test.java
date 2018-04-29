package com.kute.zookeeper;

import com.google.common.collect.Lists;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * created by kute on 2018/04/29 09:44
 */
public class Test {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        List<String > connectionList = Lists.newArrayList(
                "127.0.0.1:2181",
                "127.0.0.1:2182",
                "127.0.0.1:2183"
        );

        try(
                ZookeeperFactory.Builder builder = ZookeeperFactory.builder()
                .connectionList(connectionList)
                .sessionTimeout(5000)
                .build()
        ) {

            if(null != builder) {
                ZooKeeper zooKeeper = builder.getZooKeeper();

                Watcher watcher = new DefaultWatcher();

                List<String > children = zooKeeper.getChildren("/", watcher);
                System.out.println(children);

                System.out.println(new String(zooKeeper.getData("/p2181", true, null)));

                System.out.println(ZooDefs.Perms.ALL);

            }
        }

    }
}
