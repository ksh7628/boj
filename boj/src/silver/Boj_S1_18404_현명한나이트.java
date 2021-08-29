package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 12.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버1
 * 혼자 품: O
 * 풀이: 
 * 1. map 배열에 상대방 말과 거리를 동시에 저장하기 위해 -1로 초기화하고 상대방 말을 -2로 저장하고 pos 배열에 입력 순으로 상대방 말의 좌표를 저장한다.
 * 2. bfs를 통해 모든 상대방 말을 만날때까지 탐색하면서 거리를 갱신해 나간다.
 * 3. 입력받은 좌표의 순서대로 거리를 출력한다.
 * 느낀 점: 어렵지 않은 bfs 문제였는데 굳이 안해줘도 되는 Comparable을 통한 정렬을 해주어서 그 과정을 생략하였다.
 */
public class Boj_S1_18404_현명한나이트 {
	static class Pair {
		int x, y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static int[][] map;
	static Pair[] pos;
	static int[] dx = { -2, -2, -1, -1, 1, 1, 2, 2 };
	static int[] dy = { -1, 1, -2, 2, -2, 2, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N + 1][N + 1];
		pos = new Pair[M];

		for (int i = 1; i <= N; i++) {// 거리를 구하기 위해 -1로 초기화
			Arrays.fill(map[i], -1);
		}

		st = new StringTokenizer(br.readLine(), " ");
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			map[r][c] = -2;// 상대편 말을 -2로 정의
			pos[i] = new Pair(r, c);// 입력 순으로 상대방 말들의 좌표를 배열에 저장
		}

		bfs(x, y);
		br.close();
	}

	/* bfs를 통해 상대방 말까지의 최소 이동 수를 구하고 입력된 순서대로 이동 수를 출력 */
	private static void bfs(int x, int y) {
		StringBuilder sb = new StringBuilder();
		int dist = 0;
		ArrayDeque<Pair> q = new ArrayDeque<>();
		map[x][y] = 0;
		q.offer(new Pair(x, y));

		loop: while (!q.isEmpty()) {
			int size = q.size();
			dist++;

			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				if (M == 0) {// 모든 상대방 말을 만났다면 탐색 종료
					break loop;
				}

				for (int dir = 0; dir < 8; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					if (check(nx, ny) && map[nx][ny] < 0) {
						if (map[nx][ny] == -2) {// 상대방 말에 도달했다면 M 1 감소
							M--;
						}
						map[nx][ny] = dist;
						q.offer(new Pair(nx, ny));
					}
				}
			}
		}

		for (Pair p : pos) {
			sb.append(map[p.x][p.y]).append(" ");
		}
		System.out.print(sb);
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 1 <= x && x <= N && 1 <= y && y <= N;
	}
}