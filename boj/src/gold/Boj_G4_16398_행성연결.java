package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 그래프 이론, 최소 스패닝 트리
 * 난이도: 골드4
 * 소요 시간: 0h 16m
 * 혼자 품: O
 * 풀이: MST 알고리즘을 적용해서 품.
 * 느낀 점: 
 * 처음에는 크루스칼로 풀었는데 시간이 생각보다 많이 걸려서 프림으로 다시 풀게 되었다.
 * 크루스칼의 경우 O(ElogE), 프림의 경우 O(VlogE)의 시간복잡도를 가지는데, 이 문제의 경우 간선의 개수가 V^2라서
 * 프림 알고리즘의 성능이 더 좋다. MST로 풀리는 문제라는 것을 알게 됐다면 적절한 알고리즘 선택을 하는 것 또한 중요하다.
 */
public class Boj_G4_16398_행성연결 {
	static class Node implements Comparable<Node> {
		int vertex;
		long cost;

		public Node(int vertex, long cost) {
			super();
			this.vertex = vertex;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Long.compare(this.cost, o.cost);
		}
	}

	static int[][] edge;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		edge = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				edge[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(prim());
		br.close();
	}

	private static long prim() {
		int cnt = 0;
		long res = 0;
		boolean[] visit = new boolean[N];
		long[] dist = new long[N];

		Arrays.fill(dist, Integer.MAX_VALUE);
		Queue<Node> pq = new PriorityQueue<>();
		dist[0] = 0;
		pq.offer(new Node(0, 0));

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			if (visit[node.vertex]) {
				continue;
			}

			visit[node.vertex] = true;
			res += node.cost;
			if (++cnt == N) {
				break;
			}

			for (int i = 0; i < N; i++) {
				if (!visit[i] && edge[node.vertex][i] != 0 && dist[i] > edge[node.vertex][i]) {
					dist[i] = edge[node.vertex][i];
					pq.offer(new Node(i, dist[i]));
				}
			}
		}

		return res;
	}
}