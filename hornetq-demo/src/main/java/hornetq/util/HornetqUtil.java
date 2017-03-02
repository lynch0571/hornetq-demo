package hornetq.util;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @Description HornetQ工具类，用来初始化连接信息，并获取连接工厂、队列等 
 * @author Lynch 
 * @date 2017年3月2日
 */
public class HornetqUtil {
    private static Context context = null;
    private static ConnectionFactory factory = null;
    private static Queue queue = null;

    // 用静态代码块来初始化，当类被载入时，只被执行一次
    static {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "jnp://192.168.137.239:1099");
        try {
            context = new InitialContext(p);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据名称获取连接工厂
     * 
     * @param factoryName
     * @return
     */
    public static ConnectionFactory getConnectionFactory(String factoryName) {
        try {
            factory = (ConnectionFactory) context.lookup("/" + factoryName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return factory;
    }

    /**
     * 根据名称获取队列
     * 
     * @param queueName
     * @return
     */
    public static Queue getQueue(String queueName) {
        try {
            queue = (Queue) context.lookup("queue/" + queueName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return queue;
    }
}
