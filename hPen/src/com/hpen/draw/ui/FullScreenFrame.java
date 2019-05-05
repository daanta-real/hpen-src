package com.hpen.draw.ui;

import java.awt.Rectangle;

import javax.swing.JFrame;

import com.hakademy.utility.screen.ScreenManager;
import com.hpen.util.CursorManager;

/**
 * �巡�� ���� �ʴ� ��ü ȭ�� â�� �����ϴ� Ŭ����
 * â�� ��ġ�� ���콺 Ŀ���� ��ġ�� ������ ����
 * @author hwang
 */
public class FullScreenFrame extends JFrame{
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
