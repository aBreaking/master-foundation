package master.abreaking.jedis;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis cluster config
 * @author liwei_paas
 * @date 2020/7/30
 */
public class RedisClusterConfig {
    final static String redis_ip_port = "127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003,127.0.0.1:7004,127.0.0.1:7005,127.0.0.1:7006";
    final static String redis_p = "5172551";

    public JedisCluster getJedisCluster(){
        Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
        List<String> addresses = Arrays.asList(redis_ip_port.split(","));
        for (String addr : addresses) {
            String[] s = addr.split(":");
            clusterNodes.add(new HostAndPort(s[0],Integer.parseInt(s[1])));
        }
        return new JedisCluster(clusterNodes, 3000, 3000, 1000, redis_p, new JedisPoolConfig());
    }
}
