package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 3.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 
 * 일반적인 bfs처럼 방문할 수 있는 정점을 하나씩 방문하지만, set에 넣고 order 배열에 순서대로 저장되어 있는 정점들을 확인하면서
 * 삭제가 가능하다면 순서대로 진행중이고 삭제가 불가능하다면 올바른 순서가 아니라는 것을 판단할 수 있다.
 * 느낀 점: 
 * list와 set을 한 번에 사용할 생각을 하지 못해서 맞왜틀만 반복하다가 다른 사람의 코드를 참조하고 나서야 이해가 가게 되었다.
 * 골드3이상의 문제는 기본기는 물론이고 설계할 때 더 깊이 생각해봐야 되는 것 같다.
 */
public class Boj_G3_16940_BFS스페셜저지 {
	static List<Integer>[] list;
	static boolean[] visit;
	static int[] order;
	static int N;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		list = new ArrayList[N + 1];
		visit = new boolean[N + 1];
		order = new int[N];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		StringTokenizer st = null;
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			// 양방향 인접 리스트
			list[from].add(to);
			list[to].add(from);
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			order[i] = Integer.parseInt(st.nextToken());
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 탐색하면서 올바른 순서인지 검사한다 */
	private static int bfs() {
		// 시작 정점이 1이 아니라면 올바른 순서가 아니다
		if (order[0] != 1) {
			return 0;
		}

		Set<Integer> set = new HashSet<>();
		ArrayDeque<Integer> q = new ArrayDeque<>();
		visit[1] = true;
		q.offer(1);

		int idx = 1;
		while (!q.isEmpty()) {
			int cur = q.poll();

			for (int nxt : list[cur]) {
				// 다음 방문할 수 있는 정점에 대해 아직 방문하지 않았다면 set에 해당 정점을 넣는다
				if (!visit[nxt]) {
					visit[nxt] = true;
					set.add(nxt);
				}
			}

			int size = set.size();
			// set을 순회하면서 올바른 방문 순서인지 확인
			for (int i = 0; i < size; i++) {
				// 삭제 가능하다면 올바른 순서이므로 큐에 넣고 다음 순서를 확인하기 위해 인덱스 1 증가
				if (set.remove(order[idx])) {
					q.offer(order[idx++]);
				} else {// 삭제가 불가능하면 올바른 순서가 아니다 
					return 0;
				}
			}
		}

		return 1;
	}
}