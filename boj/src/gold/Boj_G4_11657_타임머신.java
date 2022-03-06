package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 그래프 이론, 벨만-포드
 * 난이도: 골드4
 * 소요 시간: 0h 16m
 * 혼자 품: O
 * 풀이: 음의 가중치가 존재하기 때문에 벨만-포드 알고리즘을 사용했고 한번 더 반복문을 통해 음의 사이클을 판정.
 * 느낀 점: 벨만-포드 알고리즘 기본 문제라서 어렵지 않게 품.
 */
public class Boj_G4_11657_타임머신 {
	static class Node {
		int from, to, cost;

		public Node(int from, int to, int cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}

	static List<Node> city = new ArrayList<>();
	static long[] dist;
	static int N, M;
	static final long INF = Long.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());

			city.add(new Node(from, to, cost));
		}

		dist = new long[N];
		Arrays.fill(dist, INF);

		solution();
		br.close();
	}

	private static void solution() {
		StringBuilder sb = new StringBuilder();

		if (!bellmanFord()) {
			sb.append(-1);
		} else {
			for (int i = 1; i < N; i++) {
				sb.append(dist[i] == INF ? -1 : dist[i]).append("\n");
			}
		}

		System.out.print(sb);
	}

	/* 벨만-포드 알고리즘 */
	private static boolean bellmanFord() {
		dist[0] = 0;

		for (int i = 0; i < N; i++) {
			for (Node node : city) {
				if (dist[node.from] != INF && dist[node.to] > dist[node.from] + node.cost) {
					dist[node.to] = dist[node.from] + node.cost;
				}
			}
		}

		// 음의 사이클을 찾는다면 false 반환
		for (Node node : city) {
			if (dist[node.from] != INF && dist[node.to] > dist[node.from] + node.cost) {
				return false;
			}
		}

		return true;
	}
}