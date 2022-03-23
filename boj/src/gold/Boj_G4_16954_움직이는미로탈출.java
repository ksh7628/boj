package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 22.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드4
 * 소요 시간: 1h 00m
 * 혼자 품: O
 * 풀이: 
 * 1. bfs로 탐색하여 매 시간마다 visit을 초기화 해서 8방향 + 제자리를 탐색한 후 벽을 내린다.
 * 2. (0, 7)에 도착 가능하다면 1 리턴 그게 아니라면 큐를 모두 비운 후 0 리턴.
 * 느낀 점: visit 배열을 사용하지 않고 풀었을 때 성능이 너무 안좋게 나와서 매번 초기화 해주는 식으로 푸니 괜찮게 푼 것 같다.
 */
public class Boj_G4_16954_움직이는미로탈출 {
	static char[][] map = new char[8][8];
	static int[] dx = { 0, -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 8; i++) {
			map[i] = br.readLine().toCharArray();
		}

		System.out.println(bfs());
		br.close();
	}

	private static int bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { 7, 0 });

		while (!q.isEmpty()) {
			int size = q.size();
			boolean[][] visit = new boolean[8][8];

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();
				if (cur[0] == 0 && cur[1] == 7) {
					return 1;
				}

				if (map[cur[0]][cur[1]] == '#') {
					continue;
				}

				for (int d = 0; d < 9; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] == '.') {
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					}
				}

			}

			downWall();
		}

		return 0;
	}

	/* 벽을 한칸씩 밑으로 내린다 */
	private static void downWall() {
		for (int j = 0; j < 8; j++) {
			map[7][j] = '.';
		}

		for (int j = 0; j < 8; j++) {
			for (int i = 6; i >= 0; i--) {
				if (map[i][j] == '#') {
					map[i + 1][j] = '#';
					map[i][j] = '.';
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < 8 && 0 <= y && y < 8;
	}
}