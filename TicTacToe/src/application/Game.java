package application;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Game {

	private Konsument konsument = new Konsument(new QueueAsynchConsumer(this));
	private Producent producent = new Producent();
	boolean isX;
	boolean canMove;
	int number;
	int tab[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };// 0-nothing,1-X,-1-O
	int tabElements = 0;

	@FXML
	Button startGame;
	@FXML
	Label infoLabel;
	@FXML
	Label label1;
	@FXML
	Label label2;
	@FXML
	Label label3;
	@FXML
	Label label4;
	@FXML
	Label label5;
	@FXML
	Label label6;
	@FXML
	Label label7;
	@FXML
	Label label8;
	@FXML
	Label label9;

	public void close() {
		konsument.close();
	}

	public void drawAlert() {
		infoLabel.setText("Draw");
		restart();
	}

	public void win(boolean var1) {
		if (var1)
			infoLabel.setText(" X wins");
		else
			infoLabel.setText(" O wins");
		restart();
	}

	public void label(int var1, int var2) {
		switch (var1) {
		case 1:
			switch (var2) {
			case 1:
				label1.setText("X");
				break;
			case 2:
				label2.setText("X");
				break;
			case 3:
				label3.setText("X");
				break;
			case 4:
				label4.setText("X");
				break;
			case 5:
				label5.setText("X");
				break;
			case 6:
				label6.setText("X");
				break;
			case 7:
				label7.setText("X");
				break;
			case 8:
				label8.setText("X");
				break;
			case 9:
				label9.setText("X");
				break;
			}
			break;
		case -1:
			switch (var2) {
			case 1:
				label1.setText("O");
				break;
			case 2:
				label2.setText("O");
				break;
			case 3:
				label3.setText("O");
				break;
			case 4:
				label4.setText("O");
				break;
			case 5:
				label5.setText("O");
				break;
			case 6:
				label6.setText("O");
				break;
			case 7:
				label7.setText("O");
				break;
			case 8:
				label8.setText("O");
				break;
			case 9:
				label9.setText("O");
				break;
			}
			break;
		case 0:
			switch (var2) {
			case 1:
				label1.setText("+");
				break;
			case 2:
				label2.setText("+");
				break;
			case 3:
				label3.setText("+");
				break;
			case 4:
				label4.setText("+");
				break;
			case 5:
				label5.setText("+");
				break;
			case 6:
				label6.setText("+");
				break;
			case 7:
				label7.setText("+");
				break;
			case 8:
				label8.setText("+");
				break;
			case 9:
				label9.setText("+");
				break;
			}
			break;
		}
	}

	public void checkNumber(int a) {
		boolean oldValue = isX;
		if (a == number)
			return;
		if (a > number) {
			isX = false;
			producent.sendQueueMessages("can");
		} else
			isX = true;

		System.out.println(isX);
		if (isX)
			infoLabel.setText("You are X");
		else
			infoLabel.setText("You are O");
		if (isX != oldValue)
			producent.sendQueueMessages("init" + number);
	}

	public void permision() {
		canMove = true;
	}

	public void move(boolean var1, int var2) {
		if (var1) {
			tab[var2 - 1] = 1;
			label(1, var2);
		} else {
			tab[var2 - 1] = -1;
			label(-1, var2);
		}
		tabElements++;

		isEnd();

	}

	private void isEnd() {
		if (tabElements == 9) {

			producent.sendQueueMessages("draw");

		}
		if (tabElements >= 5) {
			isEqual(0, 4, 8);
			isEqual(0, 3, 6);
			isEqual(1, 4, 7);
			isEqual(2, 5, 8);
			isEqual(0, 1, 2);
			isEqual(3, 4, 5);
			isEqual(6, 7, 8);
			isEqual(6, 4, 2);
		}

	}

	private void isEqual(int a, int b, int c) {
		if (tab[a] != 0)
			if (tab[a] == tab[b])
				if (tab[b] == tab[c])
					producent.sendQueueMessages("win" + tab[a]);
	}

	private void restart() {
		for (int i = 0; i != 9; ++i) {
			tab[i] = 0;
			label(0, i + 1);
		}
		tabElements = 0;
		canMove = false;
		startGame.setDisable(false);
	}

	@FXML
	private void startGame_Click() {
		isX = true;
		canMove = false;
		startGame.setDisable(true);
		number = new Random().nextInt();
		System.out.println(number);
		producent.sendQueueMessages("init" + number);
	}

	@FXML
	private void label1_Click() {
		label_Click(1);
	}

	@FXML
	private void label2_Click() {
		label_Click(2);
	}

	@FXML
	private void label3_Click() {
		label_Click(3);
	}

	@FXML
	private void label4_Click() {
		label_Click(4);
	}

	@FXML
	private void label5_Click() {
		label_Click(5);
	}

	@FXML
	private void label6_Click() {
		label_Click(6);
	}

	@FXML
	private void label7_Click() {
		label_Click(7);
	}

	@FXML
	private void label8_Click() {
		label_Click(8);
	}

	@FXML
	private void label9_Click() {
		label_Click(9);
	}

	private void label_Click(int i) {
		if (!startGame.isDisable())
			return;

		if (tabElements == 0 && isX && canMove) {
			producent.sendQueueMessages("move" + "t" + i);
			return;
		}

		if (tabElements % 2 == 1 && !isX && tab[i - 1] == 0) {
			producent.sendQueueMessages("move" + "f" + i);
		}

		if (tabElements % 2 == 0 && isX && tab[i - 1] == 0) {
			producent.sendQueueMessages("move" + "t" + i);
		}
	}

}
