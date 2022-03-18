package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 18.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드3
 * 소요 시간: 0h 48m
 * 혼자 품: O
 * 풀이: 
 * 1. 달팽이(소용돌이) 이동: 좌->하->우->상 순으로 이동하면서 거리를 방향이 2번 바뀔때마다 1씩 늘린다.
 * 2. 토네이도 이동 시 퍼지는 모래의 좌표를 sx,sy 2차원 배열로 표현, 각 퍼센트를 per 배열에 저장한다.
 * 3. a의 값: y - 나머지 흩어진 모래의 양 으로 계산한다.
 * 느낀 점: 이전에 풀어봤던 문제라서 시간을 단축했고 코드의 길이도 줄이게 되어 만족스럽다.
 */
public class Boj_G3_20057_마법사상어와토네이도 {
	static int[][] map;
	static int[][] sx = { { -1, 1, -2, 2, -1, 1, -1, 1, 0, 0 }, { -1, -1, 0, 0, 0, 0, 1, 1, 2, 1 }, { -1, 1, -2, 2, -1, 1, -1, 1, 0, 0 },
			{ 1, 1, 0, 0, 0, 0, -1, -1, -2, -1 } };// 좌하우상
	static int[][] sy = { { 1, 1, 0, 0, 0, 0, -1, -1, -2, -1 }, { -1, 1, -2, 2, -1, 1, -1, 1, 0, 0 }, { -1, -1, 0, 0, 0, 0, 1, 1, 2, 1 },
			{ -1, 1, -2, 2, -1, 1, -1, 1, 0, 0 } };
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { -1, 0, 1, 0 };
	static int[] per = { 1, 2, 7, 10, 5 };
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();
		System.out.println(res);
		br.close();
	}

	// 모래 퍼지는 규칙 -> bfs?
	// -> 예외 처리할게 너무 많고 토네이도 방향마다 또 다르다
	// -> [토네이도 방향][모래 퍼지는방향] 2차원 배열로 값 세팅이 나을거 같다
	// 달팽이 이동 -> 좌하우상 순 -> 1,1,2,2,3,3,...
	private static void solution() {
		int x = N / 2, y = N / 2;
		int d = 0, len = 1;

		loop: while (true) {
			for (int i = 0; i < len; i++) {
				x += dx[d];
				y += dy[d];
				// y좌표 -1 되는순간 중단
				if (y < 0) {
					break loop;
				}
				setTornado(x, y, d);
			}

			if (++d % 2 == 0) {
				len++;
				if (d == 4) {
					d = 0;
				}
			}
		}
	}

	// 배열 범위 벗어나는 모래만큼 res에 누적
	// a(sx,sy의 9번 인덱스): y에 있는 모래 양 - 퍼센트 합 양 => 소수점 버린다
	private static void setTornado(int x, int y, int td) {
		int amount = map[x][y];

		for (int d = 0; d < 9; d++) {
			int nx = x + sx[td][d];
			int ny = y + sy[td][d];
			int req = amount * per[d / 2] / 100;
			
			map[x][y] -= req;
			// 범위 밖으로 나감
			if (!isCheck(nx, ny)) {
				res += req;
				continue;
			}
			// 안 나감
			map[nx][ny] += req;
		}

		// a 자리에 남은 모래를 저장
		int ax = x + sx[td][9], ay = y + sy[td][9];
		if (!isCheck(ax, ay)) {
			res += map[x][y];
		} else {
			map[ax][ay] += map[x][y];
		}
		// 모든 모래가 퍼짐
		map[x][y] = 0;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}