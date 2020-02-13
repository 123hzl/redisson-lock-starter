package com.hadoop.lock.config;

import com.hadoop.lock.aop.LockAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 * redisson分布式锁配置
 *
 * @author hzl 2020/02/10 7:15 PM
 */
@Configuration
public class RedissionConfig {


	@Bean
	@ConditionalOnMissingBean(LockAop.class)
	public LockAop lockAop() {
		return new LockAop();
	}

}
