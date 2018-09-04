package com.hpen.util;

public class English2KoreanParser{
	private static English2KoreanParser parser = new English2KoreanParser();
	public static English2KoreanParser getParser() {
		return parser;
	}
	private English2KoreanParser() {}
	
	private String english = "rRseEfaqQtTdwWczxvgkoiOjpuPhynbml";
	private String korean = "���������������������������������������������¤äĤŤƤǤˤ̤ФѤ�";
	private String first = "��������������������������������������";
	private String second = "�������¤äĤŤƤǤȤɤʤˤ̤ͤΤϤФѤҤ�";
	private String third = "������������������������������������������������������";
	
	public String parse(String input) {

		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // �ʼ�, �߼�, ����

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			int p = english.indexOf(ch);
			if (p == -1) { // �������� �ƴ�
				// �����ִ� �ѱ��� ������ ó��
				if (nCho != -1) {
					if (nJung != -1) // �ʼ�+�߼�+(����)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // �ʼ���
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // �߼���
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // ������
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // ����
				if (nJung != -1) {
					if (nCho == -1) { // �߼��� �Էµ�, �ʼ�����
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // �����̴�
						if (nJong == -1) { // ���� �Է� ��
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // ������ �ƴ϶� �ʼ��̴�
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ��
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ��
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ��
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ��
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ��
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ��
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ��
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ��
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ��
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ��
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ��
							nJong = 17;
						} else { // ���� �Է� ��, �ʼ�����
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // �ʼ� �Ǵ� (��/��)�����̴�
					if (nCho == -1) { // �ʼ� �Է� ����
						if (nJong != -1) { // ������ �� �ʼ�
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ��
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ��
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ��
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ��
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ��
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ��
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ��
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ��
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ��
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ��
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ��
						nCho = -1;
						nJong = 17;
					} else { // �������� ��Ÿ
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // ����
				if (nJong != -1) { // (�ձ��� ����), �ʼ�+�߼�
					// ������ �ٽ� ����
					int newCho; // (�ӽÿ�) �ʼ�
					if (nJong == 2) { // ��, ��
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ��, ��
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ��, ��
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ��, ��
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ��, ��
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ��, ��
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ��, ��
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ��, ��
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ��, ��
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ��, ��
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ��, ��
						nJong = 16;
						newCho = 9;
					} else { // ������ �ƴ�
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // �ձ��ڰ� �ʼ�+�߼�+(����)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // �������� ����
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // �߼� �Է� ��
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ��
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ��
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ��
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ��
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ��
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ��
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ��
					nJung = 19;
				} else { // ���� �ȵǴ� ���� �Է�
					if (nCho != -1) { // �ʼ�+�߼� �� �߼�
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // �߼� �� �߼�
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// ������ �ѱ��� ������ ó��
		if (nCho != -1) {
			if (nJung != -1) // �ʼ�+�߼�+(����)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // �ʼ���
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // �߼���
				buffer.append(second.charAt(nJung));
			else { // ������
				if (nJong != -1)
					buffer.append(third.charAt(nJong));
			}
		}

		return buffer.toString();
	}
	
	public String seperate(String input) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(c >= '��' && c <= '�R') {
				int cho = (c - 0xac00) / 28 / 21;
				int jung = (c - 0xac00) / 28 % 21;
				int jong = (c - 0xac00) % 28 - 1;
				buffer.append(first.charAt(cho));
				switch(second.charAt(jung)) {
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				default:	buffer.append(second.charAt(jung));
				}
				if(jong >= 0) {
					switch(third.charAt(jong)) {
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					case '��':	buffer.append('��'); buffer.append('��'); break;
					default: buffer.append(third.charAt(jong));
					}
				}
			}
			else if(second.contains(String.valueOf(c))) {
				switch(c) {
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				default:	buffer.append(c);
				}
			}
			else if(third.contains(String.valueOf(c))) {
				switch(c) {
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				case '��':	buffer.append('��'); buffer.append('��'); break;
				default: buffer.append(c);
				}
			}
			else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}
	
	public String merge(String input) {

		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // �ʼ�, �߼�, ����

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			int p = korean.indexOf(ch);
			if (p == -1) { // �ѱ������� �ƴ�
				// �����ִ� �ѱ��� ������ ó��
				if (nCho != -1) {
					if (nJung != -1) // �ʼ�+�߼�+(����)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // �ʼ���
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // �߼���
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // ������
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // ����
				if (nJung != -1) {
					if (nCho == -1) { // �߼��� �Էµ�, �ʼ�����
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // �����̴�
						if (nJong == -1) { // ���� �Է� ��
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // ������ �ƴ϶� �ʼ��̴�
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ��
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ��
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ��
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ��
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ��
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ��
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ��
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ��
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ��
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ��
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ��
							nJong = 17;
						} else { // ���� �Է� ��, �ʼ�����
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // �ʼ� �Ǵ� (��/��)�����̴�
					if (nCho == -1) { // �ʼ� �Է� ����
						if (nJong != -1) { // ������ �� �ʼ�
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ��
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ��
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ��
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ��
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ��
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ��
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ��
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ��
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ��
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ��
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ��
						nCho = -1;
						nJong = 17;
					} else { // �������� ��Ÿ
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // ����
				if (nJong != -1) { // (�ձ��� ����), �ʼ�+�߼�
					// ������ �ٽ� ����
					int newCho; // (�ӽÿ�) �ʼ�
					if (nJong == 2) { // ��, ��
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ��, ��
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ��, ��
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ��, ��
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ��, ��
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ��, ��
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ��, ��
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ��, ��
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ��, ��
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ��, ��
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ��, ��
						nJong = 16;
						newCho = 9;
					} else { // ������ �ƴ�
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // �ձ��ڰ� �ʼ�+�߼�+(����)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // �������� ����
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // �߼� �Է� ��
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ��
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ��
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ��
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ��
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ��
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ��
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ��
					nJung = 19;
				} else { // ���� �ȵǴ� ���� �Է�
					if (nCho != -1) { // �ʼ�+�߼� �� �߼�
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // �߼� �� �߼�
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// ������ �ѱ��� ������ ó��
		if (nCho != -1) {
			if (nJung != -1) // �ʼ�+�߼�+(����)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // �ʼ���
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // �߼���
				buffer.append(second.charAt(nJung));
			else { // ������
				if (nJong != -1)
					buffer.append(third.charAt(nJong));
			}
		}

		return buffer.toString();
	}
	
	private char mergeKorean(int nCho, int nJung, int nJong) {
		char result = 0xac00;
		result += nCho * 21 * 28;
		result += nJung * 28;
		result += nJong + 1;
		return result;
	}
}
