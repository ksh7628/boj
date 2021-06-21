import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 21.
 *
 * 분류: 그래프 이론, 다익스트라
 * 난이도: 골드4
 * 혼자 품: X
 * 풀이: 
 * 1. 입력받은 값들을 인접 리스트에 저장한다.
 * 2. 출발점 -> 목적지1 -> 목적지2 -> 도착점, 출발점 -> 목적지2 -> 목적지1 -> 도착점 두 가지 경로에 대해 각 지점마다의 최소 거리를 다익스트라 알고리즘으로 구해준다.
 * 3. 중간에 길이 없다면 총 경로의 거리 값을 무한대로 설정하고 두 경로 중 최소거리를 구한다.
 * 느낀 점: 
 * 처음에는 한번의 다익스트라로 두 목적지를 경유하는 최소거리를 구할려고 했는데 구현 방법이 떠오르지 않았고 또한 두 목적지를 경유하는 순서에 따라 다른 값이 나올 수 있기 때문에
 * 설계 방향을 잘못 잡아서 어떻게 풀어야 될지 감이 안잡히게 되어 다른 사람의 코드를 참조하니 도착점이든 목적지 두곳이든 한 장소를 경유할 떄마다 거리값을 저장하고 다시 다익스트라를
 * 돌리는 방법이 있다는 것을 알게 되었다. 상대적으로 구현이 쉬운 플로이드 알고리즘만 사용했었는데 이번을 계기로 다익스트라에 더 익숙해져야 겠다고 느낀 문제였다.
 */
public class Boj_G4_1504_특정한최단경로 {
	static class Node implements Comparable<Node> {
		int v, d;

		public Node(int v, int d) {
			super();
			this.v = v;
			this.d = d;
		}

		@Override
		public int compareTo(Node o) {
			return this.d - o.d;
		}
	}

	static ArrayList<Node>[] al;
	static int[] dist;
	static int N, E, dest1, dest2;
	static final int INF = Integer.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		al = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			al[i] = new ArrayList<>();
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());

			al[from].add(new Node(to, dist));
			al[to].add(new Node(from, dist));
		}

		st = new StringTokenizer(br.readLine(), " ");
		dest1 = Integer.parseInt(st.nextToken());
		dest2 = Integer.parseInt(st.nextToken());

		System.out.println(process());
		br.close();
	}

	/* 1 -> 목적지1 -> 목적지2 -> N과, 1 -> 목적지2 -> 목적지1 -> N중 최소 거리를 계산 */
	private static int process() {
		int total1 = INF, total2 = INF;
		int sToV1 = dijkstra(1, dest1);
		if (sToV1 != INF) {// 목적지1을 못가면 N까지 못감
			int v1ToV2 = dijkstra(dest1, dest2);
			if (v1ToV2 != INF) {// 목적지2를 못가면 N까지 못감
				int v2ToN = dijkstra(dest2, N);
				total1 = sToV1 + v1ToV2 + v2ToN;
			}
		}
		
		int sToV2 = dijkstra(1, dest2);
		if (sToV2 != INF) {// 목적지2를 못가면 N까지 못감
			int v2ToV1 = dijkstra(dest2, dest1);
			if (v2ToV1 != INF) {// 목적지1을 못가면 N까지 못감
				int v1ToN = dijkstra(dest1, N);
				total2 = sToV2 + v2ToV1 + v1ToN;
			}
		}

		int res = Math.min(total1, total2);// 두 경로 중 최소 거리 갱신
		return (res == INF) ? -1 : res;
	}

	/* 우선순위 큐를 사용한 다익스트라 */
	private static int dijkstra(int start, int end) {
		dist = new int[N + 1];
		Arrays.fill(dist, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.offer(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			if (cur.v == end) {// end까지 도착했다면 거리 반환
				return cur.d;
			}

			for (Node n : al[cur.v]) {
				if (dist[n.v] > cur.d + n.d) {
					dist[n.v] = cur.d + n.d;
					pq.offer(new Node(n.v, dist[n.v]));
				}
			}
		}

		return INF;// end까지 갈 수 없다면 INF 반환
	}
}