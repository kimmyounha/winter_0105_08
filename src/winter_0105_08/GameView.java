package winter_0105_08;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import winter_0105_08.Diamond;
import winter_0105_08.Hail;

public class GameView extends JFrame {

	Hail[] hails = new Hail[25];
	JLabel[] lblHails = new JLabel[hails.length];
	Diamond[] diamonds = new Diamond[hails.length / 5];
	JLabel[] lblDiamonds = new JLabel[diamonds.length];
	JLabel charLbl = new JLabel(new ImageIcon("images/user.jpg"));
	JLabel scoreLbl = new JLabel("점수: 100점");
	int score = 100;

	public GameView() {
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		Random random = new Random();
		HailThread hThread = null;
		DiamondThread dThread = null;
		scoreLbl.setBounds(10, 10, 100, 30);
		add(scoreLbl);

		for (int i = 0; i < hails.length; i++) {
			hails[i] = new Hail();
			hails[i].setX(i * 70);
			hails[i].setY(i * random.nextInt(70));
			hails[i].setW(60);
			hails[i].setH(60);
			hails[i].setImgName("images/stone.png");
			hails[i].setPoint(10);
			lblHails[i] = new JLabel(new ImageIcon(hails[i].getImgName()));
			lblHails[i].setBounds(hails[i].getX(), hails[i].getY(), hails[i].getW(), hails[i].getH());
			add(lblHails[i]);
			hThread = new HailThread(lblHails[i], hails[i]);
			hThread.start();
		}

		for (int i = 0; i < diamonds.length; i++) {
			diamonds[i] = new Diamond();
			diamonds[i].setX(i * 80 + random.nextInt(70));
			diamonds[i].setY(0);
			diamonds[i].setW(70);
			diamonds[i].setH(50);
			diamonds[i].setImgName("images/diamond.jpg");
			diamonds[i].setPoint(20);
			lblDiamonds[i] = new JLabel(new ImageIcon(diamonds[i].getImgName()));
			lblDiamonds[i].setBounds(diamonds[i].getX(), diamonds[i].getY(), diamonds[i].getW(), diamonds[i].getH());
			add(lblDiamonds[i]);
			dThread = new DiamondThread(lblDiamonds[i], diamonds[i]);
			dThread.start();
		}

		charLbl.setBounds(300, 300, 60, 70);
		add(charLbl);
		addKeyListener(keyL);

		setTitle("");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 30, 700, 400);
		setVisible(true);
		setResizable(false);
		requestFocus();
	}

	public void changeScore() {
		for (int i = 0; i < lblHails.length; i++) {
			if (charLbl.getX() >= lblHails[i].getX() && charLbl.getX() <= lblHails[i].getX() + lblHails[i].getWidth()) {
				if (charLbl.getY() >= lblHails[i].getY()
						&& charLbl.getY() <= lblHails[i].getY() + lblHails[i].getHeight()) {
					if (score > hails[i].getPoint()) {
						score -= hails[i].getPoint();
						scoreLbl.setText("점수: " + score + "점");
					}
				}
			}
		}

		for (int i = 0; i < lblDiamonds.length; i++) {
			if (charLbl.getX() >= lblDiamonds[i].getX()
					&& charLbl.getX() <= lblDiamonds[i].getX() + lblDiamonds[i].getWidth()) {
				if (charLbl.getY() >= lblDiamonds[i].getY()
						&& charLbl.getY() <= lblDiamonds[i].getY() + lblDiamonds[i].getHeight()) {
					if (score <= 980) {
						score += diamonds[i].getPoint();
						scoreLbl.setText("점수: " + score + "점");
					}
				}
			}
		}
	}

	KeyAdapter keyL = new KeyAdapter() {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				charLbl.setLocation(charLbl.getX(), charLbl.getY() - 10);
				break;
			case KeyEvent.VK_DOWN:
				charLbl.setLocation(charLbl.getX(), charLbl.getY() + 10);
				break;
			case KeyEvent.VK_LEFT:
				charLbl.setLocation(charLbl.getX() - 10, charLbl.getY());
				break;
			case KeyEvent.VK_RIGHT:
				charLbl.setLocation(charLbl.getX() + 10, charLbl.getY());
				break;
			}
			changeScore();
		}
	};

	public class HailThread extends Thread {
		JLabel hailLbl;
		Hail hail;

		public HailThread(JLabel hailLbl, Hail hail) {
			this.hailLbl = hailLbl;
			this.hail = hail;
		}

		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if (hailLbl.getY() <= 600)
					hailLbl.setLocation(hailLbl.getX(), hailLbl.getY() + 10);
				else {
					hailLbl.setLocation(hailLbl.getX(), random.nextInt(70));
				}
				try {
					sleep(10 * random.nextInt(20));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public class DiamondThread extends Thread {
		JLabel diamondLbl;
		Diamond diamond;

		public DiamondThread(JLabel diamondLbl, Diamond diamond) {
			this.diamondLbl = diamondLbl;
			this.diamond = diamond;
		}

		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if (diamondLbl.getY() <= 600)
					diamondLbl.setLocation(diamondLbl.getX(), diamondLbl.getY() + 10);
				else {
					diamondLbl.setLocation(diamondLbl.getX(), random.nextInt(70));
				}
				try {
					sleep(10 * random.nextInt(20));
				} catch (InterruptedException e) {

				}
			}

		}
	}

	public static void main(String args[]) {
		new GameView();
	}
}