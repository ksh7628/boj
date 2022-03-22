package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 22.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 그래프 탐색, 트리, 깊이 우선 탐색, 트리에서의 다이나믹 프로그래밍
 * 난이도: 골드5
 * 소요 시간: 0h 17m
 * 혼자 품: O
 * 풀이: 메모이제이션 + dfs로 풀이.
 * 느낀 점: 트리 DP의 기초 문제를 다지게 되었다.
 */
public class Boj_G5_15681_트리와쿼리 {
	static List<Integer>[] tree;
	static int[] dp;
	static int N, R;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		tree = new ArrayList[N + 1];
		dp = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			tree[i] = new ArrayList<>();
			dp[i] = -1;
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			tree[a].add(b);
			tree[b].add(a);
		}

		dfs(R);
		dp[R]++;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Q; i++) {
			int num = Integer.parseInt(br.readLine());
			sb.append(dp[num]).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	private static int dfs(int cur) {
		if (dp[cur] != -1) {
			return 1;
		}
		
		dp[cur] = 0;
		for (int nxt : tree[cur]) {
			dp[cur] += dfs(nxt);
		}

		return dp[cur];
	}
}