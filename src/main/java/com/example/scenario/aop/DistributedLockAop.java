package com.example.scenario.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

import static co.elastic.clients.elasticsearch.ingest.ProcessorBuilders.kv;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";
    private final AopForTransaction aopForTransaction;
    private final RedissonClient redissonClient;

    @Around("@annotation(com.example.scenario.aop.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
            signature.getParameterNames(),
            joinPoint.getArgs(),
            distributedLock.key());
        RLock rLock = redissonClient.getLock(key);

        try{
            boolean available = rLock.tryLock(
                distributedLock.waitTime(),
                distributedLock.leaseTime(),
                distributedLock.timeUnit());
            if(!available){
                return false;
            }
            return aopForTransaction.proceed(joinPoint);
        }catch (InterruptedException e){
            throw new InterruptedException();
        }finally {
            try{
                rLock.unlock();
            } catch (IllegalArgumentException e){
                log.info("Redisson Lock Already Unlock {} {}",
                    method.getName(),
                    key);
            }
        }
    }
}
