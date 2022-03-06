package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 그래프 이론, 벨만-포드
 * 난이도: 골드3
 * 소요 시간: 1h 01m
 * 혼자 품: X
 * 풀이: 음의 사이클 발생 유무를 벨만-포드 알고리즘으로 찾으면 된다.
 * 느낀 점: 
 * https://www.acmicpc.net/problem/11657이랑 거의 비슷한 문제인데 조건문에 현재 dist배열이 INF가 아닌지를
 * 검사하는 구문과 오버플로우 발생때문에 틀린 이유를 혼자서 찾기 어려웠던 문제.
 * 꼼꼼하게 체크를 해야 되기 때문에 다시 풀어봐야 될 문제이다.
 */
public class Boj_G3_1865_웜홀 {
	static class Node {
		int from, to, cost;

		public Node(int from, int to, int cost) {
			super();
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}

	static List<Node> list;
	static int[] dist;
	static int N, M, W;
	static final int INF = 987654321;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			list = new ArrayList<>();
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;
				int cost = Integer.parseInt(st.nextToken());

				list.add(new Node(from, to, cost));
				list.add(new Node(to, from, cost));
			}

			for (int i = 0; i < W; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;
				int cost = Integer.parseInt(st.nextToken());

				list.add(new Node(from, to, -cost));
			}

			dist = new int[N];
			Arrays.fill(dist, INF);

			if (bellmanFord()) {
				sb.append("YES");
			} else {
				sb.append("NO");
			}
			sb.append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 벨만 포드 알고리즘으로 음의 사이클 발생 유무를 검사한다. */
	private static boolean bellmanFord() {
		dist[0] = 0;
		// 최소 거리 갱신 유무 판별하는 변수
		boolean isUpdate = false;

		for (int i = 0; i < N; i++) {
			isUpdate = false;

			for (Node node : list) {
				if (dist[node.to] > dist[node.from] + node.cost) {
					dist[node.to] = dist[node.from] + node.cost;
					isUpdate = true;
				}
			}

			// 갱신이 안일어났다면 더 이상 둘러볼 필요는 없다
			if (!isUpdate) {
				break;
			}
		}

		if (isUpdate) {
			for (int i = 0; i < N; i++) {
				for (Node node : list) {
					// 또 갱신이 일어났다면 음의 사이클 찾음
					if (dist[node.to] > dist[i] + node.cost) {
						return true;
					}
				}
			}
		}
		return false;
	}
}