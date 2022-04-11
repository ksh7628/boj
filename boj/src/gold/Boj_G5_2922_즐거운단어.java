package gold;

import java.io.*;

/**
 * @author	: ksh76
 * @date	: 2022. 4. 11.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 골드5
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이:
 * 1. '_'를 발견
 * 1-1. 모음을 넣을 경우 현재까지의 경우의 수 * 5;
 * 1-2. L을 넣을 경우 현재까지의 경우의 수 그대로
 * 1-3. L을 제외한 자음을 넣을 경우 현재까지의 경우의 수 * 20;
 * 2. 나머지의 경우 모음 개수 or 자음 개수를 현재 알파벳에 맞게 누적
 * 3. 중간에 연속된 모음 개수 or 자음 개수가 3개가 되면 현재 진행중인 탐색을 종료
 * 느낀 점: 
 * 처음에 시간초과가 났고 경우의 수를 계속 더해가는 방식으로는 최대 정답이 2^63 - 1라서 다른 방식을 택해야 했다.
 * '_'가 있는 위치만 따로 탐색을 하려 해도 코드가 너무 꼬여버려서 디버깅도 잘 안되는 상황이 발생해서 결국 다른 사람의 코드를 참조하게 되었다.
 * 이번 문제처럼 경우의 수를 곱해가는 로직에 대한 이해가 부족했던 것을 느끼게 됨.
 */
public class Boj_G5_2922_즐거운단어 {
	static String word;
	static long res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		word = br.readLine();
		dfs(0, 0, 0, 1, false);
		System.out.println(res);
		br.close();
	}

	/* 모든 경우의 수를 구한다 */
	private static void dfs(int i, int moCnt, int jaCnt, long sum, boolean isL) {
		// 모음 or 자음이 연속 3번 등장하면 안됨
		if (moCnt == 3 || jaCnt == 3) {
			return;
		}

		if (i == word.length()) {
			// L이 포함된 경우에만 경우의 수 누적
			if (isL) {
				res += sum;
			}
			return;
		}

		char c = word.charAt(i);
		if (c == '_') {
			// 모음
			dfs(i + 1, moCnt + 1, 0, sum * 5, isL);
			// L
			dfs(i + 1, 0, jaCnt + 1, sum, true);
			// L을 제외한 자음
			dfs(i + 1, 0, jaCnt + 1, sum * 20, isL);
		} else if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			dfs(i + 1, moCnt + 1, 0, sum, isL);
		} else if (c == 'L') {
			dfs(i + 1, 0, jaCnt + 1, sum, true);
		} else {
			dfs(i + 1, 0, jaCnt + 1, sum, isL);
		}
	}
}