package hornetq.demo02;

import hornetq.pojo.Person;
import hornetq.util.HornetqUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @Description 消费者，永真循环监听并消费队列 
 * @author Lynch 
 * @date 2017年3月2日
 */
public class Consumer {

    public static void main(String[] args) {

        ConnectionFactory factory = HornetqUtil.getConnectionFactory("ConnectionFactory");
        Queue queue = HornetqUtil.getQueue("DLQ");

        try (Connection con = factory.createConnection();
                Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageConsumer consumer = session.createConsumer(queue)) {

            con.start();

            System.out.println("消费者循环监听：");
            while (true) {
                ObjectMessage messageReceived = (ObjectMessage) consumer.receive();
                Person one = (Person) messageReceived.getObject();
                System.out.println("Receive message: " + one);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
