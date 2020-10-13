package com.hpen.util;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DialogManager {
	public static void alert(String msg) {
		JLabel label = new JLabel(msg);
		label.setFont(new Font("����", Font.PLAIN, 20));
		JOptionPane.showMessageDialog(null, label, "�˸�", JOptionPane.PLAIN_MESSAGE);
	}

	public static int confirm(String string) {
		JLabel label = new JLabel(string);
		label.setFont(new Font("����", Font.PLAIN, 20));
		return JOptionPane.showConfirmDialog(null, label, "�ֽ� ���� �ٿ�ε�", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
}
