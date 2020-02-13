package com.hadoop.lock.constant;

/**
 * description
 * redis的部署模式
 * @author hzl 2020/02/11 9:41 AM
 */
public enum RedisType {

	//集群模式
	CLUSTER("CR"),
	//云托管模式
	TRUST("TR"),
	//单redis节点模式
	SINGLE("SI"),
	//哨兵模式
	SENTRY("SE"),
	//主从模式
	MS("MS");

	private final String value;

	RedisType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
