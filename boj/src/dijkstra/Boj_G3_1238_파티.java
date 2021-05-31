package dijkstra;

import java.io.*;
import java.util.*;

public class Boj_G3_1238_파티 {
	static class Node implements Comparable<Node> {
		int v, d;

		public Node(int v, int d) {
			super();
			this.v = v;
			this.d = d;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.d, o.d);
		}
	}

	static int[] dist, revDist;
	static int N, M, X;
	static final int INF = Integer.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		ArrayList<Node>[] list = new ArrayList[N + 1];
		ArrayList<Node>[] revList = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
			revList[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			list[from].add(new Node(to, d));
			revList[to].add(new Node(from, d));
		}
		
		dist = new int[N + 1];
		Arrays.fill(dist, INF);
		revDist = new int[N + 1];
		Arrays.fill(revDist, INF);

		dijkstra(list, dist);
		dijkstra(revList, revDist);
		System.out.println(getMaxTime());
		br.close();
	}

	private static void dijkstra(ArrayList<Node>[] al, int[] dis) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dis[X] = 0;
		pq.offer(new Node(X, dis[X]));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			for (Node node : al[cur.v]) {
				if (dis[node.v] > cur.d + node.d) {
					dis[node.v] = cur.d + node.d;
					pq.offer(new Node(node.v, dis[node.v]));
				}
			}
		}
	}

	private static int getMaxTime() {
		int maxT = 0;
		for (int i = 1; i <= N; i++) {
			maxT = Math.max(maxT, dist[i] + revDist[i]);
		}
		return maxT;
	}
}
