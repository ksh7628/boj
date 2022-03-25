package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 25.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 맵의 정보를 입력받는데 공백이 주어지지 않으므로 문자를 정수형으로 치환해서 저장
 * 2-1. bfs를 통해 탐색 시작
 * 2-2. 벽을 만났다면 현재까지 벽을 부쉈는지 인덱스를 통해 확인하여 부술 수 있다면 부순 횟수를 증가시키고 거리 갱신 및 방문 처리 후 큐에 넣음
 * 2-3. 벽이 아니라면 평범한 bfs처럼 거리갱신, 방문처리 후 큐에 넣음
 * 느낀 점: 
 * 예전에 정말 어렵게 느껴졌던 문제라서 다시 풀었는데 저번보다는 성능도 조금 향상되긴 했다.
 * 하지만 조건문을 더 사용한것과 3차원 배열을 사용해서 그런지 다른 사람들이 푼 코드에 비해 성능이 좋지 못했던 점이 아쉽다.
 * 1차 리팩토링: 메모리 조금이라도 아끼기 위해 int -> char로 map을 선언, 큐에는 객체 3개만 담고 거리는 큐 사이클마다 증가하는 식으로 풀이.
 */
public class Boj_G4_2206_벽부수고이동하기 {
	static char[][] map;
	static boolean[][][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[2][N][M];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		System.out.println(bfs());
		br.close();
	}

	private static int bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[0][0][0] = true;
		q.offer(new int[] { 0, 0, 0 });

		// 시작하는 칸 포함해서 거리를 셈
		int dist = 1;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();
				if (cur[0] == N - 1 && cur[1] == M - 1) {
					return dist;
				}

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (!isCheck(nx, ny) || visit[cur[2]][nx][ny]) {
						continue;
					}

					// 이미 벽을 부쉈는데 벽을 만나면 못부숨
					if (cur[2] == 1 && map[nx][ny] == '1') {
						continue;
					}

					// 이동 가능
					if (map[nx][ny] == '0') {
						visit[cur[2]][nx][ny] = true;
						q.offer(new int[] { nx, ny, cur[2] });
					// 벽을 부술 수 있고 부쉈는데 방문 안한 경우
					} else if (cur[2] == 0 && !visit[1][nx][ny]) {
						visit[1][nx][ny] = true;
						q.offer(new int[] { nx, ny, 1 });
					}
				}
			}

			dist++;
		}

		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}