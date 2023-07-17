package test.myblog.config;

import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;




import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


@Configuration
public class RedisConfig {
	
	private final static RedisTemplate<String, Object> template = new RedisTemplate<>();
	private final static LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
	
	@Bean
	public static RedisTemplate<String, Object> redisTemplate() {
		template.setConnectionFactory(lettuceConnectionFactory);
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer objectSerializer = new GenericJackson2JsonRedisSerializer();
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(objectSerializer);
		template.setHashKeySerializer(stringSerializer);
		template.setHashValueSerializer(objectSerializer);
		return template;
	}
	
	public static void setDatabase(Integer i) {
		lettuceConnectionFactory.setDatabase(i);
		lettuceConnectionFactory.afterPropertiesSet();
		template.setConnectionFactory(lettuceConnectionFactory);
		template.afterPropertiesSet();
	}
	
	@PreDestroy
	public void cleanup() {
		template.execute((RedisCallback<String>) connection -> {
            connection.flushAll();
			return "Done.";
        });
		if (lettuceConnectionFactory != null) {
			lettuceConnectionFactory.destroy();
		}
		if(template != null) {
			lettuceConnectionFactory.destroy();
		}
	}
}
