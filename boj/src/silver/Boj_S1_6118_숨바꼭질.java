package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버1
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: bfs로 탐색하면서 depth마다 min과 cnt를 초기화하면서 해당 depth에서 각각 갱신한다.
 * 느낀 점: 기초 bfs 문제. 10분안에 풀자.
 */
public class Boj_S1_6118_숨바꼭질 {
	static List<Integer>[] list;
	static int N;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[a].add(b);
			list[b].add(a);
		}

		bfs();
		br.close();
	}

	private static void bfs() {
		boolean[] visit = new boolean[N + 1];
		ArrayDeque<Integer> q = new ArrayDeque<>();
		visit[1] = true;
		q.offer(1);

		int min = 0, dist = -1, cnt = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			cnt = 0;
			dist++;
			min = Integer.MAX_VALUE;

			for (int i = 0; i < size; i++) {
				int cur = q.poll();
				min = Math.min(min, cur);
				cnt++;

				for (int nxt : list[cur]) {
					if (!visit[nxt]) {
						visit[nxt] = true;
						q.offer(nxt);
					}
				}
			}
		}

		System.out.println(min + " " + dist + " " + cnt);
	}
}