package com.hpen.update.subutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>���� �Ŵ���</h1>
 * @author HWANG
 */
public class FileManager {
	private static FileManager manager = new FileManager();
	public static FileManager getManager() {
		return manager;
	}
	
	private FileManager() {
		
	}
	
	/**
	 * ������ ������ ����ִ� ����/������ ��ȯ(���������� ����)
	 * @param path ������ ���
	 * @return ���� ����Ʈ
	 * @throws FileNotFoundException ������ ������ �������� ���� ���
	 */
	public List<File> getFileList(String path) throws FileNotFoundException{
		File target = new File(path);
		return getFileList(target);
	}
	
	/**
	 * ������ ������ ����ִ� ����/������ ��ȯ(���������� ����)
	 * @param target ������ ��ü
	 * @return ���� ����Ʈ
	 * @throws FileNotFoundException ������ ������ �������� ���� ���
	 */
	public List<File> getFileList(File target) throws FileNotFoundException{
		if(!target.exists())
			throw new FileNotFoundException("��� ����/������ �������� �ʽ��ϴ�");
		List<File> list = new ArrayList<>();
		if(target.isFile()) {
			list.add(target);
		}else if(target.isDirectory()){
			File[] files = target.listFiles();
			if(files != null) {
				for(File f : files) {
					list.add(f);
				}
			}
		}
		return list;
	}
	
	/**
	 * ���� ������ ������ ��� ������ ������ ��ȯ�ϴ� �޼ҵ�
	 * @param path ���� ���
	 * @return ���� ����Ʈ
	 * @throws FileNotFoundException ������ ������ �������� ���� ���
	 */
	public List<File> getAllFileList(String path) throws FileNotFoundException{
		File target = new File(path);
		return getAllFileList(target);
	}
	
	/**
	 * ���� ������ ������ ��� ������ ������ ��ȯ�ϴ� �޼ҵ�
	 * @param target ���� ���� ��ü
	 * @return ���� ����Ʈ
	 * @throws FileNotFoundException ������ ������ �������� ���� ���
	 */
	public List<File> getAllFileList(File target) throws FileNotFoundException{
		if(!target.exists())
			throw new FileNotFoundException("��� ����/������ �������� �ʽ��ϴ�");
		List<File> list = new ArrayList<>();
		getAllFileListProc(list, target);
		return list;
	}
	
	/**
	 * getAllFileList�� ���� ���� �ڵ�(���ȣ��� ����)
	 * @param list ���� ��ü�� �߰��� list ����
	 * @param target ��� ���� ��ü
	 */
	private void getAllFileListProc(List<File> list, File target) {
		if(target.isDirectory()) {
			list.add(target);
			File[] files = target.listFiles();
			if(files != null) {
				for(File f : files) {
					getAllFileListProc(list, f);
				}
			}
		}
		else if(target.isFile()) {
			list.add(target);
		}
	}
	
	private byte[] buffer = new byte[2048];
	public File copy(File target, File dest) throws IOException{
		if(target.isFile()) {
			if(!dest.exists())
				dest.mkdirs();
			File nFile = new File(dest, target.getName());
			try(
				FileOutputStream out = new FileOutputStream(nFile);
				FileInputStream in = new FileInputStream(target);
			) {
				while(true) {
					int s = in.read(buffer);
					if(s == -1) break;
					out.write(buffer, 0, s);
				}
			}
			return nFile;
		}else if(target.isDirectory()) {
			File[] list = target.listFiles();
			if(list != null) {
				for(File f : list) {
					copy(f, dest);
				}
			}
			return target;
		}
		return null;
	}
}
