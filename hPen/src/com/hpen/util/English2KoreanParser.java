package com.hpen.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
	
	public String parse(String eng) {
		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // �ʼ�, �߼�, ����

		for (int i = 0; i < eng.length(); i++) {
			char ch = eng.charAt(i);
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
	public String merge(String kor) {
		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // �ʼ�, �߼�, ����

		for (int i = 0; i < kor.length(); i++) {
			char ch = kor.charAt(i);
			int p = korean.indexOf(ch);
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
	private char mergeKorean(int nCho, int nJung, int nJong) {
		char result = 0xac00;
		result += nCho * 21 * 28;
		result += nJung * 28;
		result += nJong + 1;
		return result;
	}
}
