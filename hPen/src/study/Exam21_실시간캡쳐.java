package study;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hakademy.screen.ScreenSaver;

public class Exam21_�ǽð�ĸ�� {
	static BufferedImage im;
	public static void main(String[] args) throws Exception{
		JFrame fr = new JFrame();
		JPanel panel = new JPanel() {
			protected void paintComponent(java.awt.Graphics g) {
				if(im != null)
					g.drawImage(im, 0, 0, getWidth(), getHeight(), this);
			};
		};
		fr.add(panel);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setBounds(0, 0, 300, 300);
		fr.setVisible(true);
		while(true) {
			//System.out.println(MouseInfo.getPointerInfo().getLocation());
			//int width = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getWidth();
			//int height = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getHeight();
			//System.out.println(width+", "+height);
			
			im = ScreenSaver.getMonitorScreenShotAtCursor();
			panel.repaint();
			Thread.sleep(100);
		}
	}
}
