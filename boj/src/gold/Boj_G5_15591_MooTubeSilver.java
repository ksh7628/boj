package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: ksh76
 * @date	: 2022. 3. 31.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드5
 * 소요 시간: 0h 19m
 * 혼자 품: O
 * 풀이: dfs로 풀이. 인접 리스트를 선언해서 양방향으로 연결해주고 dfs를 수행하면서 아직 방문안했고 k값 이상일 경우에만 개수 증가. O(N*Q)로 돌아간다.
 * 느낀 점: N, Q가 5000이하라서 충분히 돌아가지만 다른 사람의 풀이를 보니 유니온 파인드를 적용한다면 효율성이 증가한다는 것을 알게 됨.
 */
public class Boj_G5_15591_MooTubeSilver {
	static class Node {
		int vertex, dist;

		public Node(int vertex, int dist) {
			super();
			this.vertex = vertex;
			this.dist = dist;
		}
	}

	static List<Node>[] list;
	static boolean[] visit;
	static int N, cnt;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			list[p].add(new Node(q, r));
			list[q].add(new Node(p, r));
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int k = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			cnt = 0;
			visit = new boolean[N + 1];
			dfs(v, k);
			sb.append(cnt).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	private static void dfs(int v, int k) {
		visit[v] = true;
		for (Node node : list[v]) {
			if (!visit[node.vertex] && node.dist >= k) {
				cnt++;
				dfs(node.vertex, k);
			}
		}
	}
}