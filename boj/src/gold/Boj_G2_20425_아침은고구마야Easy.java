package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 7.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드1
 * 소요 시간: 1h 30m 이상
 * 혼자 품: O
 * 풀이: depth 배열에 현재 깊이를 저장하면서 dfs를 수행하다가 사이클이 생기면 해당 사이클을 구성하는 노드의 개수 ^ 2를 누적시켜준다.
 * 느낀 점: 문제 조건이 boj.kr/20427의 다이아 문제 기준이라서 해결 방안을 생각하기가 어려웠는데 문제 제한 조건을 보고 사이클마다 노드 개수의 제곱을 더해간다는 것을 알게 됨.
 */
public class Boj_G2_20425_아침은고구마야Easy {
	static List<Integer>[] tree;
	static int[] depth;
	static int N, M;
	static long res;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		tree = new ArrayList[N + 1];
		depth = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			tree[i] = new ArrayList<>();
			depth[i] = -1;
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());

			tree[u].add(v);
			tree[v].add(u);
		}

		dfs(1, 1);
		System.out.println(res);
		br.close();
	}

	/* dfs를 통해 사이클을 판단 */
	private static void dfs(int cur, int cnt) {
		depth[cur] = cnt;
		for (int nxt : tree[cur]) {
			// 이미 방문한 곳
			if (depth[nxt] != -1) {
				// 사이클 체크
				if (depth[cur] < depth[nxt]) {
					res += Math.pow(depth[nxt] - depth[cur] + 1, 2);
				}
				continue;
			}

			dfs(nxt, cnt + 1);
		}
	}
}