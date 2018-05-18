package application;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;


public class Konsument {
	Queue queue;
	Topic topic;
	JMSConsumer jmsConsumer;
	JMSContext jmsContext;
	public Konsument(QueueAsynchConsumer queue1) {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		try {
			// 7676 numer portu, na którym JMS Service nasłuchuje połączeń
			//((com.sun.messaging.ConnectionFactory) connectionFactory)
			//		.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676/jms"); // [hostName][:portNumber][/serviceName]
			queue = new com.sun.messaging.Queue("ATJQueue");
			topic = new com.sun.messaging.Topic("ATJTopic");
			jmsConsumer = jmsContext.createConsumer(topic);
			jmsConsumer.setMessageListener(queue1);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		jmsContext.close();
		jmsConsumer.close();
	}
}
