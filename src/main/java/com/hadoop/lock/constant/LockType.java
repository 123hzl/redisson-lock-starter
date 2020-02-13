package com.hadoop.lock.constant;

/**
 * description
 * 分布式锁类型
 * @author hzl 2020/02/11 9:33 AM
 */
public interface LockType {

	//可重入锁
	String RE="reload";
	//公平锁

	//联锁
     String MU="multi";

	//红锁

	//读写锁，读写某个数据的时候其他人不能同时读写，key就是一个能代表该数据的值，可结合方法参数生成key


	//信号量

	//可过期信号量

	//闭锁

	//异常编码
    String ERROR="400";

}
