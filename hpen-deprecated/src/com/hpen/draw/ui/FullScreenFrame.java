package com.hpen.draw.ui;

import javax.swing.JFrame;

/**
 * �巡�� ���� �ʴ� ��ü ȭ�� â�� �����ϴ� Ŭ����
 * â�� ��ġ�� ���콺 Ŀ���� ��ġ�� ������ ����
 * @author hwang
 */
public abstract class FullScreenFrame extends JFrame{
	
	protected int mode;
	protected void setMode(int mode) {
		this.mode = mode;
	}
	public int getMode() {
		return mode;
	}
	
	public FullScreenFrame() {
	}
	
	protected void screen() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setFocusTraversalKeysEnabled(false);
	}
	
}
