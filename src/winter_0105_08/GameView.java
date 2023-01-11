package winter_0105_08;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import winter_0105_08.Diamond;
import winter_0105_08.Hail;

public class GameView extends JFrame {
	public static final int FRAME_WIDTH = 700;
	public static final int FRAME_HEIGHT = 400;
	int hailCount = 16;
	Hail[] hails;
	JLabel[] lblHails;
	Diamond[] diamonds = new Diamond[hailCount / 4];
	JLabel[] lblDiamonds = new JLabel[diamonds.length];
	JLabel charLbl = new JLabel(new ImageIcon("images/user.jpg"));
	JLabel scoreLbl = new JLabel("점수: 100점");
	String[] comboCharStr = { "10cm", "십센노이-10cm", "버스킹10cm", "토크쇼10cm", "청바지10cm" };
	JComboBox<String> comboChar = new JComboBox<String>(comboCharStr);
	String[] comboHailStr = { "어쿠스틱", "고영배", "노란머리" };
	JComboBox<String> comboHail = new JComboBox<String>(comboHailStr);
	int score = 100;
	HailThread hThread = null;
	DiamondThread dThread = null;
	Random random = new Random();
	int hailX = 70, level = 1;
	JLabel lblLevel = new JLabel("Level 1");

	public GameView(GameStart startFrame) {
		startFrame.dispose();
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		Random random = new Random();
		HailThread hThread = null;
		DiamondThread dThread = null;
		scoreLbl.setBounds(10, 10, 100, 30);
		comboChar.setBounds(580, 10, 90, 25);
		comboChar.addItemListener(comboL);
		comboHail.setBounds(580, 40, 90, 25);
		comboHail.addItemListener(comboL);
		lblLevel.setBounds(20, 20, 60, 30);
		add(scoreLbl);
		add(comboChar);
		add(comboHail);
		add(lblLevel);
		changeHailCount();

		charLbl.setBounds(300, 290, 60, 70);
		add(charLbl);
		addKeyListener(keyL);

		setTitle("우박을 피해봐");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 30, FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
		setFocusable(true);
		setResizable(false);
		requestFocus();
	}

	public void changeHailCount() {
		hails = new Hail[hailCount];
		lblHails = new JLabel[hails.length];
//		20개의 우박 객체를 생성해서 배열에 저장
		for (int i = 0; i < hailCount; i++) {
			lblLevel.setText("Level " + level);
			hails[i] = new Hail();
			hails[i].setX(i * hailX);
			hails[i].setY(i * random.nextInt(70));
			lblHails[i] = new JLabel(new ImageIcon(hails[i].getImgName()));
			lblHails[i].setBounds(hails[i].getX(), hails[i].getY(), hails[i].getW(), hails[i].getH());
			add(lblHails[i]);
			hThread = new HailThread(lblHails[i], hails[i], level);
			hThread.start();
		}

//		10개의 다이아몬드 객체를 생성해서 배열에 저장

		for (int i = 0; i < diamonds.length; i++) {
			diamonds[i] = new Diamond();
			diamonds[i].setX(i * 70 + random.nextInt(30));
			diamonds[i].setY(i * random.nextInt(10));
			lblDiamonds[i] = new JLabel(new ImageIcon(diamonds[i].getImgName()));
			lblDiamonds[i].setBounds(diamonds[i].getX(), diamonds[i].getY(), diamonds[i].getW(), diamonds[i].getH());
			add(lblDiamonds[i]);
			dThread = new DiamondThread(lblDiamonds[i], diamonds[i]);
			dThread.start();
		}
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
					if (score < 1300) {
						score += diamonds[i].getPoint();
						scoreLbl.setText("점수: " + score + "점");
					}
				}
			}
		}

		if (score >= 1200 && score < 1300) {
			if (level != 2) {
				hailCount = 20;
				hailX = 60;
				level = 2;
				JOptionPane.showMessageDialog(this, "Level " + level + "가 시작됩니다.");
				score = 1000;
				scoreLbl.setText("점수: " + score + "점");
				changeHailCount();
			}
		} else if (score >= 1300) {
			JOptionPane.showMessageDialog(this, "~~ You Win ~~");
			dispose();
		}

		scoreLbl.setText("점수: " + score + "점");
	}

	ItemListener comboL = new ItemListener() {

		@Override
		public void itemStateChanged(ItemEvent e) {
			ImageIcon icon = null;
			String imgName = null;

			if (e.getSource() == comboChar) {
				switch (comboChar.getSelectedIndex()) {
				case 0:
					imgName = "user";
					break;
				case 1:
					imgName = "user2";
					break;
				case 2:
					imgName = "user3";
					break;
				case 3:
					imgName = "user4";
					break;
				case 4:
					imgName = "user5";
					break;
				}
				icon = new ImageIcon("images/" + imgName + ".jpg");
				charLbl.setIcon(icon);
			} else if (e.getSource() == comboHail) {
				switch (comboHail.getSelectedIndex()) {
				case 0:
					imgName = "stone.png";
					break;
				case 1:
					imgName = "stone2.jpg";
					break;
				case 2:
					imgName = "stone3.jpg";
					break;
				}
				icon = new ImageIcon("images/" + imgName);
				for (int j = 0; j < lblHails.length; j++) {
					lblHails[j].setIcon(icon);
				}

			}
			GameView.this.setFocusable(true);
			GameView.this.requestFocus();
		}
	};

	KeyAdapter keyL = new KeyAdapter() {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (charLbl.getY() > 0)
					charLbl.setLocation(charLbl.getX(), charLbl.getY() - 10);
				break;
			case KeyEvent.VK_DOWN:
				if (charLbl.getY() < FRAME_HEIGHT - charLbl.getHeight() * 2)
					charLbl.setLocation(charLbl.getX(), charLbl.getY() + 10);
				break;
			case KeyEvent.VK_LEFT:
				if (charLbl.getX() > 0)
					charLbl.setLocation(charLbl.getX() - 10, charLbl.getY());
				break;
			case KeyEvent.VK_RIGHT:
				if (charLbl.getX() < FRAME_WIDTH - charLbl.getWidth())
					charLbl.setLocation(charLbl.getX() + 10, charLbl.getY());
				break;
			}
			changeScore();
		}
	};
}