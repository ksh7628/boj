package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 2.
 * 
 * 분류: 그리디 알고리즘
 * 난이도: 실버5
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: X가 연속되는 개수가 짝수라면 4로 나누어 떨어지면 무조건 AAAA를, 아니라면 마지막에 BB를 붙이고 도중에 홀수가 나오면 만들지 못한다고 판단한다.
 * 느낀 점: 쉬어가는 문제였다.
 */
public class Boj_S5_1343_폴리오미노 {
	static final String A = "AAAA";
	static final String B = "BB";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();

		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		boolean isExit = false;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'X') {
				cnt++;
			} else {
				if (cnt % 2 == 0) {
					for (int j = 0; j < cnt / 4; j++) {
						sb.append(A);
					}

					if (cnt % 4 == 2) {
						sb.append(B);
					}

					sb.append('.');
					cnt = 0;
				} else {
					isExit = true;
					break;
				}
			}
		}

		if (cnt % 2 == 0) {
			for (int j = 0; j < cnt / 4; j++) {
				sb.append(A);
			}

			if (cnt % 4 == 2) {
				sb.append(B);
			}

			cnt = 0;
		} else {
			isExit = true;
		}

		System.out.println(isExit ? -1 : sb);
		br.close();
	}
}