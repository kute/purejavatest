package com.kute.util.lock.curator;

import com.google.common.base.Strings;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by kute on 2018/1/15.
 * http://blog.csdn.net/dc_726/article/details/46475633
 */
public class CuratorCreator {

    static CuratorFramework zkclient = null;
    static String nameSpace = "kute";

    private static String host = "127.0.0.1:2181";
    private static String path = "/";

    public CuratorCreator() {
    }

    public CuratorCreator(String host){
        this(host, null, nameSpace);
    }

    public CuratorCreator(String host, String nameSpace){
        this(host, null, nameSpace);
    }

    public CuratorCreator(String host, String authorization, String nameSpace) {
        if (!Strings.isNullOrEmpty(host)) {
            CuratorCreator.host = host;
        }
        RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(CuratorCreator.host)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .retryPolicy(rp);
        builder.namespace(nameSpace);
        if(!Strings.isNullOrEmpty(authorization)) {
            builder = builder.authorization("digest", authorization.getBytes());
        }
        zkclient = builder.build();
        zkclient.start();// 放在这前面执行
    }

    public void createOrUpdateNode(String path, String content) throws Exception {
        if(exists(path)) {
            zkclient.setData().forPath(path, content.getBytes());
        } else {
            zkclient.create().forPath(path, content.getBytes());
        }
    }

    public String getNode(String path) throws Exception {
        return new String(zkclient.getData().forPath(path));
    }

    public void deleteNode(String path) throws Exception {
        zkclient.delete().forPath(path);
    }

    public boolean exists(String path) throws Exception {
        return zkclient.checkExists().forPath(path) == null;
    }

    public CuratorFramework getClient() {
        return zkclient;
    }

}
