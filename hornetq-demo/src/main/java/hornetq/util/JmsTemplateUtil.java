/**
 * 
 */
package hornetq.util;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

/**
 * @Description TODO
 * @author Lynch
 * @date 2017年3月14日
 */
public class JmsTemplateUtil {

    private static Queue q;
    private static JmsTemplate jmsTemplate;

    static {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "jnp://192.168.137.239:1099");

        JndiTemplate jndiTemplate = new JndiTemplate(p);
        JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
        factoryBean.setJndiName("/ConnectionFactory");
        factoryBean.setJndiTemplate(jndiTemplate);

        try {
            q = (Queue) jndiTemplate.lookup("queue/DLQ");
            factoryBean.afterPropertiesSet();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        ConnectionFactory cf = (ConnectionFactory) factoryBean.getObject();
        jmsTemplate = new JmsTemplate(cf);
    }

    public static Queue getQueue() {
        return q;
    }

    public static JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }
}
