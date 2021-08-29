package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 12.
 *
 * 분류: 그래프 이론, 그래프 탐색, 브루트포스 알고리즘, 너비 우선 탐색
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 값들을 map 배열에 저장해가면서 값이 2이면 virusList에 저장, 값이 0이면 빈칸의 개수를 증가
 * 2. 비활성 바이러스의 개수 중 M개를 뽑는 조합을 생성
 * 3. 조합이 하나 만들어질 때마다 map배열을 복사한 tmap배열을 bfs를 통해 최단 거리(최소 시간)을 갱신해 나감
 * 느낀 점: 
 * 처음에 빈칸을 따로 카운트해주지 않고 풀이를 시도하니 많이 헤매게 된 문제였다. 그리고 처음에 빈칸을 세서 빈칸이 하나도 없으면
 * 굳이 조합을 만들 필요가 없이 최소 시간은 항상 0이므로 탐색을 안하고 답을 바로 출력하는 것이 좋다.
 */
public class Boj_G4_17142_연구소3 {
	static class Pair {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static List<Pair> virusList = new ArrayList<>();
	static int[][] map, tmap, virus;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, emptyCnt, min = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		virus = new int[M][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					virusList.add(new Pair(i, j));// 바이러스를 생성할 수 있는 좌표 저장
				} else if (map[i][j] == 0) {
					emptyCnt++;// 빈칸의 개수를 셈
				}
			}
		}

		if (emptyCnt != 0) {// 빈 칸이 있다면 조합을 통해 모든 경우의 수 탐색
			comb(0, 0);
			if (min == Integer.MAX_VALUE) {
				min = -1;
			}
		} else {// 빈 칸이 없다면 조합 생성 안함
			min = 0;
		}
		System.out.println(min);
		br.close();
	}

	/* 조합 생성 */
	private static void comb(int start, int cnt) {
		if (cnt == M) {
			arrayCopy();
			int time = bfs();
			min = Math.min(min, time);
			return;
		}

		for (int i = start; i < virusList.size(); i++) {
			virus[cnt][0] = virusList.get(i).x;
			virus[cnt][1] = virusList.get(i).y;
			comb(i + 1, cnt + 1);
		}

	}

	/* 배열 복사 */
	private static void arrayCopy() {
		tmap = new int[N][N];
		for (int i = 0; i < N; i++) {
			System.arraycopy(map[i], 0, tmap[i], 0, N);
		}
	}

	private static int bfs() {
		int res = 0, cnt = 0;
		ArrayDeque<Pair> q = new ArrayDeque<>();
		for (int i = 0; i < M; i++) {// 조합으로 생성한 M개의 좌표를 큐에 담음
			tmap[virus[i][0]][virus[i][1]] = -1;// 방문 처리
			q.offer(new Pair(virus[i][0], virus[i][1]));
		}

		while (!q.isEmpty()) {
			if (min <= res || cnt == emptyCnt) {// 이미 구한 최소값보다 같거나 크다면 또는 빈칸을 모두 탐색했다면 탐색 종료
				break;
			}

			int size = q.size();
			for (int i = 0; i < size; i++) {// 큐의 크기만큼 탐색 -> 거리가 증가되면 시간도 증가
				Pair p = q.poll();
				for (int dir = 0; dir < 4; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					if (isCheck(nx, ny)) {
						if (tmap[nx][ny] == 1 || tmap[nx][ny] == -1) {
							continue;
						}
						if (tmap[nx][ny] == 0) {// 빈칸일 때만 개수를 셈
							cnt++;
						}
						tmap[nx][ny] = -1;
						q.offer(new Pair(nx, ny));
					}
				}
			}
			res++;
		}

		if (cnt != emptyCnt) {// 탐색이 완료되었는데 개수가 다르면 다 못퍼트림 -> 최소값 갱신 안함
			return Integer.MAX_VALUE;
		}
		return res;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}