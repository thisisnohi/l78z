package com.seeyon.v3x.rmi.server;

import java.io.File;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

import com.seeyon.ctp.office.ConvertService;
import com.seeyon.ctp.office.util.OfficeTransUtil;
import com.seeyon.ctp.office.util.ReadConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class Server {
  private static final Log log = LogFactory.getLog( Server.class );
  private ApplicationContext ctx;
  private final String host;
  private final int port;
  private List<String> serviceNames;
  private Properties config;

  public static void main(String[] args) {
	int port = 1097;

	Properties prop = readConfig();
	port = Integer.parseInt( prop.getProperty( "rmi.service.port" ).trim() );
	String services = prop.getProperty( "service.bean.id" );
	if (services == null) {
	  log.error( "没有找到服务，请确认您的service/config.properties文件中正确配置了service.bean.id。" );
	  return;
	}
	String[] serviceArray = services.split( ";" );

	Server server = new Server( prop.getProperty( "rmi.service.host" ), port );
	server.config = prop;
	try {
	  for (String service : serviceArray) {
		server.installService( service.trim() );
	  }
	  server.startup();
	} catch (RemoteException e) {
	  log.error( "服务启动失败！", e );
	}
  }

  private static Properties readConfig() {
	Properties prop = ReadConfigUtil.readConfig( "conf/rmi.properties" );
	Properties prop1 = ReadConfigUtil.readConfig( "conf/custom/rmi.properties" );
	Properties conf = ReadConfigUtil.readConfig( "conf/config.properties" );
	prop.putAll( conf );
	if (prop1 != null) prop.putAll( prop1 );
	log.debug( "prop=" + prop );
	log.debug( "conf=" + conf );
	log.debug( "prop1=" + prop1 );
	return prop;
  }

  public Server(String host, int port) {
	this.host = host;
	this.port = port;
	this.serviceNames = new ArrayList();
	log.info( "host=" + host );
	log.info( "port=" + port );
  }

  public Server() {
	this( null, 1097 );
  }

  private void startup() throws RemoteException {
	log.info( "startup services" );
	initSpringContext();
	for (String beanName : this.serviceNames) {
	  String serviceName = beanName;
	  startupService( beanName, serviceName );
	}
	startOfficetransThread();
  }

  public void installService(String beanName) {
	log.debug( "install service " + beanName );
	this.serviceNames.add( beanName );
  }

  private void startupService(String beanName, String serviceName) throws RemoteException {
	log.info( "启动服务:" + serviceName );
	Object bean;
	try {
	  bean = this.ctx.getBean( beanName );
	} catch (BeansException e1) {
	  log.error( "启动失败", e1 );
	  return;
	}
	if (bean == null) {
	  log.error( "启动失败，没有找到" + beanName );
	  return;
	}
	try {
	  Method method = bean.getClass().getMethod( "configure", new Class[]{this.config.getClass()} );
	  method.invoke( bean, new Object[]{this.config} );
	} catch (Exception e) {
	  log.debug( "注入配置，目标服务未实现configure方法，忽略。" );
	}
	RmiServiceExporter exporter = new RmiServiceExporter();
	exporter.setServiceName( serviceName );
	if (this.host != null) {
	  exporter.setRegistryHost( this.host.trim() );
	}
	exporter.setRegistryPort( this.port );
	Class[] interfaces = bean.getClass().getInterfaces();
	if (interfaces.length != 1) {
	  throw new RemoteException( "RMI服务只能导出继承一个接口的类。" );
	}
	exporter.setServiceInterface( interfaces[0] );
	exporter.setService( bean );
	exporter.afterPropertiesSet();
  }

  private void initSpringContext() {
	String property = this.config.getProperty( "applicationcontext.location" );
	log.debug( property );
	String[] paths = property.split( ";" );
	this.ctx = new ClassPathXmlApplicationContext( paths );
  }

  private void startOfficetransThread() {
	BlockingQueue queue = new LinkedBlockingQueue();

	ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 4, 15, 5L, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.DiscardOldestPolicy() );

	log.info( "构建转换线程池:" + threadPool.getClass().getName() );
	OfficeTransUtil u = OfficeTransUtil.getInstance();
	boolean isRun = true;
	final ConvertService service = ConvertService.getInstance();
	while (isRun) {
	  String[] obj = u.getNext();
	  if (obj != null) {
		final String src = obj[0];
		final String target = obj[1];
		log.info( "开始转换任务:src=" + src + "=====target:" + target );
		if ((src != "") && (target != "")) {
		  File targetFile = new File( target );
		  log.info( "new File(target)结果：" + target );
		  log.info( "targetFile.exists():" + targetFile.exists() );
		  if (!targetFile.exists()) {
			long startTime = System.currentTimeMillis();
			FutureTask task = new FutureTask( new Callable() {
			  public String call() {
				int result = service.convertToHtml( src, target );
				Server.log.info( "转换结果result：" + result );
				if (result == 0)
				  OfficeTransUtil.makeOKFile( target );
				else {
				  OfficeTransUtil.makeErrorFile( target );
				}
				return result + "";
			  }
			} );
			threadPool.execute( task );

			while (!task.isDone()) {
			  long t2 = System.currentTimeMillis();

			  if (t2 - startTime > OfficeTransUtil.timeOut) {
				log.info( "转换时间超时，取消任务" );
				task.cancel( true );
			  }
			  try {
				Thread.sleep( 3000L );
			  } catch (InterruptedException e) {
				e.printStackTrace();
			  }
			}
			log.info( "转换任务结束" );
			log.info( "线程队列数量:" + queue.size() );
		  }
		}
	  } else {
		try {
		  Thread.sleep( 3000L );
		} catch (InterruptedException e) {
		  e.printStackTrace();
		  log.info( "线程池运行异常:" + e );
		}
	  }
	}
  }
}