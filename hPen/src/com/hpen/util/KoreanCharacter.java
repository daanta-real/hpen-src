package com.hpen.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KoreanCharacter {
	private static final ArrayList<Character> a1 = new ArrayList<>();
	public static final char getS(int index){ return a1.get(index); }
	static{
		a1.add('ㄱ');/*ㄱ*/		a1.add('ㄲ');/*ㄲ*/		a1.add('ㄴ');/*ㄴ*/		a1.add('ㄷ');/*ㄷ*/
		a1.add('ㄸ');/*ㄸ*/		a1.add('ㄹ');/*ㄹ*/		a1.add('ㅁ');/*ㅁ*/		a1.add('ㅂ');/*ㅂ*/
		a1.add('ㅃ');/*ㅃ*/		a1.add('ㅅ');/*ㅅ*/		a1.add('ㅆ');/*ㅆ*/		a1.add('ㅇ');/*ㅇ*/
		a1.add('ㅈ');/*ㅈ*/		a1.add('ㅉ');/*ㅉ*/		a1.add('ㅊ');/*ㅊ*/		a1.add('ㅋ');/*ㅋ*/
		a1.add('ㅌ');/*ㅌ*/		a1.add('ㅍ');/*ㅍ*/		a1.add('ㅎ');/*ㅎ*/
	}
	private static final ArrayList<Character> a2 = new ArrayList<>();
	public static final char getM(int index){ return a2.get(index); }
	static{
		a2.add('ㅏ');/*ㅏ*/		a2.add('ㅐ');/*ㅐ*/		a2.add('ㅑ');/*ㅑ*/		a2.add('ㅒ');/*ㅒ*/
		a2.add('ㅓ');/*ㅓ*/		a2.add('ㅔ');/*ㅔ*/		a2.add('ㅕ');/*ㅕ*/		a2.add('ㅖ');/*ㅖ*/
		a2.add('ㅗ');/*ㅗ*/		a2.add('ㅘ');/*ㅘ*/		a2.add('ㅙ');/*ㅙ*/		a2.add('ㅚ');/*ㅚ*/
		a2.add('ㅛ');/*ㅛ*/		a2.add('ㅜ');/*ㅜ*/		a2.add('ㅝ');/*ㅝ*/		a2.add('ㅞ');/*ㅞ*/
		a2.add('ㅟ');/*ㅟ*/		a2.add('ㅠ');/*ㅠ*/		a2.add('ㅡ');/*ㅡ*/		a2.add('ㅢ');/*ㅢ*/
		a2.add('ㅣ');/*ㅣ*/
	}
	private static final ArrayList<Character> a3 = new ArrayList<>();
	public static final char getE(int index){ return a3.get(index); }
	static{
		a3.add((char)0x0000);/*없음*/	a3.add('ㄱ');/*ㄱ*/
		a3.add('ㄲ');/*ㄲ*/		a3.add('ㄳ');/*ㄳ*/		a3.add('ㄴ');/*ㄴ*/		a3.add('ㄵ');/*ㄵ*/
		a3.add('ㄶ');/*ㄶ*/		a3.add('ㄷ');/*ㄷ*/		a3.add('ㄹ');/*ㄹ*/		a3.add('ㄺ');/*ㄺ*/
		a3.add('ㄻ');/*ㄻ*/		a3.add('ㄼ');/*ㄼ*/		a3.add('ㄽ');/*ㄽ*/		a3.add('ㄾ');/*ㄾ*/
		a3.add('ㄿ');/*ㄿ*/		a3.add('ㅀ');/*ㅀ*/		a3.add('ㅁ');/*ㅁ*/		a3.add('ㅂ');/*ㅂ*/
		a3.add('ㅄ');/*ㅄ*/		a3.add('ㅅ');/*ㅅ*/		a3.add('ㅆ');/*ㅆ*/		a3.add('ㅇ');/*ㅇ*/
		a3.add('ㅈ');/*ㅈ*/		a3.add('ㅊ');/*ㅊ*/		a3.add('ㅋ');/*ㅋ*/		a3.add('ㅌ');/*ㅌ*/
		a3.add('ㅍ');/*ㅍ*/		a3.add('ㅎ');/*ㅎ*/
	}
	
	/**
	 * @param ascii	아스키 코드 1개(알파벳)
	 * @return 유니 코드 1개(한글), 매칭되지 않을 경우 INVALID_CHARACTER 반환
	 * 1개씩 입력되는 한글 자/모음을 조합하는 클래스
	 *	예를들어 dkssudgktpdy라면 
	 * d = ㅇ		k = ㅏ		s = ㄴ
	 * s = ㄴ		u = ㅕ		d = ㅇ
	 * g = ㅎ		k = ㅏ		
	 * t = ㅅ		p = ㅔ		
	 * d = ㅇ		y = ㅛ
	 * 로 변환하여 합쳐야 한다. 문제는 종성이 있는 경우도 있고 없는 경우도 있는데 이를 구별해야 한다는 것<br>
	 * 자음 알파벳 : qwertasdfgzxcv
	 * 모음 알파벳 : yuiophjklbnm
	 * 
	 * 합성 규칙
	 * 1.직전 글자가 없을 경우 새글자
	 * 2.직전 글자가 있을 경우
	 * 	(1) 입력 글자가 자음이면
	 * 		[1] 직전 글자가 자음이면 새글자
	 * 		[2] 직전 글자가 모음이면 받침 또는 새글자(다음 글자를 확인할 필요가 있다)
	 * 	(2) 입력 글자가 모음이면
	 * 		[1] 직전 글자가 자음이면 병합
	 * 		[2] 직전 글자가 모음이면 새글자
	 */
	
	public static final int INVALID_CHARACTER = -1;
	public static int parseKorean(int ascii){
		switch(ascii){
		//자음
		case 'Q': return 'ㅃ';		case 'W': return 'ㅉ';		case 'E': return 'ㄸ';
		case 'R': return 'ㄲ';		case 'T': return 'ㅆ';		case 'q': return 'ㅂ';
		case 'w': return 'ㅈ';		case 'e': return 'ㄷ';		case 'r': return 'ㄱ';
		case 't' : return 'ㅅ';					case 'A': case 'a': return 'ㅁ';		
		case 'S': case 's': return 'ㄴ';		case 'D': case 'd': return 'ㅇ';		
		case 'F': case 'f': return 'ㄹ';		case 'G': case 'g': return 'ㅎ';
		case 'Z': case 'z': return 'ㅋ';		case 'X': case 'x': return 'ㅌ';		
		case 'C': case 'c': return 'ㅊ';		case 'V': case 'v': return 'ㅍ';
		//모음
		case 'Y': case 'y': return 'ㅛ';		case 'U': case 'u': return 'ㅕ';		
		case 'I': case 'i': return 'ㅑ';
		case 'o': return 'ㅐ';		case 'p': return 'ㅔ';		
		case 'H': case 'h': return 'ㅗ';		case 'J': case 'j': return 'ㅓ';			
		case 'K': case 'k': return 'ㅏ';		case 'L': case 'l': return 'ㅣ';
		case 'B': case 'b': return 'ㅠ';		case 'N': case 'n': return 'ㅜ';		
		case 'M': case 'm': return 'ㅡ';
		case 'O':return 'ㅒ';			case 'P': return 'ㅖ';
		//예외
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
	 * @param korean 한글 1글자
	 * @return 자음이면 true, 아니면 false
	 */
	public static boolean isJaum(int korean){
		return korean >= 'ㄱ' && korean <= 'ㅎ';
	}
	
	public static boolean isFirstLetter(int korean){
		return a1.indexOf((char)korean) >= 0;
	}
	/**
	 * @param korean 한글 1글자
	 * @return 모음이면 true, 아니면 false
	 */
	public static boolean isMoum(int korean){
		return korean >= 'ㅏ' && korean <= 'ㅣ';
	}
	
	public static final char[][] doubleJaum = new char[][]{
		{'ㄱ','ㅅ','ㄳ'},{'ㄴ','ㅈ','ㄵ'},{'ㄴ','ㅎ','ㄶ'},{'ㄹ','ㄱ','ㄺ'},{'ㄹ','ㅁ','ㄻ'},{'ㄹ','ㅂ','ㄼ'},
		{'ㄹ','ㅅ','ㄽ'},{'ㄹ','ㅌ','ㄾ'},{'ㄹ','ㅍ','ㄿ'},{'ㄹ','ㅎ','ㅀ'},{'ㅂ','ㅅ','ㅄ'}
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
		{'ㅗ','ㅏ','ㅘ'},{'ㅗ','ㅐ','ㅙ'},{'ㅗ','ㅣ','ㅚ'},{'ㅜ','ㅓ','ㅝ'},{'ㅜ','ㅔ','ㅞ'},{'ㅜ','ㅣ','ㅟ'},{'ㅡ','ㅣ','ㅢ'}
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
		return Pattern.matches("[ㄱ-ㅎㅏ-ㅣ가-힣]+", String.valueOf(ch));
	}
	public static String seperateString(String sample) {
		for(int i=0; i<sample.length(); i++){
			char ch = sample.charAt(i);
			if(isKoreanLetter(ch)){
				if(!isJaum(ch) && !isMoum(ch)){//온전한 글자
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
//		System.out.println("분해 결과 : "+sample);
		return sample;
	}
	public static String mergeString(String text) throws Exception{
		Matcher m = Pattern.compile("[ㄱ-ㅎ]([ㅏ-ㅣ]{1,2})?").matcher(text);
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
//					System.out.println("위치 : "+text.indexOf(s) + "\t" + s + " → " + d);
					text = text.replace(s, String.valueOf(d));
				}
			}
		}
//		System.out.println("조립 결과(1차) : "+text);
		m = Pattern.compile("[가-힣][ㄱ-ㅎ]{1,2}[ㅏ-ㅣ]?").matcher(text);
		while(m.find()){
			String s = m.group();
			if(s.length() == 4){
				if(isMoum(s.charAt(3))){//ㄱㅏㅂㅅㅣ → 갑시 로 만들기 위한 조건
					char a = s.charAt(0);
					char b = s.charAt(1);
					char c = s.charAt(2);
					char d = s.charAt(3);
					int[] v = seperate(a);
					char r1 = merge((char)getS(v[0]), (char)getM(v[1]), b); 
					char r2 = merge(c, d);//뒷글자
					text = text.replace(s, ""+r1+r2);
				}
			}else if(s.length() == 3){//ㄱㅏㅂㅅ → 값으로 만들기 위한 조건
				char a = s.charAt(0);
				char b = s.charAt(1);
				char c = s.charAt(2);
				if(isDoubleJaum(b, c)){
					int[] v = seperate(a);
					char d = merge((char)getS(v[0]), (char)getM(v[1]), b, c);
					text = text.replace(s, String.valueOf(d));
				}
			}else{//length == 2, 일반 받침 글자
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
//		System.out.println("조립 결과(최종) : "+text);
		return text;
	}
	public static boolean isLastLetter(char b) {
		return a3.contains(b);
	}
}
