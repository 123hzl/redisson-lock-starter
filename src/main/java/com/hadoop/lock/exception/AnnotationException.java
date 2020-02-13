package com.hadoop.lock.exception;

import com.hadoop.lock.constant.LockType;
import lombok.Data;

/**
 * description
 * 自定义异常
 * 注解异常
 * @author hzl 2020/01/02 8:46 PM
 */
@Data
public class AnnotationException extends RuntimeException {
	private String code;
	private String msg;

	public AnnotationException(String msg) {
		super(msg);
		this.code = LockType.ERROR;
		this.msg = msg;
	}
}
