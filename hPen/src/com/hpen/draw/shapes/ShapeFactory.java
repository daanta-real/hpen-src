package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Set;

public class ShapeFactory {
	public static final int RECT = KeyEvent.VK_Q;
	public static final int CIRCLE = KeyEvent.VK_W;
	public static final int LINE = KeyEvent.VK_E;
	public static final int ARROW = KeyEvent.VK_F;
	public static final int REVERSE_ARROW = KeyEvent.VK_R;
	public static final int BOTH_ARROW = KeyEvent.VK_G;
	public static final int NORMAL = 0;
	public static final int X = KeyEvent.VK_X;
	public static final int THUNDER = KeyEvent.VK_D;
	public static final int CURVED = KeyEvent.VK_V;

	public static Shape createShape(Set<Integer> pressedKey, Point start, Point end, int thick, Color color){
		
		Shape shape = null;
		
		//���� Ű
		if(pressedKey.size() == 1){
			
			//�簢��(Q)
			if(pressedKey.contains(RECT))
				shape = new Rect(start, end, thick, color);
			
			//��(W)
			else if(pressedKey.contains(CIRCLE))
				shape = new Circle(start, end, thick, color);
			
			//��(E)
			else if(pressedKey.contains(LINE))
				shape = new Line(start, end, thick, color);
			
			//�(V)
			else if(pressedKey.contains(CURVED)) 
				shape = new CurvedLine(start, end, thick, color);
				
			//������ ȭ��ǥ(R)
			else if(pressedKey.contains(REVERSE_ARROW))
				shape = new ReverseArrow(start, end, thick, color);
			
			//ȭ��ǥ(F)
			else if(pressedKey.contains(ARROW))
				shape = new Arrow(start, end, thick, color);
			
			else if(pressedKey.contains(BOTH_ARROW)) 
				shape = new BothArrow(start, end, thick, color);
			
			else if(pressedKey.contains(X)) 
				shape = new X(start, end, thick, color);
			
			else if(pressedKey.contains(THUNDER))
				shape = new Thunder(start, end, thick, color);
		}
		
		return shape;
	}
}
