package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 10.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 트리, 깊이 우선 탐색
 * 난이도: 골드4
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: 
 * 1. 양방향 리스트를 만들고 첫번째 dfs를 아무 정점에서 수행한다.
 * 1-1. 연결된 노드가 하나뿐이고 거리의 최댓값 갱신이 가능하다면 최댓값 갱신 및 출발 노드를 갱신한다.
 * 2. 1-1에서 갱신된 출발 노드를 시작으로 dfs를 한 번더 수행한다.
 * 느낀 점: 
 * n이 1일 때, 지름이 존재하지 않으므로 따로 예외처리를 해줘야 하는 것 말고는 어렵지 않았다.
 * 예전에 푼 https://www.acmicpc.net/problem/1167와 똑같기 때문에 쉽게 풀었다.
 */
public class Boj_G4_1967_트리의지름 {
	static class Node {
		int child, dist;

		public Node(int child, int dist) {
			super();
			this.child = child;
			this.dist = dist;
		}
	}

	static List<Node>[] list;
	static boolean[] visit;
	static int n, start, max;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());

		list = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < n - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int parent = Integer.parseInt(st.nextToken());
			int child = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			list[parent].add(new Node(child, dist));
			list[child].add(new Node(parent, dist));
		}

		solution();
		System.out.println(max);
		br.close();
	}

	private static void solution() {
		// 노드가 1개인 트리는 지름이 존재하지 않는다
		if (n == 1) {
			return;
		}

		visit = new boolean[n + 1];
		dfs(1, 0);
		max = 0;
		visit = new boolean[n + 1];
		dfs(start, 0);
	}

	private static void dfs(int cur, int sum) {
		// 연결된 노드가 하나뿐이고 경로의 길이 갱신 가능하다면
		// -> 길이 갱신 및 가장 먼 노드의 번호 갱신
		if (list[cur].size() == 1 && max < sum) {
			max = Math.max(max, sum);
			start = cur;
			return;
		}

		visit[cur] = true;
		for (Node node : list[cur]) {
			if (!visit[node.child]) {
				dfs(node.child, sum + node.dist);
			}
		}
	}
}