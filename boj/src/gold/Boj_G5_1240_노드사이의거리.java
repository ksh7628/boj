package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 28.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 트리, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 골드5
 * 소요 시간: 0h 13m
 * 혼자 품: O
 * 풀이: 
 * 1. 양방향 인접 리스트 방식으로 노드들을 저장한다.
 * 2. 출발점에서 dfs방식으로 탐색해서 도착점까지 거리를 구한다.
 * 3. 구했다면 isFind에 표시를 해서 남은 탐색들을 중단한다.
 * 느낀 점: 어렵지 않은 문제였고 dfs와 bfs 둘 다 사용할 수 있으면 dfs를 사용하는 것이 좀 더 성능이 좋다.
 */
public class Boj_G5_1240_노드사이의거리 {
	static class Node {
		int num, dist;

		public Node(int num, int dist) {
			super();
			this.num = num;
			this.dist = dist;
		}
	}

	static List<Node>[] list;
	static boolean[] visit;
	static int N, end, res;
	static boolean isFind;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());

			list[from].add(new Node(to, dist));
			list[to].add(new Node(from, dist));
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			end = Integer.parseInt(st.nextToken());

			visit = new boolean[N + 1];
			res = 0;
			isFind = false;

			dfs(start, 0);
			sb.append(res).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* dfs로 도착 노드까지의 거리를 갱신해가며 탐색한다. */
	private static void dfs(int idx, int dist) {
		if (isFind) {
			return;
		}

		if (idx == end) {
			res = dist;
			isFind = true;
			return;
		}

		visit[idx] = true;
		for (Node node : list[idx]) {
			if (!visit[node.num]) {
				dfs(node.num, dist + node.dist);
			}
		}
	}
}