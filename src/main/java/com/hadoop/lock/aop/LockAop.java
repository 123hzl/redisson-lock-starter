package com.hadoop.lock.aop;

import com.hadoop.lock.annotation.Lock;
import com.hadoop.lock.constant.LockType;
import com.hadoop.lock.exception.AnnotationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/02/11 12:14 PM
 */
@Aspect
@Slf4j
public class LockAop {

	//获取配置参数
	@Autowired
	private RedissonProperties redissonProperties;
	@Autowired
	private RedissonClient redissonClient;

	@Pointcut("@annotation(lock)")
	public void lockPointCut(Lock lock) {
	}

	/**
	 * 异常关闭释放锁
	 *
	 * @return
	 * @author hzl 2020-02-11 1:01 PM
	 */
	@Around("lockPointCut(lock)")
	public Object around(ProceedingJoinPoint point, Lock lock) throws Throwable {
//		MethodSignature signature = (MethodSignature) point.getSignature();
//		Method method = signature.getMethod();
		//校验注解
		Boolean keysIsnull=validAnnotationParam(lock);
		//获取注解参数
		//看redisson文档来判断redis部署模式获取不同的锁对象
		//0:先获取锁类型
		String lockType = lock.lockType();
		List<String> keys = new ArrayList<>();
		String lockName = lock.name();

		//1：根据注解生成锁key
		if (keysIsnull) {

		}

		switch (lockName) {
			case LockType.MU:
				break;
			case LockType.RE:
				if (keysIsnull) {

				}else{

				}
				break;
		}

		if (lockType.equals(LockType.MU)) {

			Arrays.stream(lock.keys()).forEach(key -> {

			});
			RedissonMultiLock multiLock = new RedissonMultiLock();
		} else {

		}

		//String lockkey
		//2：根据key获取锁，第一种情况锁不存在，第二中锁存在，但是被占用，第三种锁存在，但是没有被占用

		//开启锁

		//执行目标方法
		Object obj = point.proceed();

		//关闭锁

		return obj;
	}

	//判断注解必输属性是否为空
	public Boolean validAnnotationParam(Lock lock) {
		Boolean keysIsnull = (lock.keys() == null || lock.keys().length == 0);

		if (StringUtils.isEmpty(lock.lockType())) {
			throw new AnnotationException("锁类型缺失");
		}
		//如果是联锁，keys必输
		if (lock.lockType().equals(LockType.MU) && keysIsnull) {
			throw new AnnotationException("联锁情况下，keys为多个锁的名称不能为空");
		}
		return keysIsnull;
	}


	//解析key，#{user.id,user.name},
	public List<String> generateKey(String key, String[] parameterNames, Object[] values, String keyConstant) {
		List<String> keys=new ArrayList<>();
		if(!key.contains("#")){
			String s = "redisson:lock:" + key+keyConstant;
			log.info("没有使用spel表达式value->{}",s);
			keys.add(s);
			return keys;
		}
		//spel解析器
		ExpressionParser parser = new SpelExpressionParser();
		//spel上下文
		EvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], values[i]);
		}
		Expression expression = parser.parseExpression(key);
		Object value = expression.getValue(context);
		if(value!=null){
			if(value instanceof List){
				List value1 = (List) value;
				for (Object o : value1) {
					keys.add("redisson:lock:" + o.toString()+keyConstant);
				}
			}else if(value.getClass().isArray()){
				Object[] obj= (Object[]) value;
				for (Object o : obj) {
					keys.add("redisson:lock:" + o.toString()+keyConstant);
				}
			}else {
				keys.add("redisson:lock:" + value.toString()+keyConstant);
			}
		}
		log.info("spel表达式key={},value={}",key,keys);
		return keys;
	}

}
