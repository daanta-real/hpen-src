package com.hpen.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Exam34_�������׽�Ʈ {
	public static void main(String[] args) throws AWTException, InterruptedException {
		Robot r = new Robot();
		while(true) {
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_4);
			Thread.sleep(30);
			r.keyRelease(KeyEvent.VK_4);
			r.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(1000);
		}
	}
}
