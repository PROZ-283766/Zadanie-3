package application;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javafx.application.Platform;

public class QueueAsynchConsumer implements MessageListener {
	Game game;

	public QueueAsynchConsumer(Game game) {
		this.game = game;
	}

	@Override
	public void onMessage(Message message) {

		Platform.runLater(() -> {
			try {
				TextMessage textMessage = (TextMessage) message;
				String msg = textMessage.getText();
				if (msg.length() > 4 && msg.substring(0, 4).equals("init")) {
					game.checkNumber(Integer.valueOf(msg.substring(4, msg.length())));
					//System.out.println(msg.substring(4, msg.length()));
				}
				if (msg.length() == 4 && msg.substring(0, 4).equals("draw")) {
					game.drawAlert();
				}
				if (msg.length() == 3 && msg.substring(0, 3).equals("can")) {
					game.permision();
				}
				if (msg.length() >= 4 && msg.substring(0, 3).equals("win")) {
					boolean var1;
					var1 = msg.substring(3, 4).equals("1")  ? true : false;
					game.win(var1);
				}
				if (msg.length() == 6 && msg.substring(0, 4).equals("move")) {
					boolean var1;
					var1 = msg.substring(4, 5).equals("t")  ? true : false;
					int var2 = Integer.valueOf(msg.substring(5, 6));
					game.move(var1, var2);
				}
				//System.out.println("Odebrano wiadomość:" + msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});

	}
}
