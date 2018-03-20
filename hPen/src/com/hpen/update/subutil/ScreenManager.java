package com.hpen.update.subutil;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.SecondaryLoop;

/**
 * ȭ���� �����ϴ� ����� ���� Manager<br>
 * �̱��� ������� ����
 * @author User
 */
public class ScreenManager {

	private static ScreenManager manager = new ScreenManager();
	public static ScreenManager getManager() {
		return manager;
	}
	private ScreenManager() {}
	
	/**
	 * 
	 * @return ���� Ŀ���� ��ġ�� ������� Rectangle����
	 */
	public Rectangle getCurrentMonitorRect() {
		PointerInfo info = MouseInfo.getPointerInfo();
		GraphicsDevice device = info.getDevice();
		GraphicsConfiguration configuration = device.getDefaultConfiguration();
		return configuration.getBounds();
	}
	
	/**
	 * @return ��� GraphicDevice �迭 ����
	 */
	private GraphicsDevice[] getAllGraphicDevices(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = environment.getScreenDevices();
		return devices;
	}
	
	/**
	 * 
	 * @return ��� GraphicDevice ī��Ʈ ��
	 */
	public int getMonitorCount(){
		return getAllGraphicDevices().length;
	}
	
	/**
	 * 
	 * @return ��� GraphicDevice�� Dimension ����
	 */
	public Rectangle[] getAllMonitorsize(){
		GraphicsDevice[] devices = getAllGraphicDevices();
		Rectangle[] rect = new Rectangle[devices.length];
		for(int i=0; i < rect.length ; i++){
			rect[i] = devices[i].getDefaultConfiguration().getBounds(); 
		}
		return rect;
	}
	
	public static final int MAIN_MONITOR = 0;
	public static final int SECOND_MONITOR = 1;
	public static final int THIRD_MONITOR = 2;
	/**
	 * ���ϴ� ȭ�鿡 ���� ũ�������� �˾Ƴ� �� �ִ� �޼ҵ�
	 * @param screen ȭ�� ��ȣ
	 * @return �ش� ������� Rectangle ����
	 */
	public Rectangle getMonitorRect(int screen){
		return getAllMonitorsize()[screen];
	}
	
}









