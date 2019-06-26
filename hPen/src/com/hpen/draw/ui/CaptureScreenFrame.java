package com.hpen.draw.ui;

import java.awt.Graphics;
import java.awt.Image;

import com.hakademy.utility.hook.KeyboardHook;
import com.hakademy.utility.screen.ScreenManager;

public abstract class CaptureScreenFrame extends FullScreenFrame{
	protected KeyboardHook hook = KeyboardHook.getInstance();
	
	protected abstract void setKeyboardPrevent() ;
	protected abstract void setKeyboardUnprevent();
	
	/**
	 * ���� Ŀ���� ��ġ�� ȭ���� �����ӿ� �����ִ� �޼ҵ�
	 */
	protected Image background;
	protected void displayCaptureScreen() {
		ScreenManager manager = ScreenManager.getManager();
		background = manager.getCurrentMonitorImage();
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		if(background == null) 
			g.fillRect(0, 0, getWidth(), getHeight());
		else
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
}



