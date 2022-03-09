package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 8.
 * 
 * 분류: 그래프 이론, 자료 구조, 우선순위 큐, 위상 정렬
 * 난이도: 골드2
 * 소요 시간: 0h 30m
 * 혼자 품: O
 * 풀이: 위상 정렬을 시키면서 큐 대신 우선 순위 큐를 적용해서 품. -> 지문에 "가능하면 쉬운 문제부터 풀어야 한다" 문구 때문.
 * 느낀 점: https://www.acmicpc.net/problem/2252 문제에서 큐 대신 우선순위 큐를 적용하면 되는 문제여서 쉽게 풀었다.
 */
public class Boj_G2_1766_문제집 {
	static class Problem implements Comparable<Problem> {
		int idx, cnt;

		public Problem(int idx, int cnt) {
			super();
			this.idx = idx;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Problem o) {
			if (this.cnt == o.cnt) {
				return Integer.compare(this.idx, o.idx);
			}
			return Integer.compare(this.cnt, o.cnt);
		}
	}

	static List<Integer>[] list;
	static int[] indegree;
	static int N, M;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		indegree = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			indegree[b]++;
		}

		solution();
		br.close();
	}

	private static void solution() {
		StringBuilder sb = new StringBuilder();
		Queue<Integer> pq = new PriorityQueue<>();

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				pq.offer(i);
			}
		}

		while (!pq.isEmpty()) {
			int cur = pq.poll();
			sb.append(cur).append(" ");

			for (int nxt : list[cur]) {
				if (--indegree[nxt] == 0) {
					pq.offer(nxt);
				}
			}
		}
		
		System.out.println(sb);
	}
}