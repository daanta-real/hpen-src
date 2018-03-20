package com.hpen.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KoreanCharacter {
	private static final ArrayList<Character> a1 = new ArrayList<>();
	public static final char getS(int index){ return a1.get(index); }
	static{
		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/
		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/
		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/
		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/
		a1.add('��');/*��*/		a1.add('��');/*��*/		a1.add('��');/*��*/
	}
	private static final ArrayList<Character> a2 = new ArrayList<>();
	public static final char getM(int index){ return a2.get(index); }
	static{
		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/
		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/
		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/
		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/
		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/		a2.add('��');/*��*/
		a2.add('��');/*��*/
	}
	private static final ArrayList<Character> a3 = new ArrayList<>();
	public static final char getE(int index){ return a3.get(index); }
	static{
		a3.add((char)0x0000);/*����*/	a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/		a3.add('��');/*��*/
		a3.add('��');/*��*/		a3.add('��');/*��*/
	}
	
	/**
	 * @param ascii	�ƽ�Ű �ڵ� 1��(���ĺ�)
	 * @return ���� �ڵ� 1��(�ѱ�), ��Ī���� ���� ��� INVALID_CHARACTER ��ȯ
	 * 1���� �ԷµǴ� �ѱ� ��/������ �����ϴ� Ŭ����
	 *	������� dkssudgktpdy��� 
	 * d = ��		k = ��		s = ��
	 * s = ��		u = ��		d = ��
	 * g = ��		k = ��		
	 * t = ��		p = ��		
	 * d = ��		y = ��
	 * �� ��ȯ�Ͽ� ���ľ� �Ѵ�. ������ ������ �ִ� ��쵵 �ְ� ���� ��쵵 �ִµ� �̸� �����ؾ� �Ѵٴ� ��<br>
	 * ���� ���ĺ� : qwertasdfgzxcv
	 * ���� ���ĺ� : yuiophjklbnm
	 * 
	 * �ռ� ��Ģ
	 * 1.���� ���ڰ� ���� ��� ������
	 * 2.���� ���ڰ� ���� ���
	 * 	(1) �Է� ���ڰ� �����̸�
	 * 		[1] ���� ���ڰ� �����̸� ������
	 * 		[2] ���� ���ڰ� �����̸� ��ħ �Ǵ� ������(���� ���ڸ� Ȯ���� �ʿ䰡 �ִ�)
	 * 	(2) �Է� ���ڰ� �����̸�
	 * 		[1] ���� ���ڰ� �����̸� ����
	 * 		[2] ���� ���ڰ� �����̸� ������
	 */
	
	public static final int INVALID_CHARACTER = -1;
	public static int parseKorean(int ascii){
		switch(ascii){
		//����
		case 'Q': return '��';		case 'W': return '��';		case 'E': return '��';
		case 'R': return '��';		case 'T': return '��';		case 'q': return '��';
		case 'w': return '��';		case 'e': return '��';		case 'r': return '��';
		case 't' : return '��';					case 'A': case 'a': return '��';		
		case 'S': case 's': return '��';		case 'D': case 'd': return '��';		
		case 'F': case 'f': return '��';		case 'G': case 'g': return '��';
		case 'Z': case 'z': return '��';		case 'X': case 'x': return '��';		
		case 'C': case 'c': return '��';		case 'V': case 'v': return '��';
		//����
		case 'Y': case 'y': return '��';		case 'U': case 'u': return '��';		
		case 'I': case 'i': return '��';
		case 'o': return '��';		case 'p': return '��';		
		case 'H': case 'h': return '��';		case 'J': case 'j': return '��';			
		case 'K': case 'k': return '��';		case 'L': case 'l': return '��';
		case 'B': case 'b': return '��';		case 'N': case 'n': return '��';		
		case 'M': case 'm': return '��';
		case 'O':return '��';			case 'P': return '��';
		//����
		default: return ascii;
		}
	}
	public static String parseKorean(String str){
		StringBuffer buffer = new StringBuffer();
		for(char ch : str.toCharArray()){
			buffer.append((char)parseKorean(ch));
		}
		return buffer.toString();
	}
	/**
	 * @param korean �ѱ� 1����
	 * @return �����̸� true, �ƴϸ� false
	 */
	public static boolean isJaum(int korean){
		return korean >= '��' && korean <= '��';
	}
	
	public static boolean isFirstLetter(int korean){
		return a1.indexOf((char)korean) >= 0;
	}
	/**
	 * @param korean �ѱ� 1����
	 * @return �����̸� true, �ƴϸ� false
	 */
	public static boolean isMoum(int korean){
		return korean >= '��' && korean <= '��';
	}
	
	public static final char[][] doubleJaum = new char[][]{
		{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},
		{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'}
	};
	public static boolean isDoubleJaum(int a, int b){
		for(int i=0; i<doubleJaum.length; i++){
			if((char)a == doubleJaum[i][0] && (char)b == doubleJaum[i][1])
				return true;
		}
		return false;
	}
	
	public static char mergeDoubleJaum(char c, char d) throws Exception{
		for(int i=0; i<doubleJaum.length; i++){
			if(doubleJaum[i][0] == c && doubleJaum[i][1] == d){
				return doubleJaum[i][2];
			}
		}
		throw new Exception();
	}
	
	public static char[] seperateJaum(char ch) throws Exception{
		for(int i=0; i<doubleJaum.length; i++){
			if(doubleJaum[i][2] == ch){
				return new char[]{doubleJaum[i][0], doubleJaum[i][1]};
			}
		}
		throw new Exception();
	}
	
	public static final char[][] doubleMoum = new char[][]{
		{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'},{'��','��','��'}
	};
	public static boolean isDoubleMoum(int a){
		for(int i=0; i<doubleMoum.length; i++){
			if((char)a2.get(a) == doubleMoum[i][2]){
				return true;
			}
		}
		return false;
	}
	public static boolean isDoubleMoum(int a, int b){
		for(int i=0; i<doubleMoum.length; i++){
			if((char)a == doubleMoum[i][0] && (char)b == doubleMoum[i][1])
				return true;
		}
		return false;
	}
	public static char mergeDoubleMoum(char c, char d) throws Exception{
		for(int i=0; i<doubleMoum.length; i++){
			if(doubleMoum[i][0] == c && doubleMoum[i][1] == d){
				return doubleMoum[i][2];
			}
		}
		throw new Exception();
	}
	public static char[] seperateMoum(int ch) throws Exception{
		for(int i=0; i<doubleMoum.length; i++){
			if(a2.get(ch) == doubleMoum[i][2]){
				return new char[]{doubleMoum[i][0], doubleMoum[i][1]};				
			}
		}
		throw new Exception();
	}
	public static char merge(int[] s) throws IllegalArgumentException{
		if (s.length != 3) throw new IllegalArgumentException();
		s[0] = a1.indexOf((char)s[0]);
		s[1] = a2.indexOf((char)s[1]);
		s[2] = a3.indexOf((char)s[2]);
		char c = (char)((((s[0] * 588) + s[1] * 28) + s[2]) + 44032);
		return c;
	}
	
	public static int[] seperate(char s){
		int[] result = new int[3];
		int a = s - 44032;
		result[0] = a / 588;
		result[1] = a / 28 % 21;
		result[2] = a % 28;
		return result;
	}
	
	public static char merge(char a, char b, char c, char d) throws Exception{
		char r = mergeDoubleJaum(c, d);
		return merge(a, b, r);
	}
	public static char merge(char a, char b, char c){
		int[] r = new int[]{a,b,c};
		return merge(r);
	}
	public static char merge(char a, char b){
		return merge(new int[]{a,b,0});
	}
	public static boolean isKoreanLetter(char ch){
		return Pattern.matches("[��-����-�Ӱ�-�R]+", String.valueOf(ch));
	}
	public static String seperateString(String sample) {
		for(int i=0; i<sample.length(); i++){
			char ch = sample.charAt(i);
			if(isKoreanLetter(ch)){
				if(!isJaum(ch) && !isMoum(ch)){//������ ����
					int[] s = seperate(ch);
					//System.out.println(a1.get(s[0])+", "+a2.get(s[1])+", "+a3.get(s[2]) +"("+s[2]+")");
					String endString = "";
					if(isDoubleMoum(s[1])){
						try{
							char[] res = seperateMoum((char) s[1]);
							endString = String.valueOf(res[0])+res[1]+(a3.get(s[2]) == 0x0000?"":String.valueOf(a3.get(s[2])));
						}catch(Exception e){
							e.printStackTrace();
						}
					}else{
						endString = a3.get(s[2]) == 0x0000?"":String.valueOf(a3.get(s[2]));
					}
					String replaceStr = String.valueOf(a1.get(s[0]))
														+(isDoubleMoum(s[1])?"":String.valueOf(a2.get(s[1])))
														+endString;
														
					sample = sample.replace(String.valueOf(ch), replaceStr);
				}
			}
		}
//		System.out.println("���� ��� : "+sample);
		return sample;
	}
	public static String mergeString(String text) throws Exception{
		Matcher m = Pattern.compile("[��-��]([��-��]{1,2})?").matcher(text);
		while(m.find()){
			String s = m.group();
			if(s.length() == 2){
				char a = s.charAt(0);
				char b = s.charAt(1);
				if(isJaum(a) && isMoum(b)){
					if(isFirstLetter(a)){
						char c = merge(a, b);
						text = text.replace(s, String.valueOf(c));
					}else{
						char[] r = seperateJaum(a);
						text = text.replace(s, ""+r[0]+r[1]+b);
					}
				}
			}else if(s.length() == 3){
				char a = s.charAt(0);
				char b = s.charAt(1);
				char c = s.charAt(2);
				if(isDoubleMoum(b, c)){
					char n = mergeDoubleMoum(b, c);
					char d = merge(a, n);
//					System.out.println("��ġ : "+text.indexOf(s) + "\t" + s + " �� " + d);
					text = text.replace(s, String.valueOf(d));
				}
			}
		}
//		System.out.println("���� ���(1��) : "+text);
		m = Pattern.compile("[��-�R][��-��]{1,2}[��-��]?").matcher(text);
		while(m.find()){
			String s = m.group();
			if(s.length() == 4){
				if(isMoum(s.charAt(3))){//���������� �� ���� �� ����� ���� ����
					char a = s.charAt(0);
					char b = s.charAt(1);
					char c = s.charAt(2);
					char d = s.charAt(3);
					int[] v = seperate(a);
					char r1 = merge((char)getS(v[0]), (char)getM(v[1]), b); 
					char r2 = merge(c, d);//�ޱ���
					text = text.replace(s, ""+r1+r2);
				}
			}else if(s.length() == 3){//�������� �� ������ ����� ���� ����
				char a = s.charAt(0);
				char b = s.charAt(1);
				char c = s.charAt(2);
				if(isDoubleJaum(b, c)){
					int[] v = seperate(a);
					char d = merge((char)getS(v[0]), (char)getM(v[1]), b, c);
					text = text.replace(s, String.valueOf(d));
				}
			}else{//length == 2, �Ϲ� ��ħ ����
				char a = s.charAt(0);
				char b = s.charAt(1);
				int[] v = seperate(a);
				if(isLastLetter(b)){
					char d = merge((char)a1.get(v[0]), (char)a2.get(v[1]), b);
					text = text.replace(s, String.valueOf(d));
				}else{
					text = text.replace(s, String.valueOf(a)+b);
				}
			}
		}
//		System.out.println("���� ���(����) : "+text);
		return text;
	}
	public static boolean isLastLetter(char b) {
		return a3.contains(b);
	}
}
