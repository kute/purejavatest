package com.kute.zookeeper;

import com.google.common.base.Preconditions;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import sun.net.util.IPAddressUtil;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.apache.zookeeper.ZooDefs.Ids.AUTH_IDS;

/**
 * created by kute on 2018/04/29 11:33
 */
public class ACLProvider {

    public static enum Schema {

        WORLD("world"),
        AUTH("auth"),
        DIGEST("digest"),
        IP("ip"),
        SUPER("super");

        private String schema;

        private Schema(String schema) {
            this.schema = schema;
        }

        public String getSchema() {
            return schema;
        }

        @Override
        public String toString() {
            return this.schema;
        }
    }

    public static class IdsProvider {

        public static final Id ANYONE_ID_UNSAFE = ZooDefs.Ids.ANYONE_ID_UNSAFE;

        public static final Id AUTH_IDS = ZooDefs.Ids.AUTH_IDS;
    }

    public static final ACL ADMIN = new ACL(ZooDefs.Perms.ALL, AUTH_IDS);

    public static ACL newAcl(List<Integer> permsList, Id id) {
        Preconditions.checkNotNull(permsList);
        int perms = 0;
        for (Integer perm : permsList) {
            perms |= perm;
        }
        return newAcl(perms, id);
    }

    public static ACL newAclWithDigest(int perms,
                                       String digestVal) throws NoSuchAlgorithmException {
        return newAcl(perms, new Id(Schema.DIGEST.schema, DigestAuthenticationProvider.generateDigest(digestVal)));
    }

    public static ACL newAclWithIp(int perms, String ip) {
        Preconditions.checkArgument(IPAddressUtil.isIPv4LiteralAddress(ip) || IPAddressUtil.isIPv6LiteralAddress(ip));
        return newAcl(perms, new Id(Schema.IP.schema, ip));
    }

    public static ACL newAclWithWorldAnyone(int perms) {
        return newAcl(perms, IdsProvider.ANYONE_ID_UNSAFE);
    }

    public static ACL newAclWithAuth(int perms) {
        return newAcl(perms, IdsProvider.AUTH_IDS);
    }

    public static ACL newAcl(int perms,
                             org.apache.zookeeper.data.Id id) {
        return new ACL(perms, id);
    }

}
