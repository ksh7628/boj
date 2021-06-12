import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 13.
 *
 * 분류: 그래프 이론, 그래프 탐색, 브루트포스 알고리즘, 너비 우선 탐색, 비트마스킹
 * 난이도: 골드2
 * 혼자 품: X
 * 풀이: 
 * 1. 입력 받은 값 중 시작위치 좌표를 변수에 저장하고 쓰레기를 리스트에 저장
 * 2. 시작위치를 trash의 가장 첫번째 인덱스에 넣고 flood fill을 이용하여 bfs를 통해 리스트의 두 좌표간 거리를 모두 저장
 * 3. 순열을 통해 최소 거리를 구함
 * 느낀 점: 
 * 한달 전에 풀때에는 순열을 사용해야된다는 문제인것을 뒤늦게 깨달아서 오늘 다시 도전했는데 시간초과가 발생하여 이유를 보니
 * 순열을 하나 만들때마다 다시 순열의 길이만큼 bfs를 계속 실행해서 bfs만 대략 O(N!*(N*H))나 되기 때문에 시간초과가 발생했던 것이다.
 * 혼자 힘으로는 도저히 못 풀거 같아서 다른 사람들의 코드를 보니 flood fill 이라는 알고리즘을 추가로 사용했는데 미리 두 점의 거리를 bfs로 계산한 후
 * 순열을 실행하기 때문에 O(N!+N^2)에 모든 탐색이 완료가 된다는 것을 알게 되었다. 플로이드 와샬 알고리즘과 동작이 거의 똑같다는 것 또한 알게 되었다.
 */
public class Boj_G2_4991_로봇청소기 {
	static class Pair {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static List<Pair> trash;
	static char[][] map;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static boolean[] used;
	static int H, W, S, si, sj, min;
	static boolean chk;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			if (W == 0 && H == 0) {
				break;
			}

			trash = new ArrayList<>();
			map = new char[H][W];

			for (int i = 0; i < H; i++) {
				String str = br.readLine();
				for (int j = 0; j < W; j++) {
					map[i][j] = str.charAt(j);
					if (map[i][j] == 'o') {// 시작 좌표 저장
						si = i;
						sj = j;
					} else if (map[i][j] == '*') {// 쓰레기를 리스트에 저장
						trash.add(new Pair(i, j));
					}
				}
			}

			process();
			sb.append(min).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 시작 */
	private static void process() {
		trash.add(0, new Pair(si, sj));// 시작점을 리스트 제일 앞에 넣음(위에서 구한 리스트의 최대 크기가 10이므로 O(N)이라도 미미함)
		S = trash.size();
		min = Integer.MAX_VALUE;
		chk = false;// 로봇 청소기가 갈 수 없는 곳이 생기면 true로 변경됨
		floodFill();

		if (!chk) {
			used = new boolean[S];
			perm(0, 0, 0);
		}
	}

	/* flood fill를 통해 좌표 간 거리들을 저장 */
	private static void floodFill() {
		dist = new int[S][S];
		for (int i = 0; i < S; i++) {
			for (int j = i + 1; j < S; j++) {
				int res = bfs(trash.get(i), trash.get(j));
				if (res == -1) {// 갈 수 없는 곳이 있다면 더 이상 반복문을 실행하지 않고 종료
					chk = true;
					min = -1;
					return;
				} else {
					dist[i][j] = dist[j][i] = res;// 양방향 저장
				}
			}
		}
	}

	/* 순열 생성 */
	private static void perm(int cnt, int idx, int distance) {
		if (min <= distance) {// 이전에 구한 최소값보다 거리값이 크거나 같다면 return(가지치기)
			return;
		}

		if (cnt == S - 1) {
			min = Math.min(min, distance);
			return;
		}

		for (int i = 1; i < S; i++) {
			if (used[i]) {
				continue;
			}

			used[i] = true;
			perm(cnt + 1, i, distance + dist[idx][i]);
			used[i] = false;
		}
	}

	/* bfs를 통해 start ~ end간 거리를 반환 */
	private static int bfs(Pair start, Pair end) {
		int res = 0;
		boolean[][] visit = new boolean[H][W];
		ArrayDeque<Pair> q = new ArrayDeque<>();
		visit[start.x][start.y] = true;
		q.offer(start);

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				if (p.x == end.x && p.y == end.y) {// 도착했다면 거리 return
					return res;
				}
				
				for (int dir = 0; dir < 4; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					if (check(nx, ny) || visit[nx][ny] || map[nx][ny] == 'x') {
						continue;
					}
					
					visit[nx][ny] = true;
					q.offer(new Pair(nx, ny));
				}
			}
			res++;
		}
		return -1;// end 좌표로 갈 수 없으면 -1 return
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= H || y < 0 || y >= W;
	}
}