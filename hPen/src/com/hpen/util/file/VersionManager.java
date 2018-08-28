package com.hpen.util.file;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hpen.util.DialogManager;
import com.hpen.value.Version;

public class VersionManager {
	
	/**
	 * X.X.X ������ ������ 00000 ������ int ������ ��ȯ�ϴ� �޼ҵ�
	 * @param version	X.X.X ������ ���� ��
	 * @return	int ���� ��
	 * @throws IllegalArgumentException	���Ŀ� ���� �ʴ� ������ ���
	 */
	private static int convert(String version) throws IllegalArgumentException{
		String regex = "^\\d\\.\\d\\.\\d$";
		if(!version.matches(regex))
			throw new IllegalArgumentException("�߸��� ����");
		int version_int = 0;
		for(int i=0; i<version.length(); i++) {
			if(i % 2 == 0) {
				version_int += version.charAt(i) - '0';
			}
			version_int *= 10;
		}
		version_int /= 10;
		return version_int;
	}
	
	public static boolean isNew(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin > 0;
	}
	
	public static boolean isSame(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin == 0;
	}
	
	public static boolean isBefore(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin < 0;
	}
	
	public static String getLatestVersionOnGithub() {
		String result = null;
		try {
			Document document = Jsoup.connect("https://github.com/hiphop5782/hPen/releases").get();
			Element tag = document.select("div.label-latest div.text-normal a").get(0);
			String regex = "(\\d+\\.\\d+\\.\\d+)";
			Matcher m = Pattern.compile(regex).matcher(tag.text());
			if(m.find()) {
				result = m.group(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
//		System.out.println("result = "+result);
		return result;
	}
	
	public static final String download_message = 
			"<html>���� ������ ������Դϴ�<br><br>"
			+ "�ֽ� ������ �Ʒ� �ּҿ��� �ٿ������ �� �ֽ��ϴ�<br><br>"
			+ "https://github.com/hiphop5782/hPen/releases<br><br>"
			+ "�ֽ� ������ �ٿ�����ðڽ��ϱ�?<br><br></html>";
	public static void checkNewestVersionOnGithub() {
		String originVersion = Version.getInstance().getVersion();
//		System.out.println("originVersion = "+originVersion);
		String githubVersion = getLatestVersionOnGithub();
//		System.out.println("githubVersion = "+githubVersion);
		if(githubVersion == null) return;
		
		if(isNew(originVersion, githubVersion)) {
			int select = DialogManager.confirm(download_message);
			if(select == 0) {
				try {
					Runtime.getRuntime().exec("cmd.exe /c hpen_updater.exe");
				}catch(Exception e) {}
				System.exit(0);
			}
		}
	}
}
