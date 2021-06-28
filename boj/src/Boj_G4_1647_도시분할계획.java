import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 29.
 *
 * 분류: 그래프 이론, 최소 스패닝 트리
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 최소 스패닝 트리(https://www.acmicpc.net/problem/1197) 문제와 풀이는 거의 유사해서 다른 과정은 생략하고 마을이 2개일 때의 최소 비용을
 * 구해야 하므로 간선이 N-2개일 때 드는 비용이 답이 된다.
 * 느낀 점: 
 * 이 문제는 풀이보다도 여태 MST 문제를 풀 때에는 Edge 객체를 갖는 배열로 선언하여 풀었었는데 정점과 간선의 개수가 많아서 그런지 우선순위 큐를 적용하여
 * 풀면 시간이 많이 절약된다는 것을 알게 된 문제였다.
 */
public class Boj_G4_1647_도시분할계획 {
	static class Edge implements Comparable<Edge> {
		int from, to, cost;

		public Edge(int from, int to, int cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {// 비용의 오름차순으로 정렬
			return this.cost - o.cost;
		}
	}

	static PriorityQueue<Edge> pq = new PriorityQueue<>();
	static int[] p;
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			pq.offer(new Edge(from, to, cost));
		}

		System.out.println(process());
		br.close();
	}

	private static int process() {
		make();
		return kruskal();
	}

	private static int kruskal() {
		int res = 0, cnt = 0;
		while (!pq.isEmpty()) {
			Edge e = pq.poll();
			if (union(e.from, e.to)) {
				res += e.cost;
				if (++cnt == N - 2) {// N-1개의 간선을 연결하면 마을이 하나가 되므로 N-2개까지만 연결
					break;
				}
			}
		}
		return res;
	}

	private static void make() {
		p = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			p[i] = i;
		}
	}

	private static int find(int x) {
		if (x == p[x]) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static boolean union(int x, int y) {
		int a = find(x);
		int b = find(y);
		if (a != b) {
			p[b] = a;
			return true;
		}
		return false;
	}
}