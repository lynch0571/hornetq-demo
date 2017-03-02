package hornetq.demo03;

import hornetq.pojo.Person;
import hornetq.util.HornetqUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @Description 生产者，发送消息到队列 
 * @author Lynch 
 * @date 2017年3月2日
 */
public class Producer {

    public static void main(String[] args) {

        ConnectionFactory factory = HornetqUtil.getConnectionFactory("ConnectionFactory");
        Queue queue = HornetqUtil.getQueue("DLQ");

        try (Connection con = factory.createConnection();
                Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(queue)) {
            con.start();
            int id = 1;
            while (++id < 10) {
                Person p = new Person(id, id + "师弟");
                ObjectMessage msg = session.createObjectMessage(p);
                producer.send(msg);
                System.out.println("Sent message: " + p);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
