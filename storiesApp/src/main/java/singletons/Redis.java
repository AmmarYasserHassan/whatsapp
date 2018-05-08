package singletons;

import config.ApplicationProperties;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class Redis {
    private static volatile Redis redis;
    public JedisCluster jedisCluster = null;

    private Redis() {
        Set<HostAndPort> connectionPoints = new HashSet<HostAndPort>();

        connectionPoints.add(new HostAndPort(ApplicationProperties.getRedisHost1(), 6379));
        connectionPoints.add(new HostAndPort(ApplicationProperties.getRedisHost2(), 6379));
        connectionPoints.add(new HostAndPort(ApplicationProperties.getRedisHost3(), 6379));

        jedisCluster = new JedisCluster(connectionPoints);
    }


    public static Redis getInstance() {

        if (redis != null) return redis;

        synchronized (Redis.class) {

            if (redis == null) {

                redis = new Redis();
            }
        }

        return redis;
    }
}
