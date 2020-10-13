package com.hpen.livezoom.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hpen.property.ProgramIcon;
import com.hpen.property.PropertyLoader;
import com.hpen.property.ZoomOption;

/**
 * ���̺��� Ŭ����, ���̺����� zoom it�� ��İ� �����ϰ� ����(ȭ���� ���콺�� ����ٴϵ���)
 * ������ ���� ����� ����â�� ������� �ϱ� ������ Ȯ�� �� Ŭ���� �ȵȴٴ� ������ ����, �ذ��� ��
 * @author Hwang
 *
 */
public class ZoomFrame extends JFrame{
	private static ZoomFrame frame = new ZoomFrame();
	public static final String titleString = "������";
	public static void start(){
		frame.setVisible(true);
		ScreenPainter painter = frame.new ScreenPainter();
		painter.start();
	}
	
	public static ZoomFrame getFrame() {
		return frame;
	}

	public static void setFrame(ZoomFrame frame) {
		ZoomFrame.frame = frame;
	}
	
	private String getTitleString(){
		return titleString + " x "+ZoomOption.getInstance().getZoom()+"����";
	}
	private ZoomFrame(){
		super.setTitle(getTitleString());
		super.setBounds(ZoomOption.getInstance().getBounds());
		super.setAlwaysOnTop(true);
		super.setIconImage(ProgramIcon.getIcon());
		super.addComponentListener(new WindowSizeChangeProcessor());
		super.addMouseWheelListener(new MouseWheelProcessor());
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		super.addKeyListener(new KeyBoardProcessor());
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		Image backImage = createImage(getWidth(), getHeight());
		Graphics backScreen = backImage.getGraphics();
		drawScreen(backScreen);
		g.drawImage(backImage, 0, 0, this);
	}
	
	private void drawScreen(Graphics backScreen){
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		int zoom = ZoomOption.getInstance().getZoom();
		Rectangle area = new Rectangle(mouse.x - getWidth()/2 /zoom, mouse.y - getHeight()/2/zoom, getWidth()/zoom, getHeight()/zoom);
		try{
			Robot robot = new Robot();
			BufferedImage img = robot.createScreenCapture(area);
			backScreen.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class KeyBoardProcessor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				close();
			}
		}
	}
	
	class MouseWheelProcessor extends MouseAdapter{
		public void mouseWheelMoved(MouseWheelEvent e) {
			ZoomFrame.this.setTitle(getTitleString());
			ZoomOption.getInstance().setZoom(ZoomOption.getInstance().getZoom()-e.getWheelRotation());
		}
	}
	
	class WindowSizeChangeProcessor extends ComponentAdapter{
		public void componentResized(ComponentEvent e) {
			if(e.getSource() == ZoomFrame.this){
				ZoomOption.getInstance().setWidth(ZoomFrame.this.getWidth());
				ZoomOption.getInstance().setHeight(ZoomFrame.this.getHeight());
			}
		}
		public void componentMoved(ComponentEvent e) {
			if(e.getSource() == ZoomFrame.this){
				ZoomOption.getInstance().setX(ZoomFrame.this.getX());
				ZoomOption.getInstance().setY(ZoomFrame.this.getY());
			}
		}
	}
	
	private void close(){
		PropertyLoader.save();
		this.dispose();
	}
	
	public static void main(String[] args) {
		ZoomFrame.start();
	}
	
	/**
	 * @author Administrator
	 * <h1>�׸� �׸��� ������</h1>
	 * 60 fps�� ������ ����
	 */
	class ScreenPainter{
		/**
		 * frame �ֻ���, �⺻ ������ 60
		 */
		private int fps = 60;
		
		/**
		 * java.util.Timer�� Ȱ���� fps�� �ֻ����� DrawingFrame�� repaint
		 */
		public void start(){
			java.util.Timer timer = new java.util.Timer(true);
			timer.schedule(new TimerTask() {
				public void run() {
					frame.repaint();
				}
			}, 0, 1000 / fps);
		}
	}
}
