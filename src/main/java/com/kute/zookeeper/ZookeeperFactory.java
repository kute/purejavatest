package com.kute.zookeeper;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * created by kute on 2018/04/26 22:59
 */
public class ZookeeperFactory {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperFactory.class);

    public static class Builder implements AutoCloseable {

        private String connectionStr;

        private int sessionTimeout;

        private Watcher watcher;

        private boolean canBeReadOnly;

        private volatile ZooKeeper zooKeeper;

        private Builder() {
            this.sessionTimeout = 5000;
            this.canBeReadOnly = false;
        }

        public ZookeeperFactory.Builder connectionList(List<String > connectionList) {
            checkConnectionList(connectionList);
            this.connectionStr = Joiner.on(",").skipNulls().join(connectionList);
            return this;
        }

        public ZookeeperFactory.Builder sessionTimeout(int sessionTimeout) {
            Preconditions.checkArgument(sessionTimeout > 0);
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public ZookeeperFactory.Builder watcher(Watcher watcher) {
            Preconditions.checkNotNull(watcher);
            return this;
        }

        public ZookeeperFactory.Builder canBeReadOnly(boolean canBeReadOnly) {
            this.canBeReadOnly = canBeReadOnly;
            return this;
        }

        private void checkConnectionList(List<String > connectionList) {
            Preconditions.checkNotNull(connectionList);
            Preconditions.checkArgument(connectionList.size() > 0);
        }

        public ZookeeperFactory.Builder build() throws IOException {
            zooKeeper = new ZooKeeper(this.connectionStr, this.sessionTimeout, this.watcher, this.canBeReadOnly);
            if(null == zooKeeper) {
                throw new UnConnectionException(Joiner.on("").join("Unconnec the server[", this.connectionStr,"] in ", this.sessionTimeout," ms."));
            }
            return this;
        }

        @Override
        public void close() {
            if(null != zooKeeper) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    logger.error("Close zookeeper connection[{}] error", this.connectionStr, e);
                }
            }
        }

        public ZooKeeper getZooKeeper() {
            return zooKeeper;
        }

    }

    public static ZookeeperFactory.Builder builder() {
        return new ZookeeperFactory.Builder();
    }

    private ZookeeperFactory(){}

}
