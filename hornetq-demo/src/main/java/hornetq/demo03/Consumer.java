package hornetq.demo03;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import hornetq.pojo.Person;
import hornetq.util.HornetqUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @Description 消费者，引入定时任务，监听并消费队列
 * @author Lynch
 * @date 2017年3月2日
 */
public class Consumer {

    public static void main(String[] args) {

        final int delay = 3000;
        System.out.println("每隔" + delay + " ms消费一次，每次都将消费消耗完为止！");

        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        pool.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                ConnectionFactory factory = HornetqUtil.getConnectionFactory("ConnectionFactory");
                Queue queue = HornetqUtil.getQueue("DLQ");
                try (Connection con = factory.createConnection();
                        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        MessageConsumer consumer = session.createConsumer(queue)) {
                    con.start();
                    System.out.println("消费者来了~");
                    while (true) {
                        // 如果间隔超过delay/10毫秒还没有收到消息，则跳出循环，进入下一轮消费
                        ObjectMessage msg = (ObjectMessage) consumer.receive(delay / 10);
                        if (msg != null) {
                            Person p = (Person) msg.getObject();
                            System.out.println("Receive message: " + p);
                        } else {
                            break;
                        }
                    }
                    System.out.println("消费结束！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, 0, delay, TimeUnit.MILLISECONDS);

    }

}
