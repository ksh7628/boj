package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 3.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드2
 * 소요 시간: 1h 19m
 * 혼자 품: O
 * 풀이: 덱을 사용해서 풀이
 * 느낀 점: 
 * 처음에 어떤 자료구조를 선택해야 할지 고민을 많이 하다가 덱의 특성을 이용해서 풀게 되었다.
 * 객체 멤버 변수 값을 바로 수정하면 얕은 복사가 발생해서 값이 모두 갱신된다는 점을 다시 상기시키게 된 문제였다.
 * https://www.acmicpc.net/problem/17837 문제보다는 좀 더 쉬운 문제같다.
 */
public class Boj_G2_17780_새로운게임 {
	static class Piece {
		int x, y, d;

		public Piece(int x, int y, int d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	static ArrayDeque<Integer>[][] dq;
	static Piece[] piece;
	static int[][] map;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { 1, 0, -1, 0 };
	static int N, K;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		dq = new ArrayDeque[N][N];
		map = new int[N][N];
		piece = new Piece[K];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				dq[i][j] = new ArrayDeque<>();
			}
		}

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;

			// 코드에서 정의한 방향 순서는 동남서북
			// 문제에서 정의한 방향 순서는 동서북남
			// -> 코드 정의 방향으로 변경
			if (d == 1) {
				d = 2;
			} else if (d == 2) {
				d = 1;
			}

			piece[i] = new Piece(r, c, d);
			dq[r][c].offer(i);
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int T = 0;

		loop: while (true) {
			if (++T > 1000) {
				return -1;
			}

			for (int i = 0; i < K; i++) {
				if (!move(i)) {
					break loop;
				}
			}
		}

		return T;
	}

	/* 말을 다음 칸으로 움직였을 때 개수가 4개 이상이 되는지 확인한다 */
	private static boolean move(int idx) {
		Piece p = piece[idx];
		// 객체 멤버 변수를 그대로 사용하면 얕은 복사가 발생해서 전부 값이 바뀜
		// -> 방지하기 위해 좌표 지역 변수를 따로 선언해서 값만 복사
		int x = p.x, y = p.y;
		// 현재 바닥에 있는 말이 아니면 움직일 수 없음
		if (dq[x][y].peek() != idx) {
			return true;
		}

		int nx = x + dx[p.d];
		int ny = y + dy[p.d];

		// 파란색이면 방향을 반대로 바꾼다
		if (!check(nx, ny) || map[nx][ny] == 2) {
			p.d = (p.d + 2) % 4;
			nx += dx[p.d] * 2;
			ny += dy[p.d] * 2;

			// 반대로 바꿨는데도 파란색이라면 움직일 수 없음
			// 방향은 바로 위에서 이미 갱신함
			if (!check(nx, ny) || map[nx][ny] == 2) {
				return true;
			}
		}

		// 빨간색
		// -> 반대로 쌓아준다
		if (map[nx][ny] == 1) {
			while (!dq[x][y].isEmpty()) {
				int num = dq[x][y].pollLast();
				dq[nx][ny].offer(num);
				piece[num].x = nx;
				piece[num].y = ny;
			}
			// 파란색
			// 정방향으로 쌓아준다
		} else {
			while (!dq[x][y].isEmpty()) {
				int num = dq[x][y].poll();
				dq[nx][ny].offer(num);
				piece[num].x = nx;
				piece[num].y = ny;
			}
		}

		// 이동한 칸의 말의 개수가 4개 이상이면 게임 종료
		if (dq[nx][ny].size() >= 4) {
			return false;
		}
		return true;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}