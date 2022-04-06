package gold;

import java.io.*;
import java.util.*;

/**
 * @author : KimSeonhong
 * @date : 2022. 4. 6.
 * 
 * 분류: 백트래킹
 * 난이도: 골드5
 * 소요 시간: 0h 21m
 * 혼자 품: O
 * 풀이: 4가지 모양의 부메랑을 만들 수 있는지 체크하고 만들 수 있다면 다음 dfs 끝날때까지 현재 부메랑 좌표 체크 후 끝나면 체크 해제하여 최댓값 갱신
 * 느낀 점: 인덱스 지정 실수를 해서 다시 생각하고 풀게 됨.
 */
public class Boj_G5_18430_무기공학 {
	static int[][] map;
	static boolean[][] isUsed;
	static int[] dx = { -1, 0, 1, 0 };// 북동남서
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		isUsed = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		dfs(0, 0, 0);
		System.out.println(res);
		br.close();
	}

	/* dfs로 탐색하면서 최댓값을 갱신 */
	private static void dfs(int x, int y, int sum) {
		res = Math.max(res, sum);

		for (int i = x; i < N; i++) {
			for (int j = y; j < M; j++) {
				if (isUsed[i][j]) {
					continue;
				}
				isUsed[i][j] = true;

				for (int d = 0; d < 4; d++) {
					int nx1 = i + dx[d];
					int ny1 = j + dy[d];
					int nx2 = i + dx[(d + 1) % 4];
					int ny2 = j + dy[(d + 1) % 4];

					if (isCheck(nx1, ny1) && isCheck(nx2, ny2) && !isUsed[nx1][ny1] && !isUsed[nx2][ny2]) {
						if (j + 1 == M) {// 끝열이면 다음 행부터 탐색
							isUsed[nx1][ny1] = true;
							isUsed[nx2][ny2] = true;
							dfs(i + 1, 0, sum + 2 * map[i][j] + map[nx1][ny1] + map[nx2][ny2]);
							isUsed[nx1][ny1] = false;
							isUsed[nx2][ny2] = false;
						} else {// 아니라면 다음 열부터 탐색
							isUsed[nx1][ny1] = true;
							isUsed[nx2][ny2] = true;
							dfs(i, j + 1, sum + 2 * map[i][j] + map[nx1][ny1] + map[nx2][ny2]);
							isUsed[nx1][ny1] = false;
							isUsed[nx2][ny2] = false;
						}
					}
				}

				isUsed[i][j] = false;
			}
			
			y = 0;// 한번 y ~ M - 1까지 수행했다면 다시 0열부터 탐색
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}