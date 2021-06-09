import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 9.
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
 */
public class Boj_G4_2206_벽부수고이동하기 {
	static int[][] map;
	static int[][][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		dist = new int[N][M][2];

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j) - '0';
			}
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 이동하면서 벽을 부쉈다는 상태를 3차원 배열의 인덱스로 나타냄  */
	private static int bfs() {
		Queue<int[]> q = new LinkedList<>();
		dist[0][0][0] = 1;// 행, 열, 벽을 부순 횟수
		q.offer(new int[] { 0, 0, 0 });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			if (cur[0] == N - 1 && cur[1] == M - 1) {
				return dist[cur[0]][cur[1]][cur[2]];
			}
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || dist[nx][ny][cur[2]] > 0) {
					continue;
				}

				if (map[nx][ny] == 1) {// 벽을 만났다면
					if (cur[2] == 1 || (cur[2] == 1 && dist[nx][ny][cur[2] - 1] > 0)) {// 이미 벽을 부순적이 있다면 continue
						continue;
					}
					dist[nx][ny][cur[2] + 1] = dist[cur[0]][cur[1]][cur[2]] + 1;
					q.offer(new int[] { nx, ny, cur[2] + 1 });// 벽을 부순 횟수 증가
				} else {
					dist[nx][ny][cur[2]] = dist[cur[0]][cur[1]][cur[2]] + 1;
					q.offer(new int[] { nx, ny, cur[2] });
				}
			}
		}
		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}