package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 11.
 * 
 * 분류: 그래프 이론, 다익스트라
 * 난이도: 골드2
 * 소요 시간: 1h 20m
 * 혼자 품: O
 * 풀이: 
 * 출발점을 s,g 또는 s,h로 하는 다익스트라를 2번 돌리는 식으로 품.
 * 처음 출발점에서 다익스트라를 수행한 후 g랑 h값을 비교해서 중간 출발지를 g로할지 h로할지 결정한다.
 * 미리 구한 최단 거리와 s -> g(h) -> h(g) -> 목적지 후보 간 비교해서 값이 다르면 불가능함.
 * 느낀 점: 
 * 개념을 떠올리는 건 어렵지 않았는데 중간에 실수가 많아서 좀 늦게 풀게 되었다.
 * 다른 풀이중에는 홀수, 짝수로 구분해서 다익스트라를 한번만 수행해서 정답을 구하는 풀이가 있던데 나중에 그 방식으로 접근 해봐야겠다.
 */
public class Boj_G2_9370_미확인도착지 {
	static class Node implements Comparable<Node> {
		int vertex, distance;

		public Node(int vertex, int distance) {
			super();
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node o) {
			return this.distance - o.distance;
		}
	}

	static StringBuilder sb = new StringBuilder();
	static List<Node>[] list;
	static int[] dist, candidate;
	static int n, m, t, s, g, h, confDist;
	static final int INF = 987654321;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());

			list = new ArrayList[n + 1];
			candidate = new int[t];

			for (int i = 1; i <= n; i++) {
				list[i] = new ArrayList<>();
			}

			st = new StringTokenizer(br.readLine(), " ");
			s = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());

			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());

				list[a].add(new Node(b, d));
				list[b].add(new Node(a, d));

				if ((a == g && b == h) || (b == g && a == h)) {
					confDist = d;
				}
			}

			for (int i = 0; i < t; i++) {
				candidate[i] = Integer.parseInt(br.readLine());
			}

			solution();
		}

		System.out.print(sb);
		br.close();
	}

	// 1. 시작점에서 다익스트라
	// 2. 최단경로가 s -> g -> h 인지 s -> h -> g 인지 확인
	// 3. 출발점에서 목적지 후보간 최소거리와 출발점에서 g-h 경로를 경유하는 목적지 후보와 비교
	// -> 값이 다르면 불가능
	private static void solution() {
		dijkstra(s);
		int[] minPath = new int[t];
		Arrays.sort(candidate);

		// 최소 경로 저장
		for (int i = 0; i < t; i++) {
			minPath[i] = dist[candidate[i]];
		}
		
		// 경유지 순서에 따라 출발점이 다름
		int sg = dist[g], startDist = 0;
		if (sg + confDist == dist[h]) {// s -> g -> h
			startDist = dist[h];
			dijkstra(h);
		} else {// s -> h -> g
			startDist = dist[g];
			dijkstra(g);
		}

		for (int i = 0; i < t; i++) {
			if (startDist + dist[candidate[i]] == minPath[i]) {
				sb.append(candidate[i]).append(" ");
			}
		}

		sb.append("\n");
	}

	private static void dijkstra(int start) {
		dist = new int[n + 1];
		Arrays.fill(dist, INF);

		boolean[] visit = new boolean[n + 1];
		Queue<Node> pq = new PriorityQueue<>();

		dist[start] = 0;
		pq.offer(new Node(start, dist[start]));

		while (!pq.isEmpty()) {
			Node node = pq.poll();

			if (visit[node.vertex]) {
				continue;
			}

			visit[node.vertex] = true;
			for (Node nxt : list[node.vertex]) {
				if (!visit[nxt.vertex] && dist[nxt.vertex] > node.distance + nxt.distance) {
					dist[nxt.vertex] = node.distance + nxt.distance;
					pq.offer(new Node(nxt.vertex, dist[nxt.vertex]));
				}
			}
		}
	}
}