

import java.io.*;
import java.util.*;

/**
 * <pre>
 * dijkstra 
 * Boj_G3_11779_최소비용구하기2.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 22.
 * @version	: 0.2
 *
 * 분류: 그래프 이론, 다익스트라
 * 난이도: 골드3
 * 혼자 품: X
 * 풀이: 다익스트라 알고리즘에 경로 복원만 추가로 구현하여 풀이하였다. 경로를 복원할때 도착지부터 시작해서 출발지까지 역순으로
 *      복원이 되기 때문에 리스트에 하나씩 담은 후 마지막에 역순으로 출력하면 출발지 ~ 도착지까지 출력이 된다.
 * 느낀 점: 오랜만에 다익스트라 알고리즘을 복습할 겸 푼 문제인데 경로 복원은 예전에 푼 DP문제나 LIS에서도 새로웠는데 또 기억이
 *        가물가물해서 정방향으로 할려니 도저히 구현이 안되어서 다른 코드를 보고 참조하였다. 다른 알고리즘에도 경로 복원 문제는
 *        종종 나오기 때문에 확실하게 복습을 해야겠다는 생각이 들었다.
 */
public class Boj_G3_11779_최소비용구하기2 {
	static class Node implements Comparable<Node> {
		int d, v;

		Node(int d, int v) {
			this.d = d;
			this.v = v;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.d, o.d);
		}
	}

	static StringBuilder sb = new StringBuilder();
	static List<Node>[] list;
	static int[] dist, pre;// 거리 정보, 경로 정보
	static int N, M, start, arrive;
	static final int INF = Integer.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		StringTokenizer st = null;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			list[from].add(new Node(d, to));
		}
		st = new StringTokenizer(br.readLine(), " ");
		start = Integer.parseInt(st.nextToken());
		arrive = Integer.parseInt(st.nextToken());

		dist = new int[N + 1];
		pre = new int[N + 1];
		Arrays.fill(dist, INF);// 최단경로를 구하기 위해 처음에 무한값으로 저장

		dijkstra();
		restorePath();
		System.out.print(sb);
		br.close();
	}

	/* 우선순위 큐를 사용하는 다익스트라 알고리즘 */
	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;// 출발지 거리 0으로 저장
		pq.offer(new Node(dist[start], start));
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			for (Node node : list[cur.v]) {
				if (dist[node.v] > cur.d + node.d) {// 현재 노드에서 다음노드로 가는 거리가 다음노드로 가는 최단거리보다 짧으면
					dist[node.v] = cur.d + node.d;// 최단거리 갱신
					pq.offer(new Node(dist[node.v], node.v));
					pre[node.v] = cur.v;// 다음 노드의 번호를 인덱스로 갖는 pre배열에 현재 노드의 번호를 값으로 저장
				}
			}
		}
		sb.append(dist[arrive]).append("\n");
	}

	/* 경로 복원 */
	private static void restorePath() {
		List<Integer> path = new ArrayList<>();// 경로를 저장하는 리스트
		int cur = arrive;
		while (cur != start) {// 도착지부터 역순으로 탐색해서 출발지까지 가면 종료
			path.add(cur);
			cur = pre[cur];// 이전 경로를 현재 경로로
		}
		path.add(cur);// 출발지 저장
		sb.append(path.size()).append("\n");
		for (int i = path.size() - 1; i >= 0; i--) {// 역순으로 탐색했으므로 역순으로 출력
			sb.append(path.get(i)).append(" ");
		}
	}
}