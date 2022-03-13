package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 13.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드2
 * 소요 시간: 1h 17m
 * 혼자 품: O
 * 풀이: 
 * 덱을 2개 사용해서 품. 칸 색깔에 따라 경우가 나뉜다.
 * 해당 말 차례가 되면 원본 덱에서 해당 말을 찾을 때까지 원본 덱에서 임시 덱으로 이동한다. -> temp to horseQ(stack to queue)
 * 1. 파란색: 방향을 바꾼다. 바꿨는데도 갈 수 없는 경우에는 해당 말 방향만 바꾼 채로 이동한다. -> temp to horseQ(stack to queue)
 * 2. 빨간색: 역순으로 이동한다. temp to horseQ(queue to queue)
 * 3. 흰색: 원래 순으로 이동한다. temp to horseQ(stack to queue)
 * 느낀 점: 
 * 이동하려는 칸이 파란색 or 범위 밖인 경우에 위에 있는 말들까지 방향을 바꿔서 디버깅하느라 시간을 오래 쓰게 되었다.
 * 그래도 예전에 손도 못대는 문제였는데 점점 구현력이 늘어가는거 같아서 ps에 성취감이 생김을 느낀다.
 */
public class Boj_G2_17837_새로운게임2 {
	static class Horse {
		int x, y, d;

		public Horse(int x, int y, int d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	static ArrayDeque<Integer>[][] horseQ;
	static Horse[] horse;
	static int[][] map;
	static int[] dx = { 0, -1, 0, 1 };// 동북서남
	static int[] dy = { 1, 0, -1, 0 };
	static int N, K;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		horseQ = new ArrayDeque[N][N];
		horse = new Horse[K];
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				horseQ[i][j] = new ArrayDeque<>();
			}
		}

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;

			// 모듈러 연산을 위해 방향 정의 바꾸기
			if (d == 1) {
				d = 2;
			} else if (d == 2) {
				d = 1;
			}

			horseQ[x][y].offer(i);
			horse[i] = new Horse(x, y, d);
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int res = 0;

		loop: while (++res <= 1000) {
			for (int i = 0; i < K; i++) {
				if (!isMove(i)) {
					break loop;
				}
			}
		}

		return res > 1000 ? -1 : res;
	}

	/* 말을 이동했을 때 해당 칸에 말이 4개 이상인지 확인 */
	private static boolean isMove(int idx) {
		ArrayDeque<Integer> temp = new ArrayDeque<>();
		int x = horse[idx].x;
		int y = horse[idx].y;
		int d = horse[idx].d;

		// 해당 차례의 말을 찾을 때까지 가장 위에 있는 말부터 확인
		while (!horseQ[x][y].isEmpty()) {
			int num = horseQ[x][y].pollLast();
			temp.offer(num);

			if (num == idx) {
				break;
			}
		}

		int nx = x + dx[d];
		int ny = y + dy[d];

		// 이동한 좌표가 범위를 벗어나거나 파란색 칸인 경우
		if (!isCheck(nx, ny) || map[nx][ny] == 2) {
			horse[idx].d = (horse[idx].d + 2) % 4;
			nx = x + dx[horse[idx].d];
			ny = y + dy[horse[idx].d];

			// 방향을 변경했는데도 범위를 벗어나거나 파란색 칸인 경우
			if (!isCheck(nx, ny) || map[nx][ny] == 2) {
				while (!temp.isEmpty()) {
					horseQ[x][y].offer(temp.pollLast());
				}

				return true;
			}
		}

		// 빨간색 칸
		// -> 역순으로 이동
		if (map[nx][ny] == 1) {
			while (!temp.isEmpty()) {
				int num = temp.poll();
				horseQ[nx][ny].offer(num);
				horse[num].x = nx;
				horse[num].y = ny;
			}
		} else {// 흰색 칸 -> 원래 순서대로 이동
			while (!temp.isEmpty()) {
				int num = temp.pollLast();
				horseQ[nx][ny].offer(num);
				horse[num].x = nx;
				horse[num].y = ny;
			}
		}

		// 한 칸에 말이 4개 이상인 경우 발견
		if (horseQ[nx][ny].size() >= 4) {
			return false;
		}
		return true;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}