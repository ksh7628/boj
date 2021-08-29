package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 12.
 *
 * 분류: 그래프 이론, 그래프 탐색, 브루트포스 알고리즘, 너비 우선 탐색
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 값들을 map 배열에 저장해가면서 값이 2이면 virus에 저장, 값이 0이면 빈칸의 개수를 증가
 * 2. 빈칸의 개수를 다시 정의하고 비활성 바이러스의 개수 중 M개를 뽑는 조합을 생성
 * 3. 조합이 하나 만들어질 때마다 map배열을 복사한 tmap배열을 bfs를 통해 최단 거리(최소 시간)을 갱신해 나감
 * 느낀 점: 
 * 이전에 푼 연구소3 문제(https://www.acmicpc.net/problem/17142) 보다 조건이 적어서 쉽게 풀렸다.
 * 연구소3 문제만큼은 아니지만 그래도 항상 꼼꼼하게 요구사항을 읽는 습관을 기르는 것이 중요하다고 느낀다.
 */
public class Boj_G4_17141_연구소2 {
	static class Pair {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static List<Pair> virus = new ArrayList<>();
	static int[][] map, tmap;
	static int[] tmp;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, min, emptyCnt, INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		tmp = new int[M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					virus.add(new Pair(i, j));// 바이러스 좌표 저장
				} else if (map[i][j] == 0) {
					emptyCnt++;// 빈칸의 개수를 셈
				}
			}
		}

		min = INF;
		emptyCnt += virus.size() - M;// 빈칸의 개수 = 0의 개수 + 바이러스를 놓을 수 있는 칸의 개수 - M
		comb(0, 0);
		if (min == INF) {
			System.out.println(-1);
		} else {
			System.out.println(min);
		}
		br.close();
	}

	/* 조합 생성 */
	private static void comb(int start, int cnt) {
		if (cnt == M) {
			arrayCopy();
			bfs();
			return;
		}

		for (int i = start; i < virus.size(); i++) {
			tmp[cnt] = i;
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

	/* bfs를 통해 최소 시간을 구함 */
	private static void bfs() {
		int res = 0, cnt = 0;
		ArrayDeque<Pair> q = new ArrayDeque<>();
		for (int i = 0; i < M; i++) {
			tmap[virus.get(tmp[i]).x][virus.get(tmp[i]).y] = -1;// 방문 처리
			q.offer(virus.get(tmp[i]));// 조합을 통해 구한 바이러스를 놓는 좌표들을 큐에 넣음
		}

		while (!q.isEmpty()) {
			if (min <= res || cnt == emptyCnt) {// 구한 최소값보다 같거나 커지거나, 빈칸의 개수를 다 채웠다면 탐색 종료
				break;
			}

			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				for (int dir = 0; dir < 4; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					// 배열 범위안에 있고 벽이 아니고 방문을 아직 안했을때만 탐색
					if (check(nx, ny) && tmap[nx][ny] != 1 && tmap[nx][ny] != -1) {
						cnt++;
						tmap[nx][ny] = -1;
						q.offer(new Pair(nx, ny));
					}
				}
			}
			res++;
		}

		if (cnt == emptyCnt) {// 모든 빈칸을 채웠을 경우에만 최소값 갱신
			min = Math.min(min, res);
		}
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}