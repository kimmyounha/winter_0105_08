package winter_0105_08;

import java.util.Random;

import javax.swing.JLabel;

public class DiamondThread  extends Thread{
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
			if( diamondLbl.getY() <= GameView.FRAME_HEIGHT)
				diamondLbl.setLocation(diamondLbl.getX(), diamondLbl.getY() + 10);
			else {
				diamondLbl.setLocation(diamondLbl.getX(), random.nextInt(70));
			}
				
			
			try {
				sleep(30 * random.nextInt(50));
			} catch (InterruptedException e) {

			}
		}

	}
}