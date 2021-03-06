package toddyjms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();

			String[] messages = new String[3];
			messages[0] = "Message 1";
			messages[1] = "Message 2";
			messages[2] = "Message 3";

			producer.setPriority(3);
			producer.send(queue, messages[0]);

			producer.setPriority(1);
			producer.send(queue, messages[1]);

			producer.setPriority(9);
			producer.send(queue, messages[2]);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			System.out.println("First Message received: " + consumer.receiveBody(String.class));
			System.out.println("Seccond Message received: " + consumer.receiveBody(String.class));
			System.out.println("Third Message received: " + consumer.receiveBody(String.class));
		}
	}

}
