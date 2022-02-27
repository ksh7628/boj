package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 30.
 *
 * 분류: 자료 구조, 문자열, 정렬, 트리, 트라이
 * 난이도: 골드4
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: String 클래스의 startsWith 메소드를 이용하여 풀었다. 문자열 배열을 정렬을 해줄 경우 단일 for문으로 바로 앞 문자열이 접두어가 되는지만 체크하면 된다.
 * 느낀 점: 생각보다 자바에 문자열 메소드가 많다는 것을 알게 해준 문제였다. 트라이가 분류로 되어 있어서 다음번에는 트라이로 풀어봐야겠다.
 */
public class Boj_G4_5052_전화번호목록 {
	static String[] phoneNum;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= t; tc++) {
			N = Integer.parseInt(br.readLine());
			phoneNum = new String[N];

			for (int i = 0; i < N; i++) {
				phoneNum[i] = br.readLine();
			}
			Arrays.sort(phoneNum);

			if (isConsistent()) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
		}
		
		System.out.print(sb);
		br.close();
	}

	private static boolean isConsistent() {
		for (int i = 1; i < N; i++) {
			if (phoneNum[i].startsWith(phoneNum[i - 1])) {
				return false;
			}
		}
		return true;
	}
}