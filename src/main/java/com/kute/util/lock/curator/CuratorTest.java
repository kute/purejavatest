package com.kute.util.lock.curator;

import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * created by bailong001 on 2018/09/03 12:23
 */
public class CuratorTest {

    protected static final String PROJECT_LIFE_PATH = "/dubbo";

    public static void main(String[] args) throws Exception {

        CuratorCreator creator = new CuratorCreator("zk02-test.lianjia.com:2181", "jichu:bdcccaccB9", "jichu3");

        CuratorFramework client = creator.getClient();

        List<String> projectNodes = client.getChildren().forPath(PROJECT_LIFE_PATH);
        if (null != projectNodes) {
            for (String projectNode : projectNodes) {
                String path = PROJECT_LIFE_PATH + "/" + projectNode;
                byte[] dataBytes = client.getData().forPath(path);
                if (null != dataBytes) {
                    try {
                        System.out.println(new String(dataBytes));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

}
