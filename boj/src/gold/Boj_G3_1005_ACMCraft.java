package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 위상 정렬
 * 난이도: 골드3
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: dfs + dp로 풀이.
 * 느낀 점: 
 * 다음과 같은 2가지를 고려 못해서 다른 사람의 코드를 참조하고 풀게 되었다. 나중에 위상 정렬로도 풀어봐야겠다.
 * 1. 건설 시간값이 0으로 주어질 수 있음
 * 2. 최댓값 갱신 위치
 */
public class Boj_G3_1005_ACMCraft {
	static List<Integer>[] list;
	static int[] time, dp;
	static int N, res;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			list = new ArrayList[N + 1];
			time = new int[N + 1];
			dp = new int[N + 1];

			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i <= N; i++) {
				time[i] = Integer.parseInt(st.nextToken());
				list[i] = new ArrayList<>();
				dp[i] = -1;
			}

			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				list[b].add(a);
			}

			int start = Integer.parseInt(br.readLine());
			sb.append(dfs(start)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* dfs + dp */
	private static int dfs(int cur) {
		// 이미 dp에 값이 존재
		if (dp[cur] != -1) {
			return dp[cur];
		}

		int max = 0;
		for (int nxt : list[cur]) {
			// 현재 노드에서 갈수있는 모든 노드들 중 최댓값을 구한다
			max = Math.max(max, dfs(nxt));
		}

		max += time[cur];
		return dp[cur] = max;
	}
}