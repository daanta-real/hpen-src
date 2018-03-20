package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.hpen.property.DrawingOption;
import com.hpen.util.KoreanCharacter;

public class Text extends Rect{
	
	private static final int fix = 10;//����ġ
	
	private StringBuffer textBuffer = new StringBuffer();
	private StringBuffer ingBuffer = new StringBuffer();
	private Font font;
	private boolean isEdit = false;
	
	public String getText() {
		return textBuffer.toString();
//		return textBuffer.toString().concat(ingBuffer.toString());
	}

	public void setText(String text) {
		textBuffer = new StringBuffer(text);
	}
	
	public void append(String str){
//		[�������] ���� ����(textBuffer)�� ���� �߰�.. �Ѿ�� ���ƿ��� ������
		if(DrawingOption.getInstance().isKorean()) {
			str = KoreanCharacter.parseKorean(str);
		}
		textBuffer.append(str);
		
//		[������] �޸���ó�� �Է¹���(ingBuffer)�� �߰�.. �Ѿ�� ���� �Ұ�
//		if(DrawingOption.isKorean()) {
//			str = KoreanCharacter.parseKorean(str);
//			ingBuffer.append(str);
//		}
//		else{
//			clearIngBuffer();
//		}
		
		try{
			renew();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	private void clearIngBuffer() {
//		textBuffer.append(ingBuffer);
//		ingBuffer = new StringBuffer();
//	}
	
	private void renew() throws Exception{
//		[�������] ��ü�� �������Ͽ� �ѱ۷� ��ȯ
		int index = Math.max(textBuffer.lastIndexOf("\n"), textBuffer.lastIndexOf(" "));
		StringBuffer nonFix = new StringBuffer();
		String fix;
		if(index > -1){
			nonFix.append(textBuffer.substring(0, index+1));
			fix = textBuffer.substring(index+1);
		}else{
			fix = textBuffer.toString();
		}
		
		String tmp= KoreanCharacter.seperateString(fix);
		String fixText = KoreanCharacter.mergeString(tmp);
		textBuffer = new StringBuffer(nonFix.append(fixText));
		
//		[������] ���ۿ� �ش��ϴ� ���ڸ� �������Ͽ� ����
//		String convert = KoreanCharacter.mergeString(ingBuffer.toString());
//		System.out.print("textBuffer = "+textBuffer.toString());
//		System.out.print(",\t\tingBuffer = "+ingBuffer.toString());
//		System.out.println(",\t\tconvert = "+convert);
//		ingBuffer = new StringBuffer(convert);
//		if(convert.length() > 1) {
//			textBuffer.append(convert.charAt(0));
//			ingBuffer.deleteCharAt(0);
//		}
		
	}
	
	public void undo(){
		if(textBuffer.length() > 0)
			textBuffer.deleteCharAt(textBuffer.length()-1);
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Text(){
		this(new Point(-1, -1), new Point(-1, -1), DrawingOption.getInstance().getPointThickness(), DrawingOption.getInstance().getPointColor(), "", DrawingOption.getInstance().getFont());
	}
	public Text(Point s, Point e, int thick, Color color, String text, Font font) {
		this(s.x , s.y, e.x, e.y, thick, color, text, font);
	}
	
	public Text(int sx, int sy, int ex, int ey, int thick, Color color, String text, Font font) {
		super(sx - fix, sy - fix, ex - fix, ey - fix, thick, color);
		this.setText(text);
	    this.setFont(font);
		isEdit = true;
	}

	public void finish(){
		isEdit = false;
	}
	
	@Override
	public boolean isSamePosition(Point p) {
		return p.x >= sx && p.x <= ex && p.y >= sy && p.y <= ey;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setFont(font);
		//\n�� ���� ���� ó��
		String text = getText();
		if(!isEdit && text.trim().length() == 0) return;
		int x = sx;
		int y = sy;
		
		int a = 0;
		int len = text.split("\n").length;
		boolean enterFlag = text.endsWith("\n");
		for(String line : text.split("\n")){
			a++;
			if(isEdit && a == len && !enterFlag){
				g2d.drawString(line+"|", x, y += g2d.getFontMetrics().getHeight());
			}else if(isEdit && a == len && enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
				g2d.drawString("|", x, y += g2d.getFontMetrics().getHeight());
			}else if(!isEdit && a == len && !enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}else if(!isEdit && a == len && enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}else{
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}
		}
	}

}
