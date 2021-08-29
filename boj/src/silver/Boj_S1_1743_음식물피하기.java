package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 8.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 실버1
 * 혼자 품: O
 * 풀이: 
 * 1. K개 만큼 입력받은 음식물 좌표들을 map 배열에 1로 저장
 * 2. 모든 좌표를 bfs를 통해 음식물 크기 갱신
 * 느낀 점:
 * 기본에 익숙해져야 응용 문제에서 시간을 아낄 수 있다는 마인드로 선정한 문제라서 예전보다 빠르고 정확하게 풀었다.
 */
public class Boj_S1_1743_음식물피하기 {
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		map = new int[N + 1][M + 1];
		visit = new boolean[N + 1][M + 1];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			map[x][y] = 1;// 음식물 1로 정의
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (map[i][j] == 1 && !visit[i][j]) {
					bfs(i, j);
				}
			}
		}

		System.out.println(res);
		br.close();
	}

	private static void bfs(int x, int y) {
		int sum = 1;
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny] || map[nx][ny] == 0) {
					continue;
				}
				visit[nx][ny] = true;
				q.offer(new int[] { nx, ny });
				sum++;
			}
		}

		res = Math.max(res, sum);
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 1 || x > N || y < 1 || y > M;
	}
}