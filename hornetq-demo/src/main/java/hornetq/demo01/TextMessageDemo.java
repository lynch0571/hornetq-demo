package hornetq.demo01;

import hornetq.util.HornetqUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Description 生产者和消费者，发送文本消息，并消费该消息 
 * @author Lynch 
 * @date 2017年3月2日
 */
public class TextMessageDemo {

    public static void main(String[] args) {
        ConnectionFactory factory = HornetqUtil.getConnectionFactory("ConnectionFactory");
        Queue queue = HornetqUtil.getQueue("DLQ");

        // 建立连接和会话，进而发送和接收消息
        try (Connection con = factory.createConnection(); Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);) {
            con.start();
            produceMessage(session, queue, "Hello world!");
            consumeMessage(session, queue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生产消息
     * 
     * @param session
     * @param queue
     * @param text
     */
    public static void produceMessage(Session session, Queue queue, String text) {
        try (MessageProducer producer = session.createProducer(queue)) {
            TextMessage message = session.createTextMessage(text);
            producer.send(message);
            System.out.println("Send message:" + text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费消息
     * 
     * @param session
     * @param queue
     */
    public static void consumeMessage(Session session, Queue queue) {
        try (MessageConsumer consumer = session.createConsumer(queue)) {
            TextMessage message = (TextMessage) consumer.receive(5000);
            System.out.println("Received message:" + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
