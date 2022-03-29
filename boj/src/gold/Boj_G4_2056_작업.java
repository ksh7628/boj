package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: ksh76
 * @date	: 2022. 3. 29.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 위상 정렬
 * 난이도: 골드4
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: same boj.kr/1005, boj.kr/1516
 * 느낀 점: .
 */
public class Boj_G4_2056_작업 {
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

			int M = Integer.parseInt(st.nextToken());
			for (int j = 0; j < M; j++) {
				int v = Integer.parseInt(st.nextToken());
				list[i].add(v);
			}
		}

		int res = 0;
		for (int i = 1; i <= N; i++) {
			res = Math.max(res, dfs(i));
		}

		System.out.println(res);
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