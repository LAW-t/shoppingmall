package com.example.mall.config;

import com.example.mall.utils.FastJsonRedisSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 10:12
 */
@Configuration
public class RedisConfig {

  @Bean
  @SuppressWarnings(value = {"unchecked", "rawtypes"})
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

    // 使用StringRedisSerializer来序列化和反序列化redis的key值
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(serializer);

    // Hash的key也采用StringRedisSerializer的序列化方式
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(serializer);

    template.afterPropertiesSet();
    return template;
  }
}
