package study;

public class Exam02_�����ڵ�м� {
	public static void main(String[] args) {
		int s = '��';
		int m = '��';
		int e = '��';
		System.out.println(s +", "+m +", "+e);
		int v = '��';
		System.out.println(v);
		System.out.println(v - 0xAC00);
		v -= 0xAC00;
		int vs = (((v - (v % 28)) / 28) / 21) + '��';
		System.out.println(vs);
		int vm = (((v - (v % 28)) / 28) % 21) + '��';
		System.out.println(vm);
		int ve = (v % 28) + '��' +1;
		System.out.println(ve);
		
		int vv = 0xAC00 + 28 * 21 * (vs - '��') + 28 * (vm - '��') + (ve - '��' -1);
		System.out.println(vv);
		
	}
}




