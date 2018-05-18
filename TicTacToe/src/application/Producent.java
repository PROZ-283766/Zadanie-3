package application;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Topic;

public class Producent {
	public void sendQueueMessages(String msg) {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		try {
			// [hostName][:portNumber][/serviceName]
			// 7676 numer portu, na którym JMS Service nasłuchuje połączeń
			((com.sun.messaging.ConnectionFactory) connectionFactory)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676/jms");
			JMSContext jmsContext = connectionFactory.createContext();
			Message message = jmsContext.createMessage();
			message.setStringProperty("wiadomosc", msg);
			JMSProducer jmsProducer = jmsContext.createProducer();
			Topic topic = new com.sun.messaging.Topic("ATJTopic");
			jmsProducer.send(topic, msg);
			//System.out.printf("Wiadomość '%s' została wysłana.\n", msg);
			jmsContext.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
