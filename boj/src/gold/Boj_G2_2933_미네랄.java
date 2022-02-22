package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 22.
 * 
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색, 시뮬레이션
 * 난이도: 골드2
 * 소요 시간: 1h 30m 이상(time over)
 * 혼자 품: X
 * 풀이: 
 * 1. 홀수와 짝수 차례를 구분한다. -> delta 변수 사용
 * 2. 막대기를 날려 미네랄을 만난다면 해당 미네랄을 파괴 후 맵을 살펴 방문 배열(group)을 하나씩 넘버링하면서 리스트에 넣어가며 클러스터를 찾는다.
 * 3. 클러스터마다 row의 최댓값을 찾으면서 R-1값이 아닌 경우를 찾는다. -> 공중에 떠 있는 경우(하나만 존재)
 * 4. 찾았다면 맵에서 해당 클러스터를 모두 '.'로 변경한다 -> group 번호로 판단
 * 5. row 크기를 증가시키면서 리스트를 순회하다가 땅 또는 미네랄에 닿는 경우 순회를 중단하고 해당 거리 만큼 클러스터를 이동시킨다.
 * 느낀 점: 
 * 개인적으로 굉장히 까다로웠던 문제였다. 처음에는 밑 방향은 고려하지 않았는데 그러면 클러스터 이동이 올바르지 않은
 * 예외 케이스('ㄹ'모양)가 존재하기 때문에 모든 방향을 고려해서 찾아야만 했다. 또한 클러스터 탐색을 할 때, 공중에 떠 있는
 * 클러스터는 문제에서 하나만 존재한다고 명시해서 방문 배열을 넘버링 해주는 방법도 알게 되었다.
 * 많이 어려웠던 문제인 만큼 나중에 다시 풀어봐야겠다.
 */
public class Boj_G2_2933_미네랄 {
	static ArrayList<int[]> cluster;
	static char[][] map;
	static int[][] group;
	static int[] height;
	static int[] dx = { 1, -1, 0, 0 };// 0: 바닥
	static int[] dy = { 0, 0, -1, 1 };
	static int R, C, N, minH;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new char[R][C];
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		N = Integer.parseInt(br.readLine());
		height = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			height[i] = R - Integer.parseInt(st.nextToken());
		}

		solution();
		br.close();
	}

	// 왼 - 오 - 왼 - 오 반복..
	// 미네랄 만나면 만난 미네랄 없앰
	// 클러스터 중 제일 밑 부분들이 모두 .라면(공중에 떠 있다면)
	// 한 행씩 내리면서 밑 부분들 중 하나라도 미네랄이나 땅 만나면 멈춤
	private static void solution() {
		for (int k = 0; k < N; k++) {
			int start = 0, delta = 0, i = height[k];

			if (k % 2 == 0) {
				start = 0;
				delta = 1;
			} else {
				start = C - 1;
				delta = -1;
			}

			for (int j = start; 0 <= j && j < C; j += delta) {
				if (map[i][j] == 'x') {
					group = new int[R][C];
					map[i][j] = '.';
					setCluster();
					break;
				}
			}
		}

		printResult();
	}

	/* 공중에 떠 있는 클러스터 세팅 */
	private static void setCluster() {
		int num = 1;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (group[i][j] == 0 && map[i][j] == 'x') {
					minH = -1;
					cluster = new ArrayList<>();
					dfs(i, j, num);

					if (minH != R - 1) {
						dropMineral(num);
						return;
					}

					num++;
				}
			}
		}
	}

	/* dfs로 공중에 떠 있는 클러스터 판단 */
	// 클러스터의 제일 밑 바닥 어떻게 체크? -> 밑에 땅이거나 미네랄이 없다면 더 이상 안내려감
	// 클러스터 보관 해야 함
	private static void dfs(int x, int y, int num) {
		group[x][y] = num;
		cluster.add(new int[] { x, y });
		minH = Math.max(minH, x);

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (!check(nx, ny)) {
				continue;
			}

			if (group[nx][ny] == 0 && map[nx][ny] == 'x') {
				dfs(nx, ny, num);
			}
		}
	}

	/* 떨어트렸을 때, 클러스터 바닥 또는 미네랄에 닿을 때의 높이를 구하고 클러스터를 구한 높이만큼 밑으로 이동 */
	private static void dropMineral(int num) {
		int h = 1;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (group[i][j] == num) {
					map[i][j] = '.';
				}
			}
		}

		loop: while (!cluster.isEmpty()) {
			for (int[] cur : cluster) {
				int nx = cur[0] + h;

				if (nx >= R || map[nx][cur[1]] == 'x') {
					h--;
					break loop;
				}
			}

			h++;
		}

		for (int[] cur : cluster) {
			map[cur[0] + h][cur[1]] = 'x';
		}
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 0 <= x && x < R && 0 <= y && y < C;
	}

	/* 결과 출력 */
	private static void printResult() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}

		System.out.print(sb);
	}
}