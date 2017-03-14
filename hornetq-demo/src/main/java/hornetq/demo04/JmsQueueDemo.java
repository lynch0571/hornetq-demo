/**
 * 
 */
package hornetq.demo04;

import hornetq.util.JmsTemplateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JmsQueueDemo {

    public static void main(String[] args) {
        sendQueue();
        receiveQueue();
    }

    public static void sendQueue() {
        List<Object> objs = new ArrayList<Object>();
        objs.add("1");
        objs.add("2");

        final String text = JSON.toJSONString(objs, SerializerFeature.BrowserCompatible, SerializerFeature.WriteClassName,
                SerializerFeature.DisableCircularReferenceDetect);
        JmsTemplateUtil.getJmsTemplate().send(JmsTemplateUtil.getQueue(), new MessageCreator() {
            @Override
            public Message createMessage(Session s) throws JMSException {
                TextMessage tm = s.createTextMessage(text);
                return tm;
            }
        });

        System.out.println("Sent:"+text);
    }

    public static void receiveQueue() {
        TextMessage msg = (TextMessage) JmsTemplateUtil.getJmsTemplate().receive(JmsTemplateUtil.getQueue());
        try {
            System.out.println("Received:"+msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
