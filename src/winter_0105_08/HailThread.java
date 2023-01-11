package winter_0105_08;

import java.util.Random;

import javax.swing.JLabel;

public class HailThread extends Thread{
	JLabel hailLbl;
	Hail hail;
	int level;
	
	public HailThread(JLabel hailLbl, Hail hail, int level) {
		this.hailLbl = hailLbl;
		this.hail = hail;
		this.level = level;
	}
	
	
	@Override
	public void run() {
		while (true) {
			if(level == 2) 
				break;
				
			Random random = new Random();
			if( hailLbl.getY() <= GameView.FRAME_HEIGHT)
				hailLbl.setLocation(hailLbl.getX(), hailLbl.getY() + 10);
			else {
				hailLbl.setLocation(hailLbl.getX(), random.nextInt(70));
			}
			//changeScore();
			try {
				sleep(20 * random.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}