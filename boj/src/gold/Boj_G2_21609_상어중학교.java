package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 13.
 * 
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 시뮬레이션, 깊이 우선 탐색
 * 난이도: 골드2
 * 소요 시간: 0h 53m
 * 혼자 품: O
 * 풀이: 
 * 1. 모든 맵에서 dfs를 수행해가며 가장 큰 블록 그룹을 찾는다
 * 2. 1에서 찾은 블록 그룹의 모든 블록을 제거하기 위해 dfs를 한 번 더 수행한다.
 * 3. 중력 -> 반시계 90도 회전 -> 중력
 * 느낀 점: 
 * 3번째로 풀게 되었는데 이전과 다른 점은 bfs를 2번 수행하던것을 dfs 1번 수행으로 고침
 * 그룹 찾고 지우는 과정 동작 방식이 동일하기 때문에 인자에 boolean 변수 하나를 더 추가해서 메소드 재사용성을 높임
 * 그 외에 성능상으로 아쉬운 점이라면 중력 작용(블록 떨어트리기)인데 삼성 A형은 성능 이전에 빠르게 푸는게 중요해서 익숙한 방식을 사용하게 되었다.
 */
public class Boj_G2_21609_상어중학교 {
	static ArrayDeque<int[]> rainbow = new ArrayDeque<>();
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, size, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 0) {
					map[i][j] = 6;// 무지개 블록 0 -> 6
				}
			}
		}

		solution();
		System.out.println(res);
		br.close();
	}

	/* 지문 1 ~ 5 과정 반복 */
	private static void solution() {
		while (isAutoPlay()) {// 1, 2
			setGravity();// 3
			rotateMap();// 4
			setGravity();// 5
		}
	}

	/* 
	 * 오토 플레이 작동
	 * 무지개 블록은 서로 공유가 가능하다
	 * -> 한번 방문처리를 한 후 다음 탐색 전에 무지개 블록 좌표는 다시 방문해제
	 * 기준 블록은 행, 열 가장 작은순으로
	 * 블록 그룹을 찾을 때에는 블록 크기, 무지개 블록 개수가 같으면 행, 열 큰 순으로
	 * -> 2중 for문 (행, 열): 0 ~ N-1 순으로
	 */
	private static boolean isAutoPlay() {
		visit = new boolean[N][N];
		boolean flag = false;
		int maxSize = 0, maxRB = 0, row = -1, col = -1;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (1 <= map[i][j] && map[i][j] <= M && !visit[i][j]) {
					size = 0;
					dfs(i, j, map[i][j], false);

					if (size < 2) {
						continue;
					}

					if (maxSize < size) {
						maxSize = size;
						maxRB = rainbow.size();
						row = i;
						col = j;
					} else if (maxSize == size && maxRB <= rainbow.size()) {
						maxRB = rainbow.size();
						row = i;
						col = j;
					}

					// 다른 블록에서 무지개 블록을 참조하기 위해 방문 처리 해제
					while (!rainbow.isEmpty()) {
						int[] p = rainbow.poll();
						visit[p[0]][p[1]] = false;
					}

					flag = true;
				}
			}
		}

		if (!flag) {
			return false;
		}

		visit = new boolean[N][N];
		size = 0;
		dfs(row, col, map[row][col], true);
		rainbow.clear();
		res += size * size;

		return true;
	}

	/* dfs로 해당 그룹의 블록 개수를 구함 */
	private static void dfs(int x, int y, int num, boolean isRemove) {
		size++;
		visit[x][y] = true;

		// 2번 과정 수행시에는 isRemove가 true로 고정
		if (isRemove) {
			map[x][y] = 0;
		}

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny) && !visit[nx][ny] && (map[nx][ny] == num || map[nx][ny] == 6)) {
				if (map[nx][ny] == 6) {
					rainbow.offer(new int[] { nx, ny });
				}

				dfs(nx, ny, num, isRemove);
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	/* 검은색 블록을 제외하고 중력 작용 */
	private static void setGravity() {
		for (int j = 0; j < N; j++) {
			for (int i = N - 2; i >= 0; i--) {
				if (map[i][j] <= 0) {
					continue;
				}

				int nx = i + 1, num = map[i][j];
				while (nx < N && map[nx][j] == 0) {
					map[nx - 1][j] = 0;
					map[nx++][j] = num;
				}
			}
		}
	}

	/* 반시계 방향으로 회전 */
	private static void rotateMap() {
		int[][] tmap = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tmap[N - 1 - j][i] = map[i][j];
			}
		}

		for (int i = 0; i < N; i++) {
			System.arraycopy(tmap[i], 0, map[i], 0, N);
		}
	}
}