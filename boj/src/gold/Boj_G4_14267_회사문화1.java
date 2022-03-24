package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 24.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 그래프 탐색, 트리, 깊이 우선 탐색, 트리에서의 다이나믹 프로그래밍
 * 난이도: 골드4
 * 소요 시간: 0h 26m
 * 혼자 품: O
 * 풀이: 인접 리스트로 트리를 구현, dfs를 수행하면서 칭찬 누적의 합을 dp에 저장하는 식으로 품.
 * 느낀 점: 한 사원이 여러 번 칭찬 받을 수 있다는 점을 간과해서 여러번 틀리고 고치게 되었다.
 */
public class Boj_G4_14267_회사문화1 {
	static List<Integer>[] tree;
	static int[] dp, num;
	static int n;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		tree = new ArrayList[n + 1];
		dp = new int[n + 1];
		num = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			tree[i] = new ArrayList<>();
		}

		st = new StringTokenizer(br.readLine(), " ");
		st.nextToken();
		for (int i = 2; i <= n; i++) {
			int parent = Integer.parseInt(st.nextToken());
			tree[parent].add(i);
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int idx = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			num[idx] += w;
		}

		solution();
		br.close();
	}

	private static void solution() {
		Arrays.fill(dp, -1);
		dfs(1, num[1]);

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			sb.append(dp[i]).append(" ");
		}

		System.out.print(sb);
	}

	private static void dfs(int x, int cost) {
		if (dp[x] != -1) {
			return;
		}

		dp[x] = cost;
		for (int nxt : tree[x]) {
			dfs(nxt, cost + num[nxt]);
		}
	}
}