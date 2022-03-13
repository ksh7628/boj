package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 13.
 * 
 * 분류: 수학, 문자열, 정렬
 * 난이도: 실버4
 * 소요 시간: 0h 20m
 * 혼자 품: O
 * 풀이: 원본 숫자와 자릿수마다 영어로 읽는 Number 객체를 정의해서 품.
 * 느낀 점: 예전에 이문제를 1시간이 넘도록 못풀었었는데 지나고 보니 문제를 잘 읽어야 된다는 것을 알게 됨.
 */
public class Boj_S4_1755_숫자놀이 {
	static class Number implements Comparable<Number> {
		int num;
		String convNum;

		public Number(int num, String convNum) {
			super();
			this.num = num;
			this.convNum = convNum;
		}

		@Override
		public int compareTo(Number o) {
			return this.convNum.compareTo(o.convNum);
		}
	}

	static Number[] number;
	static String[] engNum = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		number = new Number[N - M + 1];

		solution();
		br.close();
	}

	/* 숫자를 영어로 읽었을 때의 사전순으로 정렬 */
	private static void solution() {
		for (int i = 0; i < N - M + 1; i++) {
			setEngNum(i + M);
		}

		Arrays.sort(number);
		StringBuilder sb = new StringBuilder();
		int cnt = 0;

		for (int i = 0; i < N - M + 1; i++) {
			sb.append(number[i].num).append(" ");
			if (++cnt % 10 == 0) {
				sb.append("\n");
			}
		}

		System.out.print(sb);
	}

	/* 숫자를 자릿수로 나눠서 읽은 문자열을 저장 */
	private static void setEngNum(int n) {
		if (n < 10) {
			number[n - M] = new Number(n, engNum[n]);
			return;
		}

		int first = n / 10;
		int second = n % 10;
		number[n - M] = new Number(n, engNum[first] + engNum[second]);
	}
}