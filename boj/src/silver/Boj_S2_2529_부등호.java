package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 10.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 실버2
 * 소요 시간: 0h 26m
 * 혼자 품: O
 * 풀이: 
 * 1. 부등호 배열을 생성하여 따로 저장한다.
 * 2. dfs를 통해 모든 경우의 수를 탐색한다. 모든 부등호를 만족하는 경우가 나온다면 max와 min을 갱신한다.
 * 느낀 점: 
 * 가지치기 없이 모든 경우를 탐색하고 문자열 연산을 계속하기 때문에 시간이 다른 사람들보다 좀 더 걸렸다.
 * 또한 문자열 비교 메소드인 compareTo에 대해 알게 되었다.
 */
public class Boj_S2_2529_부등호 {
	static char[] op;
	static boolean[] isUsed;
	static int k;
	static String max = "", min = "";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		k = Integer.parseInt(br.readLine());
		op = new char[k];

		String str = br.readLine();
		// 부등호만 op 배열에 순서대로 저장
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				op[i / 2] = str.charAt(i);
			}
		}

		// max, min 초기화
		for (int i = 0; i <= k; i++) {
			max += "0";
			min += "9";
		}

		for (int i = 0; i < 10; i++) {
			isUsed = new boolean[10];
			isUsed[i] = true;
			solution(0, Integer.toString(i));
		}

		System.out.println(max + "\n" + min);
		br.close();
	}

	// 모든 경우의 수를 탐색하여 최댓값과 최솟값 갱신
	private static void solution(int cnt, String res) {
		if (cnt == k) {
			if (res.compareTo(max) > 0) {
				max = res;
			}

			if (res.compareTo(min) < 0) {
				min = res;
			}

			return;
		}

		for (int i = 0; i < 10; i++) {
			if (!isUsed[i]) {
				char c = op[cnt];
				
				// 부등호식을 만족하는 경우에만 계속 탐색
				switch (c) {
				case '>':
					if (res.charAt(res.length() - 1) - '0' > i) {
						isUsed[i] = true;
						solution(cnt + 1, res + Integer.toString(i));
						isUsed[i] = false;
					}
					break;
				default:
					if (res.charAt(res.length() - 1) - '0' < i) {
						isUsed[i] = true;
						solution(cnt + 1, res + Integer.toString(i));
						isUsed[i] = false;
					}
				}
			}
		}
	}
}