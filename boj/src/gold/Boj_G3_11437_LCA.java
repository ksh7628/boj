package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 25.
 * 
 * 분류: 트리, 최소 공통 조상
 * 난이도: 골드3
 * 소요 시간: 0h 38m
 * 혼자 품: O
 * 풀이: 
 * 1. 주어진 연결 정점으로 인접 리스트를 만든다.
 * 2. dfs를 통해 각 노드별 깊이를 depth, 부모 노드를 tree에 저장한다.
 * 3. 주어진 두 노드의 깊이가 같아질 때까지 깊이가 큰 노드를 올린다.
 * 4. 깊이가 같아졌다면 두 노드의 부모가 같아질 때까지 탐색한다.
 * 느낀 점: 
 * 입력에서 부모-자식 관계가 주어지지 않고 루트만 주어졌기 때문에 dfs로 트리를 만들어야 한다.
 * lca 메소드에서 최댓값 변수에 먼저 저장하고 다시 두 노드 중 depth가 max인 노드를 하나씩 위로 올렸는데
 * 이 경우, 불필요한 연산이 더 수행되어서 최악의 경우 N번 더 연산하게 되어서 처음에 시간초과가 났다.
 * 항상 불필요한 연산은 제거하는게 좋으므로 꼼꼼한 설계와 구현을 갖추자.
 */
public class Boj_G3_11437_LCA {
	static List<Integer>[] list;
	static int[] tree, depth;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		list = new ArrayList[N + 1];
		tree = new int[N + 1];
		depth = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[a].add(b);
			list[b].add(a);
		}

		dfs(0, 1, 1);
		int M = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			sb.append(lca(a, b)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 
	 * 1. dfs를 통해 tree[자식] = 부모 형태로 저장
	 * 2. 트리의 깊이를 depth에 저장
	 */
	private static void dfs(int pre, int cur, int lv) {
		tree[cur] = pre;
		depth[cur] = lv;

		for (int nxt : list[cur]) {
			if (depth[nxt] == 0) {
				dfs(cur, nxt, lv + 1);
			}
		}
	}

	/* 
	 * 1. 깊이가 같아질 때까지 맞춰준다
	 * 2. 깊이가 같아진다면 두 노드의 부모가 같아질때까지 탐색한다
	 */
	private static int lca(int a, int b) {
		while (depth[a] != depth[b]) {
			/*
			int max = Math.max(depth[a], depth[b]);
			if (a == max) {
				a = tree[a];
			} else {
				b = tree[b];
			} */
			
			if (depth[a] > depth[b]) {
				a = tree[a];
			} else {
				b = tree[b];
			}
		}

		while (a != b) {
			a = tree[a];
			b = tree[b];
		}

		return a;
	}
}