package com.hpen.draw.ui;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hpen.draw.shapes.Curve;
import com.hpen.draw.shapes.Icon;
import com.hpen.draw.shapes.Shape;
import com.hpen.draw.shapes.ShapeFactory;
import com.hpen.draw.shapes.Text;
import com.hpen.draw.ui.component.SaveImageFileChooser;
import com.hpen.property.DrawingOption;
import com.hpen.property.PropertyLoader;
import com.hpen.update.subutil.ScreenManager;
import com.hpen.util.CursorManager;
import com.hpen.util.ScreenData;
import com.hpen.util.image.IconManager;
import com.hpen.util.key.KeyManager;
import com.hpen.util.key.KeyboardPrevent;

/**
 * ĸ�� ��ũ�� �ʱ� ȭ��
 * @author Hwang
 *
 */
public class DrawingFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DrawingOption options = DrawingOption.getInstance();
	
	/**
	 * ���� ���� �÷���
	 */
	private int mode = DRAWING_MODE;
	public static final int DRAWING_MODE = 0;
	public static final int TEXT_MODE = 1;
	public static final int ICON_MODE = 2;
	public static final int CHOICE_MODE = 3;
	public boolean isDefaultMode() {
		return isDrawingMode();
	}
	public void restoreDefaultMode() {
		mode = DRAWING_MODE;
		setCursor();
	}
	public boolean isTextMode() {
		return mode == TEXT_MODE;
	}
	public boolean isIconMode() {
		return mode == ICON_MODE;
	}
	public boolean isDrawingMode() {
		return mode == DRAWING_MODE;
	}
	public boolean isChoiceMode() {
		return mode == CHOICE_MODE;
	}
	
	private static DrawingFrame df = new DrawingFrame();
	public static void start(){
		if(df.isVisible()) return;
		
		df.setKeyboardPrevent();
		df.setWindowTransparent();
		df.prepare();
		df.eventbind();
		df.setVisible(true);
	}
	private void setKeyboardPrevent() {
		KeyboardPrevent.addKey(KeyboardPrevent.WINDOWS_LEFT);
		KeyboardPrevent.addKey(KeyboardPrevent.WINDOWS_RIGHT);
		KeyboardPrevent.addKey(KeyboardPrevent.MENU);
		KeyboardPrevent.addKey(KeyboardPrevent.ALT_RIGHT, ()->{
			options.changeKorean();
		});
		KeyboardPrevent.blockWindowsKey();
	}
	private void setKeyboardUnprevent() {
		KeyboardPrevent.unblockWindowsKey();
	}

	private ScreenData screenData;
	private ScreenPainter screenPainter;
	
	private SaveImageFileChooser chooser = new SaveImageFileChooser(options.getSaveFolder());
	private DrawingFrame(){
		init();
	}
	private void exit(){
		PropertyLoader.save();
		eventunbind();
		clear();
		setVisible(false);
		df.setKeyboardUnprevent();
	}

	private BufferedImage bg;
	private void setWindowTransparent(){
//		bg = ScreenSaver.getMonitorScreenShotAtCursor();
		bg = ScreenManager.getManager().getCurrentMonitorImage();
		repaint();
		
		//��ũ���� �׽�Ʈ
		//try {
		//	bg = ScreenSaver.getMonitorScreenShotAtCursor();
		//	ImageIO.write(bg, "png", new File("test.png"));
		//}catch(Exception e) {e.printStackTrace();}
	}
	
	private Curve curve;
	private Text text;
	
	private void init(){
		//��ũ�� ����
		screen();
		keybind();
	}
	
	private void screen(){
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setFocusTraversalKeysEnabled(false);
		
		addWindowListener(windowAdapter);
		
		//test code
		//setBackground(Color.yellow);
		//System.out.println("size = "+this.getBounds());
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			exit();
		}
	};
	
	private MouseMotionEvt motionEvt = new MouseMotionEvt();
	private MouseEvt mouseEvt = new MouseEvt();
	private MouseWheelEvt wheelEvt = new MouseWheelEvt();
	private KeyEvt keyEvt = new KeyEvt();
	
	private void eventunbind(){
		removeMouseMotionListener(motionEvt);
		removeMouseListener(mouseEvt);
		removeMouseWheelListener(wheelEvt);
		getContentPane().removeKeyListener(keyEvt);
	}
	private void eventbind(){
		//�̺�Ʈ ����
		//WindowUtility.setWindowOpacity(this, 60/100f);
		addMouseMotionListener(motionEvt);
		addMouseListener(mouseEvt);
		addMouseWheelListener(wheelEvt);
		getContentPane().addKeyListener(keyEvt);
	}
	private void prepare(){
		setBounds(ScreenManager.getManager().getCurrentMonitorRect());
		getContentPane().setCursor(CursorManager.createCircleCursor());
		screenData = new ScreenData();
		curve = new Curve();
		text = new Text();
		screenPainter = new ScreenPainter(this);
	}
	private void clear(){
		screenData = null;
		curve = null;
		text = null;
		screenPainter.kill();
		screenPainter = null;
	}
	
	/**
	 * key binding method
	 */
	private void keybind(){
		ActionMap actionMap = ((JPanel)this.getContentPane()).getActionMap();
		InputMap inputMap = ((JPanel)this.getContentPane()).getInputMap();
		
		/* input map setting */
		inputMap.put(KeyManager.esc, "esc");
		inputMap.put(KeyManager.backspace, "backspace");
		inputMap.put(KeyManager.ctrl_s, "ctrl_s");
		inputMap.put(KeyManager.ctrl_z, "ctrl_z");
		inputMap.put(KeyManager.ctrl_r, "ctrl_r");
		inputMap.put(KeyManager.ctrl_y, "ctrl_y");
		inputMap.put(KeyManager.t, "t");
		inputMap.put(KeyManager.c, "c");
		inputMap.put(KeyManager.f1, "f1");
		inputMap.put(KeyManager.f2, "f2");
		inputMap.put(KeyManager.f3, "f3");
		inputMap.put(KeyManager.f4, "f4");
		inputMap.put(KeyManager.f5, "f5");
		inputMap.put(KeyManager.f6, "f6");
		inputMap.put(KeyManager.f7, "f7");
		inputMap.put(KeyManager.f8, "f8");
		inputMap.put(KeyManager.f9, "f9");
		inputMap.put(KeyManager.f10, "f10");
		
		
		/* action map setting */
		actionMap.put("esc", new AbstractAction(){
			private static final long serialVersionUID = 1587852735003918298L;
			public void actionPerformed(ActionEvent e) {
				if(isDefaultMode()) {
					screenData.clear();
					exit();
					return;
				}
				
				switch(mode) {
				case TEXT_MODE:
					text.finish();
					break;
				case ICON_MODE:
					selectedIcon = null;
					break;
				case CHOICE_MODE:
					screenData.clearChoice();
					break;
				}
				restoreDefaultMode();
			}
		});
				
		actionMap.put("backspace", new AbstractAction(){
			private static final long serialVersionUID = -1895194402565202987L;
			public void actionPerformed(ActionEvent e) {
				if(isTextMode()){
					text.undo();
				}
			}
		});
		actionMap.put("ctrl_s", new AbstractAction(){
			private static final long serialVersionUID = -1895194402565202987L;
			public void actionPerformed(ActionEvent e) {
				try {
					saveScreen();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		actionMap.put("ctrl_z", new AbstractAction(){
			private static final long serialVersionUID = 3866328194655477517L;
			public void actionPerformed(ActionEvent e) {
				screenData.undo();
			}
		});
		actionMap.put("ctrl_r", new AbstractAction(){
			private static final long serialVersionUID = -5201116251074061469L;
			public void actionPerformed(ActionEvent e) {
				if(!isTextMode()) {
					screenData.recovery();
				}
			}
		});
		actionMap.put("ctrl_y", new AbstractAction() {
			private static final long serialVersionUID = -6354565020879453633L;
			@Override
			public void actionPerformed(ActionEvent e) {
				screenData.redo();
			}
		});
		actionMap.put("f1", new AbstractAction(){
			private static final long serialVersionUID = -5963057482921099056L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(1);
				setCursor();
			}
		});
		actionMap.put("f2", new AbstractAction(){
			private static final long serialVersionUID = -6354565020879453633L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(2);
				setCursor();
			}
		});
		actionMap.put("f3", new AbstractAction(){
			private static final long serialVersionUID = 2044616271891732949L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(3);
				setCursor();
			}
		});
		actionMap.put("f4", new AbstractAction(){
			private static final long serialVersionUID = -808999856814236056L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(4);
				setCursor();
			}
		});
		actionMap.put("f5", new AbstractAction(){
			private static final long serialVersionUID = -2554852532055970687L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(5);
				setCursor();
			}
		});
		actionMap.put("f6", new AbstractAction(){
			private static final long serialVersionUID = 3104526927899384109L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(6);
				setCursor();
			}
		});
		actionMap.put("f7", new AbstractAction(){
			private static final long serialVersionUID = 3733474738823690329L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(7);
				setCursor();
			}
		});
		actionMap.put("f8", new AbstractAction(){
			private static final long serialVersionUID = 18424280218463845L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(8);
				setCursor();
			}
		});
		actionMap.put("f9", new AbstractAction(){
			private static final long serialVersionUID = 5579777624382249446L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(9);
				setCursor();
			}
		});
		actionMap.put("f10", new AbstractAction(){
			private static final long serialVersionUID = -330567895445130724L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(0);
				setCursor();
			}
		});
		actionMap.put("t", new AbstractAction(){
			private static final long serialVersionUID = -4139956715708260428L;
			public void actionPerformed(ActionEvent e) {
				if(isDefaultMode()){
					mode = TEXT_MODE;
					setCursor();
				}
			}
		});
		actionMap.put("c", new AbstractAction(){
			private static final long serialVersionUID = -4139956715708260428L;
			public void actionPerformed(ActionEvent e) {
				if(!isTextMode()) {
					screenData.clearShape();
				}
			}
		});
	}
	
	private void setCursor() {
		switch(mode) {
		case TEXT_MODE:
			this.getContentPane().setCursor(CursorManager.createTextCursor());			
			break;
		case DRAWING_MODE:
			this.getContentPane().setCursor(CursorManager.createCircleCursor());			
			break;
		case ICON_MODE:
			break;
		case CHOICE_MODE:
			this.getContentPane().setCursor(CursorManager.createGrabCursor());
			break;
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	private Image backImg;
	private Graphics backScreen;
	
	@Override
	public void paint(Graphics g) {
		drawScreen();	
		g.drawImage(backImg, 0, 0, this.getContentPane());
	}
	
	/**
	 * backScreen�� ȭ���� �׸��� �޼ҵ�
	 */
	private void drawScreen(){
		prepareScreen();
		drawBackground();
		drawTempShape();
		drawShapelist();
	}
	
	private void prepareScreen() {
		if(backImg == null){
			backImg = createImage(getWidth(), getHeight());
			backScreen = backImg.getGraphics();
		}else{
			backScreen.clearRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private void drawBackground(){
		if(bg == null) return;
		backScreen.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}
	
	private boolean isDragged = false;
	
	private Set<Integer> pressedKey = new HashSet<>();
	/**
	 * �巡�� ������ ��� �ӽ� ������ �׸��� �޼ҵ�
	 * ������ ���� : screenData.start
	 * ������ ���� : screenData.end
	 * �巡�� �÷��� : isDragged
	 */
	private Shape tempShape;
	private void drawTempShape(){
//		System.out.println("isDragged = "+isDragged+", "+screenData.isTempShapeDrawable());
		if(isDragged && screenData.isTempShapeDrawable()){
			Graphics2D g2d = (Graphics2D)backScreen;
			
			tempShape = ShapeFactory.createShape(pressedKey, screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColor());
			if(tempShape == null){
				if(isTextMode()) 
					tempShape = text;
				else{
					tempShape = curve;
				}
			}
			
			tempShape.draw2(g2d);
		}
	}
	
	/**
	 * �ӽ� ������ �����ϴ� �޼ҵ�
	 */
	private void saveShape(){
		if(!screenData.isTempShapeDrawable()) return;
		
		Shape shape = ShapeFactory.createShape(pressedKey, screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColor());
		if(shape == null){
			switch(mode) {
			case TEXT_MODE:
				shape = text; 
				break;
			case ICON_MODE:
				shape = selectedIcon.copy(); 
				break;
			default:
				shape = curve;
				break;
			}
		}
		screenData.addShape(shape);
		tempShape = null;
		if(shape instanceof Curve){
			curve = new Curve();
		}
	}

	/**
	 * ����� ������ �׸��� �޼ҵ�
	 */
	private void drawShapelist(){
		if(screenData.isShapeEmpty()) return;
		Graphics2D g2d = (Graphics2D)backScreen;
		for(Shape s : screenData.getShapelist()){
			s.draw2(g2d);
		}
	}
	
	
	/**
	 * ȭ�� ���� �޼ҵ�
	 */
	private void saveScreen() throws AWTException{
		this.setKeyboardUnprevent();
		int sel = chooser.showSaveDialog(this);
		this.setKeyboardPrevent();
		if(sel != 0) return;

		chooser.saveImage(bg, this.getClass());
	}
	
	/**
	 * ��ũ���� Ű�̺�Ʈ�� ó���ϴ� Ŭ����
	 * @author Hwang
	 *
	 */
	class KeyEvt extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			int code = e.getKeyCode();
//			System.out.println(code);
//			System.out.println(Character.getType(e.getKeyChar()));
			switch(Character.getType(e.getKeyChar())) {
			
			/*���� ���ڿ� �̺з��� ���� ó��*/
			case 0:
			case Character.CONTROL:
				switch(code) {
				case KeyEvent.VK_CONTROL:
					return;
				case KeyEvent.VK_SHIFT:
					if(isDefaultMode()) {
						mode = CHOICE_MODE;
						setCursor();
					}
					break;
				case KeyEvent.VK_ENTER:
					if(isTextMode()) {
						text.append("\n");
					}
					break;
				}
			/*���� ���ڿ� ���� ó��*/
			case Character.SPACE_SEPARATOR:
				/* ����Ʈ �����̽��� ���� */
				if(isTextMode()) {
					if(e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK)
						return;
					
					switch(code) {
					case KeyEvent.VK_SPACE:
//					case KeyEvent.VK_ENTER:
						text.append(String.valueOf((char)code));
					}
				}
				break;
			/* �Ϲ� ����(��/�ҹ���, Ư������, ���� ��)�� ���� ó��*/
			case Character.LOWERCASE_LETTER:
			case Character.UPPERCASE_LETTER:
			case Character.TITLECASE_LETTER:
			case Character.DECIMAL_DIGIT_NUMBER:
			case Character.DASH_PUNCTUATION:
			case Character.START_PUNCTUATION:
			case Character.END_PUNCTUATION:
			case Character.CONNECTOR_PUNCTUATION:
			case Character.OTHER_PUNCTUATION:
			case Character.MATH_SYMBOL:
			case Character.CURRENCY_SYMBOL:
			case Character.MODIFIER_SYMBOL:
			case Character.OTHER_SYMBOL:
			case Character.INITIAL_QUOTE_PUNCTUATION:
			case Character.FINAL_QUOTE_PUNCTUATION:
//			default:
				/* �ؽ�Ʈ ����� ����� ó�� */
				if(isTextMode()) {
					text.append(String.valueOf(e.getKeyChar()));
				}
				/* ������ ó�� - ����Ű ����ҿ� ��� */
				else {
					pressedKey.add(code);
//					System.out.println(pressedKey);
				}
			}
				
		}
		@Override
		public void keyReleased(KeyEvent e) {
			pressedKey.remove(e.getKeyCode());
		}
	}
	private Icon selectedIcon;
	private List<Shape> choiceShapeList;
	class MouseEvt extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
//			System.out.println("mode = "+mode+", editable = "+screenData.isCursorDrawable()+", "+screenData.isTempShapeDrawable());
			if(e.getButton() == MouseEvent.BUTTON3){
				selectedIcon = IconManager.showIconDialog(DrawingFrame.this);
				if(selectedIcon == null) return;
				getContentPane().setCursor(CursorManager.createIconCursor(selectedIcon));
				mode = ICON_MODE;
				return;
			}
			
			switch(mode) {
			
			case TEXT_MODE:
				screenData.setStart(e.getX(), e.getY());
				int end_x = DrawingFrame.this.getX()+DrawingFrame.this.getWidth();
				int end_y = DrawingFrame.this.getY()+DrawingFrame.this.getHeight();
				screenData.setEnd(end_x, end_y);
				if(text != null) 
					text.finish();
				text = new Text(screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColorCopy(), "", options.getFontCopy());
				break;
				
			case ICON_MODE:
				
				break;
				
			case CHOICE_MODE:
				choiceShapeList = screenData.findShape(e.getX(), e.getY());
				break;
				
			case DRAWING_MODE:
			default:
				screenData.setStart(e.getX(), e.getY());
				screenData.setEnd(e.getX(), e.getY());
				curve = new Curve();
				isDragged = true;
				break;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			isDragged = false;
			switch(mode) {
			case CHOICE_MODE:
				choiceShapeList.clear();
				choiceShapeList = null;
				break;
			case ICON_MODE:
				if(selectedIcon != null) {
					selectedIcon.setSx(e.getX());
					selectedIcon.setSy(e.getY());
					selectedIcon.setSize(options.getIconSize());
				}
			case DRAWING_MODE:
			case TEXT_MODE:
				saveShape();
				screenData.clearStart();
				screenData.clearEnd();
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			isDragged = false;
			screenData.clearCursor();
		}
	}
	
	class MouseWheelEvt implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			switch(mode) {
			case TEXT_MODE:
				if(e.getWheelRotation() > 0) {
					options.decreaseFontSize();
				}else if(e.getWheelRotation() < 0) {
					options.increaseFontSize();
				}
				getContentPane().setCursor(CursorManager.createTextCursor());
				break;
			case ICON_MODE:
				if(e.getWheelRotation() > 0) {
					options.decreaseIconSize();
				}else if(e.getWheelRotation() < 0) {
					options.increaseIconSize();
				}
				getContentPane().setCursor(CursorManager.createIconCursor(selectedIcon));
				break;
			case CHOICE_MODE:
				break;
			case DRAWING_MODE:
				if(!isDragged) {
					if(e.getWheelRotation() > 0){
						options.decreasePointThickness();
					}
					else if(e.getWheelRotation() < 0){
						options.increasePointThickness();
					}
					getContentPane().setCursor(CursorManager.createCircleCursor());
				}
			}
		}
	}
	
	class MouseMotionEvt extends MouseAdapter{
		@Override
		public void mouseMoved(MouseEvent e) {
			switch(mode) {
			case CHOICE_MODE:
				List<Shape> list = screenData.findShape(e.getX(), e.getY());
//				System.out.println(list.size());
			}
			screenData.setCursor(e.getX(), e.getY());
			screenData.setStart(e.getX(), e.getY());
			//System.out.println(screenData);
		}
		
		private int oldX = -1, oldY = -1;
		@Override
		public void mouseDragged(MouseEvent e) {
			oldX = screenData.getCursor().x;
			oldY = screenData.getCursor().y;
			screenData.setCursor(e.getX(), e.getY());
			screenData.setEnd(e.getX(), e.getY());
			switch(mode) {
			case DRAWING_MODE:
				if(pressedKey.isEmpty()) {
						curve.add(e.getX(), e.getY(), options.getPointThickness(), options.getPointColor());	
				}
				break;
			case CHOICE_MODE:
				if(choiceShapeList != null) {
					for(Shape s : choiceShapeList) {
						s.move(e.getX() - oldX, e.getY() - oldY);
					}
				}
			}
				
		}
	}

}
