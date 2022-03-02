package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 2.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 0h 32m
 * 혼자 품: O
 * 풀이: 
 * 1. 섬들을 찾아서 각 섬마다 dfs를 통해 번호를 매긴다. -> 이 때, 모서리는 섬의 번호에 -1을 곱한 값을 저장한다.
 * 2. 각 섬마다 모서리의 좌표 리스트 배열에 저장한다.
 * 3. 모든 모서리 쌍에 대해 현재 섬의 모서리와 다른 섬의 모서리의 맨해튼 거리를 구해서 최솟값을 갱신한다.
 * 느낀 점: 
 * 딱 1년전에 막 bfs를 배우고 정말 어렵게 풀고 시간도 너무 간당간당하게 푼 문제여서 다시 풀게 되었다.
 * 푸는 시간도 많이 줄었고, 성능도 많이 발전하게 되었지만 모든 모서리 쌍에 대해 브루트포스를 적용하다 보니 엄청 좋은 알고리즘을 구현한 것은 아니다.
 * 나중에 더 실력이 발전하면 또 리팩토링을 시도해 볼 수 있는 문제라고 생각한다.
 */
public class Boj_G3_2146_다리만들기 {
	static List<int[]>[] side;
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, S;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		visit = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		floodFill();
		addSide();
		return findMinBridge();
	}

	/* 각 섬마다 dfs를 수행해서 번호를 매긴다 */
	private static void floodFill() {
		S = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visit[i][j] && map[i][j] == 1) {
					dfs(i, j);
					S++;
				}
			}
		}
	}

	/* dfs를 통해 섬을 만든다 */
	private static void dfs(int x, int y) {
		map[x][y] = S;
		visit[x][y] = true;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (!isCheck(nx, ny) || visit[nx][ny]) {
				continue;
			}

			if (map[nx][ny] == 0) {
				map[x][y] = -S;
			}

			if (map[nx][ny] == 1) {
				dfs(nx, ny);
			}
		}
	}

	/* 각 섬의 모서리 좌표를 리스트에 섬 번호 별로 넣는다 */
	@SuppressWarnings("unchecked")
	private static void addSide() {
		side = new ArrayList[S];
		for (int i = 1; i < S; i++) {
			side[i] = new ArrayList<>();
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] < 0) {
					side[-map[i][j]].add(new int[] { i, j });
				}
			}
		}
	}

	/* 모든 모서리 쌍에 대해 다른 섬의 모서리와 맨해튼 거리의 최솟값을 구한다 */
	private static int findMinBridge() {
		int min = Integer.MAX_VALUE;

		for (int i = 1; i < S; i++) {
			for (int j = i + 1; j < S; j++) {
				for (int[] p1 : side[i]) {
					for (int[] p2 : side[j]) {
						min = Math.min(min, getDistance(p1, p2));
					}
				}
			}
		}

		return min;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	/* 맨해튼 거리 계산 */
	private static int getDistance(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) - 1;
	}
}