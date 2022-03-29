package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 29.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 위상 정렬
 * 난이도: 골드3
 * 소요 시간: 0h 14m
 * 혼자 품: O
 * 풀이: boj.kr/1005와 동일
 * 느낀 점: dfs + dp에 익숙해지고 있는 것을 느낌.
 */
public class Boj_G3_1516_게임개발 {
	static List<Integer>[] list;
	static int[] time, dp;
	static int N;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		list = new ArrayList[N + 1];
		time = new int[N + 1];
		dp = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			time[i] = Integer.parseInt(st.nextToken());

			while (true) {
				int num = Integer.parseInt(st.nextToken());
				if (num == -1) {
					break;
				}

				list[i].add(num);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= N; i++) {
			sb.append(dfs(i)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* dfs + dp */
	private static int dfs(int x) {
		if (dp[x] != 0) {
			return dp[x];
		}

		int max = dp[x] = time[x];
		for (int nxt : list[x]) {
			max = Math.max(max, dp[x] + dfs(nxt));
		}

		return dp[x] = max;
	}
}