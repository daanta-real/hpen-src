package com.hpen.util;

import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;

import com.hpen.draw.ui.CaptureFrame;
import com.hpen.draw.ui.DrawingFrame;
import com.hpen.livezoom.ui.ZoomFrame;
import com.hpen.property.ProgramIcon;
import com.hpen.property.ui.MarkDownViewer;
import com.hpen.property.ui.PropertyFrame;
import com.hpen.util.file.RegisterManager;
import com.hpen.util.file.VersionManager;
import com.hpen.value.Version;

public class TrayManager{
	private TrayIcon tray;
	
	private PopupMenu popup = new PopupMenu();
	
	private MenuItem note = new MenuItem("�ʱ��ϱ�(����)");
	private MenuItem whiteboard = new MenuItem("�ʱ��ϱ�(ȭ��Ʈ����)");
	private MenuItem capture = new MenuItem("ĸ���ϱ�");
	private MenuItem zoom = new MenuItem("������");
	private MenuItem setting = new MenuItem("ȯ�漳��");
	private CheckboxMenuItem onStart = new CheckboxMenuItem("�������α׷� ���");
	private CheckboxMenuItem onDesktop = new CheckboxMenuItem("����ȭ�鿡 �ٷΰ��� �����");
	private MenuItem tutorial = new MenuItem("��뼳��");
	
	private MenuItem exit = new MenuItem("�����ϱ�");
	
	public static void start(){
		try{
			TrayManager mt = new TrayManager();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private  TrayManager() throws Exception{
		if(!SystemTray.isSupported())
			throw new Exception("Ʈ���̸� �������� �ʽ��ϴ�");
		tray = new TrayIcon(ProgramIcon.getIcon());
		tray.setToolTip(Version.getInstance().toString());
		SystemTray.getSystemTray().add(tray);
		
		add();
		event();
	}
	
	private void add(){
		tray.setPopupMenu(popup);
		popup.add(note);
		popup.add(whiteboard);
		popup.add(capture);
		popup.add(zoom);
		popup.addSeparator();
		popup.add(setting);
		popup.add(tutorial);
		popup.addSeparator();
		popup.add(onDesktop);
		popup.add(onStart);
		popup.addSeparator();
		popup.add(exit);
		onStart.setState(RegisterManager.checkRegisterStartProgram());
		onDesktop.setState(RegisterManager.checkRegisterDesktop());
	}
	
	private void event(){
		note.addActionListener(e->{
			DrawingFrame.start(DrawingFrame.TRANSPARENT);
		});
		whiteboard.addActionListener(e->{
			DrawingFrame.start(DrawingFrame.WHITEBOARD);
		});
		capture.addActionListener(e->{
			CaptureFrame.start();
		});
		setting.addActionListener(e->{
			PropertyFrame.start();
		});
		zoom.addActionListener(e->{
			ZoomFrame.start();
		});
		tutorial.addActionListener(e->{
			try{
				//�����쿡 ����� ���α׷����� �ڵ� �����ϴ� ���
				//Desktop desktop = Desktop.getDesktop();
				//desktop.edit(new File("README.md"));
				//MarkDownViewer viewer = new MarkDownViewer(new File("README.md"));
				Runtime.getRuntime().exec("cmd.exe /C start https://github.com/hiphop5782/hPen/blob/master/README.md");
			}catch(Exception err){
				err.printStackTrace();
			}
		});
		onStart.addItemListener(e->{
			if(onStart.getState()){
				RegisterManager.registerStartProgram();
			}else{
				RegisterManager.unregisterStartProgram();
			}
		});
		onDesktop.addItemListener(e->{
			if(onDesktop.getState()){
				RegisterManager.createShortcutToDesktop();
			}else{
				RegisterManager.removeShortcutFromDesktop();
			}
		});
		exit.addActionListener(e->{
			System.exit(0);
		});
	}
	
}








