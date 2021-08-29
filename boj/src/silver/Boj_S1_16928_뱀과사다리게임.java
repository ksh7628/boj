package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 21.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버1
 * 혼자 품: X
 * 풀이: 
 * 1. 사다리와 뱀이 겹치지 않으므로 사다리 정보와 뱀 정보를 모두 path에 저장 해준다.
 * 2. bfs를 통해 주사위를 굴렸을 떄 사다리나 뱀이 있다면 방문처리 후 큐에 넣어주고 그렇지 않다면 방문처리없이 큐에 넣어준다.
 * 느낀 점: 
 * 간단한 문제일줄 알았는데 사다리나 뱀이 있을 경우 무조건 타야하고 주사위를 굴리지 않으므로 카운팅을 해줘서는 안되기 때문에 이 부분을 주의한다면 어렵지 않은 문제인것 같다.
 * 문제를 제대로 읽지 않아서 결국 다른 사람의 코드를 참조하게 되었다. 항상 문제를 잘 읽는 습관을 기르도록 하자.
 */
public class Boj_S1_16928_뱀과사다리게임 {
	static class Node {
		int num, cnt;

		public Node(int num, int cnt) {
			super();
			this.num = num;
			this.cnt = cnt;
		}
	}

	static int[] path = new int[101];
	static boolean[] visit = new boolean[101];
	static int res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		for (int i = 0; i < N + M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			path[x] = y;
		}

		bfs();
		System.out.println(res);
		br.close();
	}

	/* bfs를 통해 최소 횟수를 구함 */
	private static void bfs() {
		ArrayDeque<Node> q = new ArrayDeque<>();
		visit[1] = true;
		q.offer(new Node(1, 0));// 1번부터 시작

		while (!q.isEmpty()) {
			Node cur = q.poll();
			if (cur.num == 100) {// 100번에 도착하면 횟수를 저장하고 탐색 종료
				res = cur.cnt;
				return;
			}

			for (int d = 1; d <= 6; d++) {
				int nxt = cur.num + d;
				if (nxt > 100 || visit[nxt]) {
					continue;
				}

				visit[nxt] = true;
				if (path[nxt] != 0) {// 사다리나 뱀이 있다면 사다리나 뱀을 탄 끝점을 방문처리 후 큐에 넣음
					if (!visit[path[nxt]]) {
						visit[path[nxt]] = true;
						q.offer(new Node(path[nxt], cur.cnt + 1));
					}
				} else {// 둘 다 없다면 주사위를 굴린 값을 큐에 넣음
					q.offer(new Node(cur.num + d, cur.cnt + 1));
				}
			}
		}
	}
}