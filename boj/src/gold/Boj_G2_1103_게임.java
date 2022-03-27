package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: ksh76
 * @date	: 2022. 3. 27.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드2
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * dfs + dp를 사용, dp에 현재 좌표까지 진행된 게임 횟수를 기록하고 다시 방문했을 때 진행 횟수가 안크다면 가지치기
 * 현재 좌표에서 시작된 dfs가 안끝났는데 현재 좌표를 또 방문할 경우 사이클이 형성되므로 나머지 탐색을 중단한다.
 * 느낀 점: 
 * <와 <=차이가 시간초과를 발생시켰다. 또한 사이클 판단을 잘못 생각해서 시간이 오래 걸렸다.
 * 그외에는 접근을 이전보다 잘한 것 같다. dfs + dp 유형에 익숙해지는것 같다.
 */
public class Boj_G2_1103_게임 {
	static int[][] dp;
	static char[][] map;
	static boolean[][] isFinished;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, res;
	static boolean isCycle;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		dp = new int[N][M];
		map = new char[N][M];
		isFinished = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		dfs(0, 0, 1);
		System.out.println(isCycle ? -1 : res);
		br.close();
	}

	/* dfs로 게임 횟수를 dp배열에 기록, 도중에 사이클이 생성되면 중단 */
	private static void dfs(int x, int y, int cnt) {
		// 사이클이 형성되었거나 이미 방문한 곳의 진행 수보다 현재 진행 수가 작거나 같다 -> 가지치기
		if (isCycle || cnt <= dp[x][y]) {
			return;
		}

		// 사이클 찾음
		if (isFinished[x][y]) {
			isCycle = true;
			return;
		}

		// 최댓값 갱신
		if (res < cnt) {
			res = cnt;
		}

		isFinished[x][y] = true;
		dp[x][y] = cnt;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d] * (map[x][y] - '0');
			int ny = y + dy[d] * (map[x][y] - '0');

			if (isCheck(nx, ny) && map[nx][ny] != 'H') {
				dfs(nx, ny, cnt + 1);
			}
		}

		isFinished[x][y] = false;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}