package study;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Exam13_�ѿ�Ű�ν� {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		JPanel panel = (JPanel)frame.getContentPane();
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_DOWN_MASK);
		panel.getInputMap().put(key, "switch charset");
		panel.getActionMap().put("switch charset", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "�ѿ���ȯ");
			}
		});
		System.out.println(panel.getInputMap().keys());
		System.out.println(panel.getActionMap().keys());
		frame.setVisible(true);
	}
}
