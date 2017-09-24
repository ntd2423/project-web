package com.ntd.aop.annotation.db;

import com.ntd.common.Const;
import java.util.logging.Logger;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.CannotCreateTransactionException;

public class DBReadWriteInterceptor implements MethodInterceptor {
	private static final Logger logger = Logger.getLogger(DBReadWriteInterceptor.class.getName());
	private MyRoutingDataSource routingDataSource;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		SwitchDataSource switchDataSource = AnnotationUtils.getAnnotation(invocation.getMethod(), SwitchDataSource.class);
		if (null == switchDataSource) {
            MyContextHolder.setCustomerType(Const.DB.MASTER);
        } else {
            if (switchDataSource.value().equals(Const.DB.MASTER)) {
                MyContextHolder.setCustomerType(Const.DB.MASTER);
            } else if (switchDataSource.value().equals(Const.DB.SLAVE)) {
                MyContextHolder.setCustomerType(Const.DB.SLAVE);
            } else {
                MyContextHolder.setCustomerType(Const.DB.MASTER);
            }
        }
		try{
			return invocation.proceed();
		}catch(Exception e){
			if(e instanceof CannotCreateTransactionException){
				logger.info("CannotCreateTransactionException");
				this.routingDataSource.removeKey(MyContextHolder.getDatasourceKey());
			}
			throw e;
		}finally {
			MyContextHolder.setCustomerType(Const.DB.MASTER);
		}
	}
	
	public void setRoutingDataSource(MyRoutingDataSource datasource){
		this.routingDataSource = datasource;
	}

}
