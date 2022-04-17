package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 17.
 * 
 * 분류: 구현, 시뮬레이션, 백트래킹
 * 난이도: 골드1
 * 소요 시간: 1h 17m
 * 혼자 품: O
 * 풀이: 
 * 1. fishCnt에서 wFish로 값을 복사한다.(5번에서 wFish 값을 fishCnt로 누적시킴)
 * 2. tmpFish에 fishCnt값을 아래와 같이 누적시킨다.
 * 2-1. 이동 가능한 경우, 바뀐 방향과 이동하는 좌표에 현재 fishCnt값을 누적시킨다.
 * 2-2. 이동 불가능한 경우, 현재 좌표와 현재 방향을 인덱스로 해서 누적시킨다.
 * 2-3. 모두 이동했다면 tmpFish에서 fishCnt로 값을 복사한다.
 * 3. 4^3의 모든 경우로 상어를 이동시킨다. [상, 상, 상], ... [우, 우, 우] 순으로 순열을 만들기 때문에 사전 순서가 된다.
 * -> 따라서, 물고기를 더 많이 먹을 수 있는 경우에만 dirPriority를 갱신시킨다.
 * 3-1. 중복 순열을 구하기 전에 totalCnt에 현재 좌표의 방향 별 물고기 수의 합을 저장한다.(순열을 구할 때, 중복 계산을 방지)
 * 3-2. 최종 이동 방향이 정해졌다면 상어의 좌표를 갱신시키면서 해당 좌표에 있는 물고기를 모두 먹고 냄새를 남긴다.
 * -> smellTim에 저장할 때, 초기 값을 3으로 설정한다.(바로 다음 4번에서 1 감소하면 2가 된다.)
 * 4. smellTime을 순회하면서 1 이상의 값들은 1씩 감소시킨다.
 * 5. wFish 값을 fishCnt에 누적시킨다.
 * 느낀 점: 
 * 실제 시험까지 포함하면 4번째로 푼 문제인데 시간 복잡도를 드디어 줄일 수 있게 되었다.
 * 이전 까지는 큐 배열을 3개 또는 4개 사용하면서 물고기 한마리마다 연산을 모두 수행했었는데 굳이 큐 배열을 사용하지 않고
 * 개수를 저장하는 기본 배열로도 풀 수 있다는 것을 다른 사람의 코드를 참조하면서 알게 되었다.
 * 특히, 방향까지 인덱스로 나타내서 매번 4x4x8의 연산만 수행하면 되기 때문에 시간복잡도가 훨씬 감소한 것을 알 수 있게 되었다.
 */
public class Boj_G1_23290_마법사상어와복제 {
	static int[][][] fishCnt = new int[4][4][8];
	static int[][][] wFish = new int[4][4][8];
	static int[][] smellTime = new int[4][4];
	static int[][] totalCnt = new int[4][4];
	static int[] dirOrder, dirPriority = new int[3];
	static int[] dx = { 0, -1, -1, -1, 0, 1, 1, 1 };// 9시부터 시계방향
	static int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] sdx = { -1, 0, 1, 0 };// 상좌하우
	static int[] sdy = { 0, -1, 0, 1 };
	static int S, sx, sy, maxEating;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			fishCnt[x][y][d]++;
		}

		st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;

		System.out.println(solution());
		br.close();
	}

	/* 마법 연습 S번 수행 */
	private static int solution() {
		while (S-- > 0) {
			readyMagic();// 1.
			moveFish();// 2.
			moveShark();// 3.
			removeSmell();// 4.
			setMagic();// 5.
		}

		return getFishCount();
	}

	/* 
	 * 1. 상어가 모든 물고기에게 복제 마법을 시전한다.
	 * sol) fishCnt[행][열][방향] = 마릿 수
	 * fishCnt -> wFish로 복사
	 */
	private static void readyMagic() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.arraycopy(fishCnt[i][j], 0, wFish[i][j], 0, 8);
			}
		}
	}

	/*
	 * 2. 모든 물고기가 한 칸 이동한다.
	 * 상어가 있는 칸, 물고기의 냄새가 있는 칸, 격자의 범위를 벗어나는 칸으로는 이동할 수 없다.
	 * 각 물고기는 자신이 가지고 있는 이동 방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전시킨다.
	 * 만약, 이동할 수 있는 칸이 없으면 이동을 하지 않는다.
	 * 그 외의 경우에는 그 칸으로 이동을 한다.
	 * sol) 이미 이동한 물고기가 또 이동하면 안되므로 임시 배열 tmp에 물고기 개수를 누적시킨다.
	 * 모든 물고기의 이동이 끝났다면 tmp -> fishCnt로 복사
	 */
	private static void moveFish() {
		int[][][] tmp = new int[4][4][8];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 8; k++) {
					if (fishCnt[i][j][k] == 0) {
						continue;
					}

					boolean isMoved = false;
					for (int d = 0; d < 8; d++) {
						int nd = (k + 8 - d) % 8;
						int nx = i + dx[nd];
						int ny = j + dy[nd];

						if (isCheck(nx, ny) && !(sx == nx && sy == ny) && smellTime[nx][ny] == 0) {
							isMoved = true;
							tmp[nx][ny][nd] += fishCnt[i][j][k];
							break;
						}
					}

					if (!isMoved) {
						tmp[i][j][k] += fishCnt[i][j][k];
					}
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.arraycopy(tmp[i][j], 0, fishCnt[i][j], 0, 8);
			}
		}
	}

	/* 
	 * 3. 상어가 연속해서 3칸 이동한다.
	 * 상어는 현재 칸에서 상하좌우로 인접한 칸으로 이동할 수 있다.
	 * 연속해서 이동하는 칸 중에 격자의 범위를 벗어나는 칸이 있으면, 그 방법은 불가능한 이동 방법이다.
	 * 연속해서 이동하는 중에 상어가 물고기가 있는 같은 칸으로 이동하게 된다면, 그 칸에 있는 모든 물고기는 격자에서 제외되며, 제외되는 모든 물고기는 물고기 냄새를 남긴다.
	 * 가능한 이동 방법 중에서 제외되는 물고기의 수가 가장 많은 방법으로 이동하며, 그러한 방법이 여러가지인 경우 사전 순으로 가장 앞서는 방법을 이용한다.
	 * sol) 우선 순위를 구한 후 해당 방향으로 이동하면서 물고기가 있다면 모두 잡아먹고 해당 좌표에 냄새를 남김
	 */
	private static void moveShark() {
		maxEating = -1;
		dirOrder = new int[3];

		// 현재 좌표의 물고기 총합을 totalCnt에 저장
		// -> 순열을 만들 때마다 구하면 중복 연산 발생
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++) {
					sum += fishCnt[i][j][k];
				}
				totalCnt[i][j] = sum;
			}
		}

		brute(sx, sy, 0, 0);

		for (int i = 0; i < 3; i++) {
			sx += sdx[dirPriority[i]];
			sy += sdy[dirPriority[i]];
			boolean isDone = false;

			for (int k = 0; k < 8; k++) {
				if (fishCnt[sx][sy][k] > 0) {
					isDone = true;
					fishCnt[sx][sy][k] = 0;
				}
			}

			if (isDone) {
				smellTime[sx][sy] = 3;
			}
		}
	}

	/* 중복 순열 4^3 = 64에 대해 모두 검사하면서 우선순위 방향을 dirPriority 배열에 저장 */
	private static void brute(int x, int y, int idx, int cnt) {
		if (idx == 3) {
			if (cnt > maxEating) {
				maxEating = cnt;
				System.arraycopy(dirOrder, 0, dirPriority, 0, 3);
			}

			return;
		}

		for (int d = 0; d < 4; d++) {
			int nx = x + sdx[d];
			int ny = y + sdy[d];

			if (isCheck(nx, ny)) {
				dirOrder[idx] = d;
				int eatingCnt = totalCnt[nx][ny];
				totalCnt[nx][ny] = 0;
				brute(nx, ny, idx + 1, cnt + eatingCnt);
				totalCnt[nx][ny] = eatingCnt;
			}
		}
	}

	/* 
	 * 두 번 전 연습에서 생긴 물고기의 냄새가 격자에서 사라진다. 
	 * sol) 값이 0이 아니면 1씩 감소시킨다. 0(냄새가 없는 칸)
	 */
	private static void removeSmell() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (smellTime[i][j] > 0) {
					smellTime[i][j]--;
				}
			}
		}
	}

	/* 
	 * 5. 1에서 사용한 복제 마법이 완료된다.
	 * 모든 복제된 물고기는 1에서의 위치와 방향을 그대로 갖게 된다.
	 * sol) wFish에 저장된 값을 fishCnt에 누적시킨다.
	 */
	private static void setMagic() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 8; k++) {
					fishCnt[i][j][k] += wFish[i][j][k];
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < 4 && 0 <= y && y < 4;
	}

	/* 남은 물고기 수의 총합을 구한다. */
	private static int getFishCount() {
		int res = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 8; k++) {
					res += fishCnt[i][j][k];
				}
			}
		}
		return res;
	}
}