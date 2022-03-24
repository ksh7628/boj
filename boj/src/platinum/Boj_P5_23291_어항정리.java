package platinum;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 24.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 플래티넘5
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 쌓을려는 어항: 서남동북 순서의 달팽이 모양, 쌓이는 어항: 북서남동 순서의 달팽이 모양 규칙
 * 1-1. 기준 열은 쌓을 때마다 갱신, 더 이상 쌓을 수 없다면 기준 행 갱신 후 쌓기 종료
 * 2. 동시에 작업해야 하므로 tmap 배열을 선언해서 1-1에서 구한 기준 행,열부터 시작해서 방문 미처리 된곳에 대해 물고기 수를 조절한다.
 * 3. 어항을 일렬로 편다. -> 1차원 배열에 하나씩 복사한 후 원본 배열 N-1행에 복사
 * 4. 두 번의 가운데 중심의 공중부양 작업을 수행한다.
 * -> 0 ~ N/2-1열까지 2층에 올리고 다시 N/2 ~ N/3-1열까지 3,4층에 쌓는다.
 * 5. 2번 3번 과정을 진행한다.
 * 6. 1~5를 반복하다가 max - min <= K를 만족한다면 수행 횟수를 출력 후 종료.
 * 느낀 점: 배열을 정말 잘 다뤄야 풀 수 있는 문제였던 것 같다. 다시 풀었을 때 문제 푸는 시간을 단축해야겠다.
 */
public class Boj_P5_23291_어항정리 {
	static int[][] map;
	static int[] dx = { 0, 1, 0, -1 };// 서남동북
	static int[] dy = { -1, 0, 1, 0 };
	static int N, K, row, col, max, min;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			map[N - 1][i] = Integer.parseInt(st.nextToken());
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int res = 0;
		while (true) {
			max = 0;
			min = 10001;
			if (getDistance()) {
				break;
			}

			stackLeft();// 1. 왼쪽에서 오른쪽으로 어항을 쌓는다
			controlCount();// 2. 어항에 있는 물고기 수 조절한다
			setLine();// 3. 바닥에 일렬로 놓는다
			stackMiddle();// 4. 가운데를 중심으로 어항을 쌓는다
			controlCount();// 5. 어항에 있는 물고기 수 조절한다
			setLine();// 6. 바닥에 일렬로 놓는다
			res++;
		}

		return res;
	}

	/*
	 * 최댓값과 최솟값의 차를 구해서 K이하면 종료
	 * 아니라면 최솟값에 해당하는 어항의 물고기 수 1증가
	 */
	private static boolean getDistance() {
		for (int i = 0; i < N; i++) {
			max = Math.max(max, map[N - 1][i]);
			min = Math.min(min, map[N - 1][i]);
		}

		if (max - min <= K) {
			return true;
		}

		for (int i = 0; i < N; i++) {
			if (map[N - 1][i] == min) {
				map[N - 1][i]++;
			}
		}

		return false;
	}

	/*
	 * 달팽이 모양
	 * 쌓여있는 배열을 서남동북 순으로 순회해서 북서남동 순으로 쌓는다
	 * 새로 쌓을 어항들의 높이가 남은 바닥의 길이보다 크면 종료
	 */
	private static void stackLeft() {
		int h = 1, w = 1, cnt = 1;
		int size = 1, len = 0;
		boolean sign = false;
		map[N - 2][1] = map[N - 1][0];

		int distance = N - 1;
		for (int i = 0;; i++) {
			distance -= h + 1;
			// 더 이상 공중 부양시킬 수 없으면 종료
			if (distance <= 0) {
				// 기준 행 설정
				row = N - 1 - h;
				break;
			}

			cnt++;
			int sx = N - 1 - h, sy = w + 1;
			int ex = N - 1, ey = sy + size;

			if (i % 2 == 0) {
				sign = true;
				w += size + 1;
			} else {
				sign = false;
				h++;
				size++;
				w += size;
			}

			len = size;
			for (int d = 0; d < cnt; d++) {
				for (int j = 0; j < len; j++) {
					sx += dx[d % 4];
					sy += dy[d % 4];
					ex += dx[(d + 3) % 4];
					ey += dy[(d + 3) % 4];
					map[ex][ey] = map[sx][sy];
				}

				// 기준 열 설정
				if (d == 1) {
					col = ey;
				}

				if (sign) {
					sign = false;
				} else {
					sign = true;
					len--;
				}
			}
		}
	}

	/* 물고기가 있는 모든 인전합 어항에 대해 동시에 수 조절 */
	private static void controlCount() {
		int[][] tmap = new int[N - row][N - col];
		boolean[][] visit = new boolean[N - row][N - col];

		for (int i = row; i < N; i++) {
			for (int j = col; j < N; j++) {
				if (map[i][j] == 0) {
					break;
				}

				visit[i - row][j - col] = true;
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];

					if (!isCheck(nx, ny) || map[nx][ny] == 0 || visit[nx - row][ny - col]) {
						continue;
					}

					int distance = Math.abs(map[nx][ny] - map[i][j]) / 5;
					if (distance == 0) {
						continue;
					}

					if (map[nx][ny] > map[i][j]) {
						tmap[nx - row][ny - col] -= distance;
						tmap[i - row][j - col] += distance;
					} else {
						tmap[nx - row][ny - col] += distance;
						tmap[i - row][j - col] -= distance;
					}
				}
			}
		}

		for (int i = row; i < N; i++) {
			for (int j = col; j < N; j++) {
				map[i][j] += tmap[i - row][j - col];
			}
		}
	}

	/* 기준점으로부터 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return row <= x && x < N && col <= y && y < N;
	}

	/* 쌓은 어항을 일렬로 놓는다 */
	private static void setLine() {
		int[] tmap = new int[N];
		int idx = 0;
		for (int j = col; j < N; j++) {
			for (int i = N - 1; i >= row; i--) {
				if (map[i][j] == 0) {
					break;
				}

				tmap[idx++] = map[i][j];
			}
		}

		map = new int[N][N];
		System.arraycopy(tmap, 0, map[N - 1], 0, N);
	}

	/* 가운데를 중심으로 공중 부양 */
	private static void stackMiddle() {
		int start = 0, middle = N / 2;
		for (int i = 0; i < middle; i++) {
			map[N - 2][N - 1 - i] = map[N - 1][i];
		}

		start = middle;
		middle = N / 4 * 3;
		int len = 0;

		for (int j = middle - 1; j >= start; j--) {
			map[N - 3][middle + len] = map[N - 2][j];
			map[N - 4][middle + len] = map[N - 1][j];
			len++;
		}

		row = N - 4;
		col = middle;
	}
}