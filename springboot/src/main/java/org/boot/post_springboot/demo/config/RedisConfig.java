package org.boot.post_springboot.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    // Redis 연결을 위한 ConnectionFactory 빈을 생성합니다.
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 서버의 단일 인스턴스 구성을 설정합니다.
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        // Lettuce 라이브러리를 사용하여 Redis 연결을 생성합니다.
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // Redis 작업을 위한 RedisTemplate 빈을 생성합니다.
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 앞서 생성한 ConnectionFactory를 설정합니다.
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // 키의 직렬화/역직렬화에 문자열 직렬화기를 사용합니다.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 값의 직렬화/역직렬화에 문자열 직렬화기를 사용합니다.
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}