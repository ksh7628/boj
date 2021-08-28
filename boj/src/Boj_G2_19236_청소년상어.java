import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 27.
 *
 * 분류: 구현, 시뮬레이션, 백트래킹
 * 난이도: 골드2
 * 소요 시간: time over
 * 혼자 품: X
 * 풀이: 
 * 1. 인덱스를 번호로 가지는 Fish 배열에 각 물고기 정보들을 저장
 * 2. 원본 배열의 값들을 복사 배열에 옮김
 * 3. 1번 물고기부터 16번 물고기까지 살아있는 물고기들만 이동시킨다.
 * 4. 상어가 먹을 수 있는 물고기가 있다면 재귀를 통해 그 물고기의 위치와 방향, 현재까지 먹은 물고기 번호의 합 + 지금 먹을 물고기 번호를 인자로 전달
 * 5. 갱신된 최댓값을 출력
 * 느낀 점: 
 * 자료구조 선택을 굉장히 어렵게 생각해서 결국 다른 사람의 풀이를 보고 풀게 되었다. 특히 복사 배열에 객체 배열을 그대로 복사해줬는데 이 경우 주소값이 같아져서
 * 원본 배열 값이 바뀌면 복사 배열 값도 바뀌게 된다는 점을 새로 알게 되었는데 객체를 재생성하여 생성자의 인자에 원래 객체의 멤버 변수값들을 전달해주어야 한다는 것을 알게 되었다.
 * 골드 3이상의 시뮬레이션 문제는 효율적인 자료구조 선택 및 이런 류의 문제는 복사에 대한 개념을 확실히 알아야 풀 수 있다는 것을 알게 되었다.
 */
public class Boj_G2_19236_청소년상어 {
	static class Fish {
		int x, y, num, dir;
		boolean alive;

		public Fish(int x, int y, int num, int dir, boolean alive) {
			super();
			this.x = x;
			this.y = y;
			this.num = num;
			this.dir = dir;
			this.alive = alive;
		}
	}

	static Fish[] fish;
	static int[][] map;
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 }; // 12시부터 반시계로 8방향
	static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };
	static int max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		fish = new Fish[17];
		map = new int[4][4];

		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 4; j++) {
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken()) - 1;
				fish[num] = new Fish(i, j, num, dir, true);
				map[i][j] = num;
			}
		}

		int sx = 0, sy = 0, sd = fish[map[0][0]].dir, eatFish = map[0][0];
		fish[map[0][0]].alive = false;
		map[0][0] = -1;// 상어 위치 -1

		dfs(eatFish, sx, sy, sd);
		System.out.println(max);
		br.close();
	}

	// dfs를 통해 최대한 많이 먹을 수 있는 물고기 번호의 합을 구함
	private static void dfs(int sum, int sx, int sy, int sd) {
		max = Math.max(max, sum);

		// dfs를 통해 배열들의 값이 바뀌므로 복사 배열을 생성하고 값을 저장
		int[][] tmap = new int[4][4];
		for (int i = 0; i < 4; i++) {
			System.arraycopy(map[i], 0, tmap[i], 0, 4);
		}

		Fish[] tfish = new Fish[17];
		for (int i = 1; i <= 16; i++) {// 객체배열을 곧바로 복사하면 값을 수정할 때 원본 배열과 복사 배열 값이 모두 바뀐다!! -> 객체 새로 생성
			tfish[i] = new Fish(fish[i].x, fish[i].y, fish[i].num, fish[i].dir, fish[i].alive);
		}

		for (int i = 1; i <= 16; i++) {
			if (!tfish[i].alive) {// 이미 먹은 물고기의 번호는 패스
				continue;
			}

			for (int j = 0; j < 8; j++) {// 현재 물고기의 방향으로부터 반시계 방향으로 탐색
				int nd = (fish[i].dir + j) % 8;
				int nx = fish[i].x + dx[nd];
				int ny = fish[i].y + dy[nd];

				if (check(nx, ny) && map[nx][ny] != -1) {
					fish[i].dir = nd;
					if (map[nx][ny] == 0) {// 빈 칸이라면 해당 물고기가 있는 칸을 빈칸으로 수정하고 물고기 정보를 빈칸에 옮김
						map[fish[i].x][fish[i].y] = 0;
						fish[i].x = nx;
						fish[i].y = ny;
						map[nx][ny] = i;
					} else {// 빈 칸이 아니라면 두 물고기 정보를 서로 변경
						int swapNum = fish[map[nx][ny]].num;
						fish[swapNum].x = fish[i].x;
						fish[swapNum].y = fish[i].y;
						map[fish[swapNum].x][fish[swapNum].y] = swapNum;

						fish[i].x = nx;
						fish[i].y = ny;
						map[nx][ny] = i;
					}
					break;
				}
			}
		}

		// 맵 크기가 4x4 이므로 상어는 최대 3칸까지 이동가능
		for (int k = 1; k <= 3; k++) {
			int nx = sx + dx[sd] * k;
			int ny = sy + dy[sd] * k;

			if (check(nx, ny) && map[nx][ny] != 0) {// 물고기를 먹을 수 있다면
				int eatFish = map[nx][ny];
				int nd = fish[eatFish].dir;
				map[sx][sy] = 0;
				map[nx][ny] = -1;
				fish[eatFish].alive = false;

				dfs(sum + eatFish, nx, ny, nd);

				map[sx][sy] = -1;
				map[nx][ny] = eatFish;
				fish[eatFish].alive = true;
			}
		}

		// 원본 배열을 다시 유지
		for (int i = 0; i < 4; i++) {
			System.arraycopy(tmap[i], 0, map[i], 0, 4);
		}

		for (int i = 1; i <= 16; i++) {
			fish[i] = new Fish(tfish[i].x, tfish[i].y, tfish[i].num, tfish[i].dir, tfish[i].alive);
		}
	}

	private static boolean check(int x, int y) {
		return 0 <= x && x < 4 && 0 <= y && y < 4;
	}
}