package study;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Exam05_�������� {
	public static void main(String[] args) throws Exception{
		//���� - 9
		//���ĺ� - 2
		//Ư������ - 20����
		//��Ʈ�Ѿ�Ʈ����Ʈ - 0
		//�齺���̽�, ��, ���� - 15
		
		
//		JFrame frame = new JFrame();
//		frame.setSize(400, 300);
//		frame.setLocationByPlatform(true);
//		frame.setAlwaysOnTop(true);
//		frame.setResizable(false);
//		frame.setVisible(true);
//		frame.setFocusTraversalKeysEnabled(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.addKeyListener(new KeyAdapter(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println(Character.getType(e.getKeyChar()));
//			}
//		});
		
		int a = '��';
		int b = '��';
		int c = '��';
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		//[{(�ʼ�)��588}+{(�߼�)��28}+(����)]+44032
		int uni = a * 588 + b * 28 + 0 + 44032;
		System.out.println(uni);
	}
}
