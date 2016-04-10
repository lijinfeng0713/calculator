package com.lijinfeng.calculator_fengge;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Calculate {

	public String str_old = null;
	public boolean drg_flag = true;
	public double pi = 4 * Math.atan(1);// ��ֵ
		public Calculate() {

		}

		final int MAXLEN = 500;

		public String replaceOp(String str) {
			str =str.replaceAll("sin", "s");
			str =str.replaceAll("cos", "c");
			str =str.replaceAll("log", "g");
			str =str.replaceAll("tan", "t");
			str =str.replaceAll("��", "2��");
			return str;
		}
		
		/*
		 * ������ʽ ��������ɨ�裬������numberջ���������operatorջ 
		 * +-�������ȼ�Ϊ1��
		 * ���»������ȼ�Ϊ2��
		 * log ln sin cos tan n!�������ȼ�Ϊ3��
		 * ��^�������ȼ�Ϊ4 
		 * �����ڲ�����������ͬ����������ȼ���4
		 * ��ǰ��������ȼ�����ջ��ѹջ��
		 * ����ջ������һ�����������������������
		 *  �ظ�ֱ����ǰ���������ջ��
		 *   ɨ������ʣ�µ���������������μ���
		 */
		public String process(String str) {
			int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
			// weightPlusΪͬһ�����µĻ������ȼ���weightTemp��ʱ��¼���ȼ��ı仯
			// topOpΪweight[]��operator[]�ļ�������topNumΪnumber[]�ļ�����
			// flagΪ�������ļ�������1Ϊ������-1Ϊ����
			int weight[]; // ����operatorջ������������ȼ�����topOp����
			double number[]; // �������֣���topNum����
			char ch, ch_gai, operator[];// operator[]�������������topOp����
			String num;// ��¼���֣�str��+-����()sctgl!��^�ֶΣ�+-����()sctgl!��^�ַ�֮����ַ�����Ϊ����
			weight = new int[MAXLEN];
			number = new double[MAXLEN];
			operator = new char[MAXLEN];
			String expression = str;
			StringTokenizer expToken = new StringTokenizer(expression,
					"+ - �� �� () sctg l!��^");
			int i = 0;
			while (i < expression.length()) {
				ch = expression.charAt(i);
				// �ж�������
				if (i == 0) {
					if (ch == '-')
						flag = -1;
				} else if (expression.charAt(i - 1) == '(' && ch == '-')
					flag = -1;
				// ȡ�����֣�������������ת�Ƹ�����
				if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {
					num = expToken.nextToken();
					ch_gai = ch;
					// ȡ����������
					while (i < expression.length()
							&& (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {
						ch_gai = expression.charAt(i++);
					}
					// ��ָ���˻�֮ǰ��λ��
					if (i >= expression.length())
						i -= 1;
					else {
						i -= 2;
					}
					if (num.compareTo(".") == 0)
						number[topNum++] = 0;
					// ����������ת�Ƹ�����
					else {
						number[topNum++] = Double.parseDouble(num) * flag;
						flag = 1;
					}
				}
				// ��������������ȼ�
				if (ch == '(')
					weightPlus += 4;
				if (ch == ')')
					weightPlus -= 4;
				if (ch == '-' && flag == 1 || ch == '+' || ch == '��'
						|| ch == '��' || ch == 's' || ch == 'c' || ch == 't'
						|| ch == 'g' || ch == 'l' || ch == '!' || ch == '��'
						|| ch == '^') {
					switch (ch) {
					// +-�����ȼ���ͣ�Ϊ1
					case '+':
					case '-':
						weightTemp = 1 + weightPlus;
						break;
					// x�µ����ȼ��Ըߣ�Ϊ2
					case '��':
					case '��':
						weightTemp = 2 + weightPlus;
						break;
					// sincos֮�����ȼ�Ϊ3
					case 's':
					case 'c':
					case 't':
					case 'g':
					case 'l':
					case '!':
						weightTemp = 3 + weightPlus;
						break;
					// �������ȼ�Ϊ4
					// case '^':
					// case '��':
					default:
						weightTemp = 4 + weightPlus;
						break;
					}
					// �����ǰ���ȼ����ڶ�ջ����Ԫ�أ���ֱ����ջ
					if (topOp == 0 || weight[topOp - 1] < weightTemp) {
						weight[topOp] = weightTemp;
						operator[topOp] = ch;
						topOp++;
						// ���򽫶�ջ����������ȡ����ֱ����ǰ��ջ��������������ȼ�С�ڵ�ǰ�����
					} else {
						while (topOp > 0 && weight[topOp - 1] >= weightTemp) {
							switch (operator[topOp - 1]) {
							// ȡ�������������ӦԪ�ؽ�������
							case '+':
								number[topNum - 2] += number[topNum - 1];
								break;
							case '-':
								number[topNum - 2] -= number[topNum - 1];
								break;
							case '��':
								number[topNum - 2] *= number[topNum - 1];
								break;
							// �жϳ���Ϊ0�����
							case '��':
								if (number[topNum - 1] == 0) {
									return showError(1, str_old);
								}
								number[topNum - 2] /= number[topNum - 1];
								break;
							case '��':
								if (number[topNum - 1] < 0) {
									return showError(2, str_old);
								}
								number[topNum - 2] = Math.pow(number[topNum - 1],
										1 / number[topNum - 2]);
								break;
							case '^':
								number[topNum - 2] = Math.pow(
										number[topNum - 2], number[topNum - 1]);
								break;
							// ����ʱ���нǶȻ��ȵ��жϼ�ת��
							// sin
							case 's':
								if (drg_flag == true) {
									number[topNum - 1] = Math
											.sin((number[topNum - 1] / 180)
													* pi);
								} else {
									number[topNum - 1] = Math
											.sin(number[topNum - 1]);
								}
								topNum++;
								break;
							// cos
							case 'c':
								if (drg_flag == true) {
									number[topNum - 1] = Math
											.cos((number[topNum - 1] / 180)
													* pi);
								} else {
									number[topNum - 1] = Math
											.cos(number[topNum - 1]);
								}
								topNum++;
								break;
							// tan
							case 't':
								if (drg_flag == true) {
									if ((Math.abs(number[topNum - 1]) / 90) % 2 == 1) {
										return showError(2, str_old);
									}
									number[topNum - 1] = Math
											.tan((number[topNum - 1] / 180)
													* pi);
								} else {
									if ((Math.abs(number[topNum - 1]) / (pi / 2)) % 2 == 1) {
										return showError(2, str_old);
									}
									number[topNum - 1] = Math
											.tan(number[topNum - 1]);
								}
								topNum++;
								break;
							// log
							case 'g':
								if (number[topNum - 1] <= 0) {
									return showError(2, str_old);
								}
								number[topNum - 1] = Math
										.log10(number[topNum - 1]);
								topNum++;
								break;
							// ln
							case 'l':
								if (number[topNum - 1] <= 0) {
									return showError(2, str_old);
								}
								number[topNum - 1] = Math
										.log(number[topNum - 1]);
								topNum++;
								break;
							// �׳�
							case '!':
								if (number[topNum - 1] > 170) {
									return showError(3, str_old);
								} else if (number[topNum - 1] < 0) {
									return showError(2, str_old);
								}
								number[topNum - 1] = N(number[topNum - 1]);
								topNum++;
								break;
							}
							// ����ȡ��ջ����һ��Ԫ�ؽ����ж�
							topNum--;
							topOp--;
						}
						// ����������ջ
						weight[topOp] = weightTemp;
						operator[topOp] = ch;
						topOp++;
					}
				}
				i++;
			}
			// ����ȡ����ջ���������������
			while (topOp > 0) {
				// +-xֱ�ӽ�����ĺ���λ��ȡ������
				switch (operator[topOp - 1]) {
				case '+':
					number[topNum - 2] += number[topNum - 1];
					break;
				case '-':
					number[topNum - 2] -= number[topNum - 1];
					break;
				case '��':
					number[topNum - 2] *= number[topNum - 1];
					break;
				// �漰������ʱҪ���ǳ�������Ϊ������
				case '��':
					if (number[topNum - 1] == 0) {
						return showError(1, str_old);
					}
					number[topNum - 2] /= number[topNum - 1];
					break;
				case '��':
					if (number[topNum - 1] < 0) {
						return showError(2, str_old);
					}
					number[topNum - 2] = Math.pow(number[topNum - 1],
							1 / number[topNum - 2]);
					break;
				case '^':
					number[topNum - 2] = Math.pow(number[topNum - 2],
							number[topNum - 1]);
					break;
				// sin
				case 's':
					if (drg_flag == true) {
						number[topNum - 1] = Math
								.sin((number[topNum - 1] / 180) * pi);
					} else {
						number[topNum - 1] = Math.sin(number[topNum - 1]);
					}
					topNum++;
					break;
				// cos
				case 'c':
					if (drg_flag == true) {
						number[topNum - 1] = Math
								.cos((number[topNum - 1] / 180) * pi);
					} else {
						number[topNum - 1] = Math.cos(number[topNum - 1]);
					}
					topNum++;
					break;
				// tan
				case 't':
					if (drg_flag == true) {
						if ((Math.abs(number[topNum - 1]) / 90) % 2 == 1) {
							return showError(2, str_old);
						}
						number[topNum - 1] = Math
								.tan((number[topNum - 1] / 180) * pi);
					} else {
						if ((Math.abs(number[topNum - 1]) / (pi / 2)) % 2 == 1) {
							return showError(2, str_old);
						}
						number[topNum - 1] = Math.tan(number[topNum - 1]);
					}
					topNum++;
					break;
				// ����log
				case 'g':
					if (number[topNum - 1] <= 0) {
						return showError(2, str_old);
					}
					number[topNum - 1] = Math.log10(number[topNum - 1]);
					topNum++;
					break;
				// ��Ȼ����ln
				case 'l':
					if (number[topNum - 1] <= 0) {
						return showError(2, str_old);
					}
					number[topNum - 1] = Math.log(number[topNum - 1]);
					topNum++;
					break;
				// �׳�
				case '!':
					if (number[topNum - 1] > 170) {
						return showError(3, str_old);
					} else if (number[topNum - 1] < 0) {
						return showError(2, str_old);
					}
					number[topNum - 1] = N(number[topNum - 1]);
					topNum++;
					break;
				}
				// ȡ��ջ��һ��Ԫ�ؼ���
				topNum--;
				topOp--;
			}
			// ���������̫����ʾ������Ϣ
			if (number[0] > 7.3E306) {
				return showError(3, str_old);
			}
			// ������ս��
			return String.valueOf(FP(number[0]));

		}

		/*
		 * FP = floating point ����С��λ�����ﵽ���� ��������
		 * 0.6-0.2=0.39999999999999997���������FP���ɽ����ʹ����Ϊ0.4 ����ʽ����Ϊ15λ
		 */
		public double FP(double n) {
			DecimalFormat format = new DecimalFormat("0.#############");
			return Double.parseDouble(format.format(n));
		}

		/*
		 * �׳��㷨
		 */
		public double N(double n) {
			int i = 0;
			double sum = 1;
			// ���ν�С�ڵ���n��ֵ���
			for (i = 1; i <= n; i++) {
				sum = sum * i;
			}
			return sum;
		}
		
		/*
		 * ������ʾ������"="֮��������ʽ��process()�����У����ִ����������ʾ
		 */
		public String showError(int code, String str) {
			String message = "";
			switch (code) {
			case 1:
				//0������Ϊ����
				message = "ERROR";
				break;
			case 2:
				message = "ERROR";
				break;
			case 3:
				//ֵ̫�󣬳�����Χ
				message = "ERROR";
			}
			return message;
		}
	}