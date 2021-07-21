package master.abreaking.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * spring-redis rest
 * @author liwei_paas
 * @date 2020/7/30
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")*/
public class SpringRedisMaster {

    @Resource
    JedisCluster jedisCluster;

    @Test
    public void test01() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{

                String name = jedisCluster.hget("routeInfo","10_183");
                System.out.println(name);
            }).start();
        }
        System.out.println("test connect number");
        Thread.sleep(10000);
    }
}
