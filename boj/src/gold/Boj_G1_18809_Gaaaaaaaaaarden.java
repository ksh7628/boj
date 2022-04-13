package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 13.
 * 
 * 분류: 구현, 그래프 이론, 브루트포스 알고리즘, 그래프 탐색, 너비 우선 탐색, 시뮬레이션
 * 난이도: 골드1
 * 소요 시간: 1h 30m 이상
 * 혼자 품: O
 * 풀이: 조합 + bfs로 풀이.
 * 1. list에 있는 배양액을 뿌릴 수 있는 좌표들 중 초록 배양액과 빨간 배양액의 합 만큼 조합을 뽑는다.
 * 2. 해당 좌표를 방문 처리 후 각 큐에 넣고 bfs를 수행한다.
 * 3. 같은 시간동안 먼저 초록 배양액을 퍼트리고 빨간 배양액을 퍼트린다.
 * 3-1. 빨간 배양액을 퍼트릴 때, 같은 시간대에 초록 배양액이 퍼진 좌표라면 꽃을 피울 수 있므으로 개수를 카운팅하고 -1로 저장한다.
 * 3-2. 다음 차례에 초록 배양액 큐에 있는 좌표를 꺼냈을 때, 이미 꽃이 핀 곳이라면 다음 탐색으로 넘어간다.
 * 4. 조합을 뽑아가면서 매번 최댓값을 갱신한다. 
 * 느낀 점: 2개의 큐를 사용하는 문제라서 각 큐마다 조건을 잘 따져주지 않으면 틀리기 쉽다는 것을 알게 되었다.
 */
public class Boj_G1_18809_Gaaaaaaaaaarden {
	@SuppressWarnings("unchecked")
	static ArrayDeque<int[]>[] q = new ArrayDeque[2];
	// 배양액을 뿌릴 수 있는 좌표
	static List<int[]> list = new ArrayList<>();
	// 해당 좌표에 도달한 시간을 저장, [배양액(초록: 0, 빨강: 1)][행][열]
	static int[][][] time;
	static int[][] map;
	static int[] color, idx;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, G, R, S, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		G = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					list.add(new int[] { i, j });
				}
			}
		}

		S = R + G;
		color = new int[list.size()];
		idx = new int[S];
		comb(0, 0, 0, 0);

		System.out.println(res);
		br.close();
	}

	/* 배양액을 뿌릴 수 있는 땅 중 G + R 개를 고르는 조합을 뽑고 그 중 순서를 고려해서 bfs를 수행한 후 최댓값을 갱신 */
	private static void comb(int start, int cnt, int greenCnt, int redCnt) {
		if (cnt == S) {
			if (greenCnt == G && redCnt == R) {
				time = new int[2][N][M];
				q[0] = new ArrayDeque<>();
				q[1] = new ArrayDeque<>();

				// 각 색깔에 맞는 배양액을 큐에 넣고 방문 처리
				for (int i = 0; i < S; i++) {
					int ci = color[idx[i]] - 1;
					int[] pos = list.get(idx[i]);
					time[0][pos[0]][pos[1]] = 1;
					time[1][pos[0]][pos[1]] = 1;
					q[ci].offer(pos);
				}

				res = Math.max(res, bfs());
			}
			return;
		}

		for (int i = start; i < list.size(); i++) {
			if (color[i] == 0) {
				idx[cnt] = i;
				// 초록색 배양액: 1, 빨간색 배양액: 2
				color[i] = 1;
				comb(i + 1, cnt + 1, greenCnt + 1, redCnt);
				color[i] = 2;
				comb(i + 1, cnt + 1, greenCnt, redCnt + 1);
				color[i] = 0;
			}
		}
	}

	/* bfs를 통해 피울 수 있는 꽃의 개수를 구한다 */
	private static int bfs() {
		int t = 1, cnt = 0;

		// 둘 중 하나라도 비면 꽃이 더 생길일이 없으므로 종료
		while (!q[0].isEmpty() && !q[1].isEmpty()) {
			t++;
			int gs = q[0].size();
			int rs = q[1].size();

			// 초록색 배양액을 퍼트린다
			for (int i = 0; i < gs; i++) {
				int[] cur = q[0].poll();
				// 이미 꽃을 피운 좌표라면 탐색하지 않음
				if (time[0][cur[0]][cur[1]] == -1) {
					continue;
				}

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					// 배열 범위 체크, 초록색 배양액이 안 닿은 곳, 빨간색 배양액이 안 닿은 곳, 호수가 아닌 곳
					if (isCheck(nx, ny) && time[0][nx][ny] == 0 && time[1][nx][ny] == 0 && map[nx][ny] > 0) {
						time[0][nx][ny] = t;
						q[0].offer(new int[] { nx, ny });
					}
				}
			}

			// 빨간색 배양액을 퍼트린다
			for (int i = 0; i < rs; i++) {
				int[] cur = q[1].poll();

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && map[nx][ny] > 0) {
						// 초록 배양액이 같은 시간에 퍼진 곳이라면 꽃을 피울 수 있다
						if (time[0][nx][ny] == t) {
							cnt++;
							time[0][nx][ny] = -1;
							time[1][nx][ny] = -1;

							// 아직 빨강 배양액이 방문 안한 곳
						} else if (time[1][nx][ny] == 0) {
							time[1][nx][ny] = t;
							q[1].offer(new int[] { nx, ny });
						}
					}
				}
			}
		}

		return cnt;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}