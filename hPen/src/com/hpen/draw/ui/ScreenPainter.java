package com.hpen.draw.ui;

import javax.swing.JFrame;

/**
 * @author Administrator
 * <h1>�׸� �׸��� ������</h1>
 * 24 fps�� ������ ����
 */
public class ScreenPainter extends Thread{
	/**
	 * frame �ֻ���, �⺻ ������ 24
	 */
	private int fps = 24;

	private JFrame target;
	
	private boolean flag = true;
	public void kill(){
		flag = false;
	}
	public ScreenPainter(JFrame target){
		this.target = target;
		this.setDaemon(true);
		this.start();
	}
	public void run(){
		while(flag){
			target.repaint();
			try{
				Thread.sleep(1000 / fps);
			}catch(InterruptedException e){}
		}
	}
}