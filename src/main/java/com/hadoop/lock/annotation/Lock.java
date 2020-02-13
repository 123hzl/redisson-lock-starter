package com.hadoop.lock.annotation;


import java.lang.annotation.*;

/**
 * description
 * redisson分布式锁注解
 *
 * @author hzl 2020/02/10 7:15 PM
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lock {
	/**
	 * <p>
	 * 锁类型，当锁类型为联锁的情况下，keys存储的就是其他锁的名称，name是当前锁的名称
	 * 当非联锁的情况下，keys和name结合才是当前锁的名称，keys正常情况下为参数值，能用来确定当前操作的唯一行，读写锁的时候用
	 * </p>
	 *
	 * @author hzl 2020/02/13 3:46 PM
	 */
	String lockType() default "";

	/**
	 * 锁的名字
	 *
	 * @return String
	 */
	String name() default "";

	/**
	 * 锁的key,多个key表示联锁
	 *
	 * @return String
	 */
	String[] keys() default {};

	/**
	 * 持锁时间，持锁超过此时间则自动丢弃锁
	 * 单位毫秒，默认5秒
	 *
	 * @return long
	 */
	long leaseTime() default 5000L;

	/**
	 * 没有获取到锁时，等待时间
	 * 单位毫秒，默认60秒
	 *
	 * @return long
	 */
	long waitTime() default 60000L;

	/**
	 * 是否采用锁的异步执行方式(异步拿锁，同步阻塞)
	 *
	 * @return boolean
	 */
	boolean async() default false;

	/**
	 * 是否采用公平锁
	 *
	 * @return boolean
	 */
	boolean fair() default false;
}