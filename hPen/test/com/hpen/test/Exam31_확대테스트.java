package com.hpen.test;

import com.hpen.draw.ui.DrawingFrame;
import com.hpen.draw.ui.State;
import com.hpen.util.ClassManager;

public class Exam31_Ȯ���׽�Ʈ {
	public static void main(String[] args) throws Exception{
		ClassManager.initialize();
		DrawingFrame.start(State.WHITEBOARD);
	}
}
