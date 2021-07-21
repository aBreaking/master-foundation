package master.abreaking.jedis;

import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JedisCluster使用
 * @author liwei_paas
 * @date 2020/7/28
 */
public class JedisClusterMaster {

    final static String redis_ip_port = "127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003,127.0.0.1:7004,127.0.0.1:7005,127.0.0.1:7006";
    final static String redis_p = "5172551";
    final static Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
    static {
        List<String> addresses = Arrays.asList(redis_ip_port.split(","));
        for (String addr : addresses) {
            String[] s = addr.split(":");
            clusterNodes.add(new HostAndPort(s[0],Integer.parseInt(s[1])));
        }
    }

    @Test
    public void deleteBatch() throws InterruptedException {
        JedisCluster redis = getJedisCluster();

        for (int i = 0; i < 10; i++) {
            redis.set("a"+i,String.valueOf(System.currentTimeMillis()));
        }

        Thread.sleep(3000);
        String[] keys = new String[10];
        for (int i = 0; i < 10; i++) {
            keys[i]  = "a"+i;
        }
        String lua = "for i=0,9 do redis.call('del', KEYS[i]) end";
        redis.eval(lua,10,keys);

    }

    @Test
    public void jedisCluster() throws InterruptedException, IOException {
        JedisCluster cluster = getJedisCluster();
        String name = cluster.get("name");
        System.out.println(name);
        // cluster.close();
    }

    @Test
    public void anyJedisConnect() throws Exception {
        for (int i = 0; i < 100; i++) {
            //connected_clients:101
            new Thread(()->{
                final JedisCluster cluster = getJedisCluster();
                String name = cluster.get("name");
                System.out.println(name);
            }).start();
        }
        System.out.println("test connect number");
        Thread.sleep(10000);
    }
    @Test
    public void jedisConnect() throws Exception {
        //一个cluster默认 8个连接
        final JedisCluster cluster = getJedisCluster();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                String name = cluster.get("name");
                System.out.println(name);
            }).start();
        }
        final JedisCluster cluster2 = getJedisCluster();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                String name = cluster2.get("name");
                System.out.println(name);
            }).start();
        }
        final JedisCluster cluster3 = getJedisCluster();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                String name = cluster3.get("name");
                System.out.println(name);
            }).start();
        }

        System.out.println("test connect number");
        Thread.sleep(10000);
    }

    public static JedisCluster getJedisCluster(){

        JedisCluster cluster = new JedisCluster(clusterNodes, 3000, 3000, 1000, redis_p, new JedisPoolConfig());
        return cluster;
    }

    public void test01(){

    }
}
