package com.hpen.update;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import com.hpen.update.subutil.DownloadManager;
import com.hpen.update.subutil.ExecuteManager;
import com.hpen.update.subutil.ZipManager;
import com.hpen.util.file.VersionManager;
import com.hpen.value.Version;

public class UpdateProgram {
	public static void main(String[] args) {
		try {
//			if(args.length < 1) {
//				System.out.println("�Ű������� ��������");
//				System.out.println("[1] ������Ʈ�� ��������");
//				System.out.println("[2] ������Ʈ�� ��ġ�� ����(������ ��������)");
//				return;
//			}
//			String version = args[0];
//			String destFolderPath = args.length >= 2?args[1]:System.getProperty("user.dir");
			
			String latestVersion = VersionManager.getLatestVersionOnGithub();
			if(!VersionManager.before(latestVersion)) {
				System.out.println("�ֽ� ���� �����");
				System.exit(0);
			}
			String destFolderPath = System.getProperty("user.dir");
			File f = DownloadManager.getManager().download("https://github.com/hiphop5782/hPen/archive/master.zip", "hpen.zip");
			ZipManager.getManager().unzip(f.getAbsolutePath(), destFolderPath, true, true);
//			JOptionPane.showMessageDialog(null, "���α׷� ������Ʈ�� �Ϸ�Ǿ����ϴ�", "����� �˸�", JOptionPane.PLAIN_MESSAGE);
			ExecuteManager.getManager().execute("cmd.exe /c "+destFolderPath+File.separator+"hpen.exe");
			System.exit(0);
		}catch(Exception e) {
			try(PrintWriter p = new PrintWriter(new FileWriter(new File("error.log")))){
				e.printStackTrace(p);
			}catch(Exception err) {}
			JOptionPane.showMessageDialog(null, "������Ʈ ������ ������ �߻��߽��ϴ�", "����", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}
