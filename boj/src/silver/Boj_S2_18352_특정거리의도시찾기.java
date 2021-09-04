package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 9. 4.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버2
 * 소요 시간: 1h 20m
 * 혼자 품: O
 * 풀이: 
 * 입력값들을 인접 리스트에 저장해가면서 이미 저장된 노드간 방향 정보가 있다면 contains 메소드를 사용하여 저장하지 않고
 * 우선순위 큐를 사용한 다익스트라로 거리가 K가 되는 순간 탐색을 중지하고 큐에 남아있는 노드 번호를 출력해주었다.
 * 느낀 점: 
 * 처음에 bfs로 시도했다가 조건을 놓쳤었는데 다익스트라로 다시 풀긴 풀었다. 하지만 시간이 생각보다 많이 걸렸고
 * List 인터페이스에 contains 메소드의 시간복잡도가 O(n)이라서 시간이 더 걸렸던것 같다. 리팩토링이 필요한 코드라 생각된다.
 */
public class Boj_S2_18352_특정거리의도시찾기 {
	static class Node implements Comparable<Node> {
		int num, dist;

		public Node(int num, int dist) {
			super();
			this.num = num;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			if (this.dist == o.dist) {
				return this.num - o.num;
			}
			return this.dist - o.dist;
		}
	}

	static PriorityQueue<Node> pq = new PriorityQueue<>();
	static ArrayList<Integer>[] al;
	static int[] distance;
	static int N, M, K, X;
	static final int INF = Integer.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		al = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			al[i] = new ArrayList<>();
		}
		distance = new int[N + 1];
		Arrays.fill(distance, INF);

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			if (!al[from].contains(to)) {// 중복된 경로가 아니라면 인접 리스트 저장
				al[from].add(to);
			}
		}

		dijkstra();
		br.close();
	}

	// 우선순위 큐를 사용한 다익스트라
	private static void dijkstra() {
		boolean[] visit = new boolean[N + 1];
		distance[X] = 0;
		pq.offer(new Node(X, 0));

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			if (node.dist == K) {// 거리가 K라면 탐색 종료
				pq.offer(node);
				break;
			}

			if (visit[node.num]) {
				continue;
			}

			visit[node.num] = true;
			for (int i : al[node.num]) {
				if (!visit[i] && distance[i] > node.dist + distance[node.num]) {
					distance[i] = distance[node.num] + 1;
					pq.offer(new Node(i, distance[i]));
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		if (pq.isEmpty()) {// 큐가 비었다면 -1
			sb.append(-1);
		} else {
			while (!pq.isEmpty()) {// 비어있지 않다면 정렬된 노드 번호 순
				sb.append(pq.poll().num).append("\n");
			}
		}
		System.out.print(sb);
	}
}