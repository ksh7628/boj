package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 17.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버2
 * 소요 시간: 0h 06m
 * 혼자 품: O
 * 풀이: 
 * 1. 인접 리스트에 입력값을 저장한 후 오름차순으로 방문하기 위해 정렬한다.
 * 2. R을 시작점으로 하는 bfs를 수행하면서 방문 순서를 visit에 저장하고 N번까지의 순서를 출력한다.
 * 느낀 점: boj.kr/24479와 유형이 같음. dfs대신 bfs사용.
 */
public class Boj_S2_24444_알고리즘수업너비우선탐색1 {
	static List<Integer>[] list;
	static int[] visit;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		visit = new int[N + 1];

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

		for (int i = 1; i <= N; i++) {
			Collections.sort(list[i]);
		}

		bfs(R);

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= N; i++) {
			sb.append(visit[i]).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	private static void bfs(int start) {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		int order = 0;
		visit[start] = ++order;
		q.offer(start);

		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int cur = q.poll();

				for (int nxt : list[cur]) {
					if (visit[nxt] == 0) {
						visit[nxt] = ++order;
						q.offer(nxt);
					}
				}
			}
		}
	}
}