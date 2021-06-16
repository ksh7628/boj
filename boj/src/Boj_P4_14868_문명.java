import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 16.
 *
 * 분류: 그래프 이론, 자료 구조, 그래프 탐색, 너비 우선 탐색, 분리 집합
 * 난이도: 플래티넘4
 * 혼자 품: O
 * 풀이: 
 * 1. 최초 입력값들을 받을 때 큐에 넣어주면서 바로 합칠 수 있다면 유니온 파인드 연산을 통해 합쳐준다.
 * 2. bfs를 통해 탐색해가면서 다른 문명을 만났다면 합쳐준다.
 * 3. 한번 더 사방 체크를 통해 합칠 수 있다면 합쳐준다.
 * 4. 모두 합쳐졌다면 탐색을 중단하고 걸린 시간을 출력한다.
 * 느낀 점: 
 * 처음으로 푼 플래티넘4 문제여서 느낌이 새로운데 최초 입력 때 합칠 수 있는 문명들을 처리를 안해주어서 좀 헤매게 되었다.
 * 하지만 성능이 썩 좋지 못해서 나중에 다른 사람들의 코드를 참조해서 리팩토링 한 후에 비슷한 문제인 https://www.acmicpc.net/problem/3197 문제를 시도해 봐야겠다.
 * 여담으로 bfs에 union-find 를 접목한 새로운 유형의 문제였는데 풀이법을 알게 되어 좋았다.
 */
public class Boj_P4_14868_문명 {
	static ArrayDeque<int[]> q = new ArrayDeque<>();
	static int[][] map;
	static int[] p;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, K, cnt;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N + 1][N + 1];
		p = new int[K + 1];
		cnt = K;

		for (int i = 1; i <= K; i++) {
			p[i] = i;
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			map[x][y] = i;
			q.offer(new int[] { x, y, i });
			boundCheck(x, y, i);// 최초 입력값 중에서 합칠 수 있다면 합쳐줌
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 모든 문명이 하나로 합쳐질때까지 탐색 */
	private static int bfs() {
		int time = 0;

		while (!q.isEmpty()) {
			if (cnt == 1) {// 모두 하나로 합쳐졌다면 햇수 반환
				return time;
			}

			int size = q.size();
			for (int i = 0; i < size; i++) {// 한칸씩 탐색
				int[] cur = q.poll();

				for (int dir = 0; dir < 4; dir++) {
					int nx = cur[0] + dx[dir];
					int ny = cur[1] + dy[dir];
					int num = find(cur[2]);

					if (check(nx, ny) || map[nx][ny] == num) {// 배열 범위 벗어나거나 같은 문명이면 다음 탐색으로
						continue;
					}

					if (map[nx][ny] == 0) {// 미개 지역이면 해당 문명이 전파
						map[nx][ny] = num;
						q.offer(new int[] { nx, ny, num });
					} else if (union(cur[2], map[nx][ny])) {// 다른 문명이라면 union 연산을 통해서 안합쳐졌다면 합침
						cnt--;
						map[nx][ny] = num;
						q.offer(new int[] { nx, ny, num });
					}

					boundCheck(nx, ny, num);// 마지막으로 사방 체크를 통해 합칠 수 있는지 체크
				}
			}

			time++;
		}

		return time;
	}

	/* 사방을 체크해서 다른 문명을 만나면 합쳐줌 */
	private static void boundCheck(int x, int y, int num) {
		for (int dir = 0; dir < 4; dir++) {
			int nx = x + dx[dir];
			int ny = y + dy[dir];

			if (check(nx, ny) || map[nx][ny] == num) {
				continue;
			}

			if (map[nx][ny] != 0 && union(num, map[nx][ny])) {
				cnt--;
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 1 || x > N || y < 1 || y > N;
	}

	private static int find(int x) {
		if (x == p[x]) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static boolean union(int x, int y) {
		int a = find(x);
		int b = find(y);

		if (a != b) {
			p[b] = a;
			return true;
		}
		return false;
	}
}