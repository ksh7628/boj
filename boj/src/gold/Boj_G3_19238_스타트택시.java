package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 14.
 * 
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 시뮬레이션
 * 난이도: 골드3
 * 소요 시간: 1h 12m
 * 혼자 품: O
 * 풀이: 
 * 1. 한 승객을 태워서 내려줄 때의 bfs를 따로 사용
 * 2. 승객의 출발점은 다 다르지만 목적지는 승객 간 겹칠 수 있다
 * 2-1. 출발점은 인덱스를 좌표, 값을 승객 번호로하는 배열로 저장
 * 2-2. 목적지는 인덱스를 승객 번호, 값을 좌표가 저장된 배열로 정의
 * 3. 승객을 태울 때 최단거리 -> 행 오름차순 -> 열 오름차순 으로 해서 태운다
 * 4. 승객을 내릴 때만 태운 좌표에서 내리는 좌표까지의 거리 * 2의 연료를 채운다
 * 5. 승객을 태우러 갈때 + 목적지에 내려줄 때 둘 다 연료가 이전에 소진되면 -1
 * 느낀 점: 
 * 다시 풀었는데도 코너 케이스를 고려 못해서 3번만에 맞추게 되었다.
 * 처음에는 승객 간 목적지가 같을 수도 있다는 점을 고려 못해서 틀렸고 두번째는 승객을 태우고 나서 start배열 초기화를 안해줘서 틀리게 되었다.
 * 이 문제는 골드3라고는 하지만 동등한 문제들에 비해 코너 케이스를 잘 생각해야 되는 문제라고 생각한다.
 */
public class Boj_G3_19238_스타트택시 {
	static List<int[]> endList = new ArrayList<>();
	static int[][] map, start;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, sx, sy, custNum, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		res = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		start = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;

		endList.add(new int[] { -1, -1 });
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int sr = Integer.parseInt(st.nextToken()) - 1;
			int sc = Integer.parseInt(st.nextToken()) - 1;
			int er = Integer.parseInt(st.nextToken()) - 1;
			int ec = Integer.parseInt(st.nextToken()) - 1;

			// 출발점은 겹칠 수 없다
			start[sr][sc] = i;
			// 도착점은 겹칠 수 있다
			endList.add(new int[] { er, ec });
		}

		System.out.println(solution());
		br.close();
	}

	// 두번 체크를 해야한다
	// -> 1. 손님 태울 때, 2. 손님 내려줄 때 
	// bfs메소드 하나쓰고 함수 인자로 태우러 갈때랑 내려주러 갈때 구분?
	// -> 코드가 메소드 2개만큼 길어지고 실수 가능성 높다, 2개로 분리하자
	private static int solution() {
		for (int i = 0; i < M; i++) {
			if (!startBfs()) {// 손님 태우러 감
				return -1;
			}
			if (!endBfs()) {// 손님 내려주러 감
				return -1;
			}
		}

		return res;
	}

	/*
	 * 시작점은 다 다름
	 * 가장 짧은 경로의 승객 선택 -> bfs
	 * 여러명일 경우, 행 최소 -> 열 최소 순으로 택시에 승객 없을 때에는 연료 충전 X
	 */
	private static boolean startBfs() {
		// 시작점에서 승객 발견
		if (start[sx][sy] > 0) {
			custNum = start[sx][sy];
			start[sx][sy] = 0;
			return true;
		}

		visit = new boolean[N][N];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[sx][sy] = true;
		q.offer(new int[] { sx, sy });

		int row = N, col = N;
		custNum = 0;
		boolean isFind = false;

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();

				// 승객 발견
				// -> 해당 depth에서 승객을 찾았다고 true 값으로 변경
				// -> 거리 비교 안해도 됨
				if (start[cur[0]][cur[1]] > 0) {
					isFind = true;

					// 행 비교
					if (cur[0] < row) {
						custNum = start[cur[0]][cur[1]];
						sx = row = cur[0];
						sy = col = cur[1];

						// 열 비교
					} else if (cur[0] == row && cur[1] < col) {
						custNum = start[cur[0]][cur[1]];
						sy = col = cur[1];
					}
				}

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] == 0) {
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					}
				}
			}

			if (isFind) {
				start[sx][sy] = 0;
				break;
			}

			// 연료가 바닥나면 불가능
			if (--res < 0) {
				return false;
			}
		}

		if (isFind) {
			return true;
		}
		return false;
	}

	/*
	 * 한 승객의 목적지는 정해져 있다
	 * -> 그러나 여러 승객의 목적지가 같을 수 있다
	 * -> end[x][y] = 승객번호 (X)
	 * *** list.get(승객번호) = {x, y}로 변경 ***
	 * 택시에 승객 있고 목적지까지 도착가능하면 그 거리만큼 소비한 연료*2 충전
	 * 실시간으로 연료량 감소시키면서 거리 변수 증가시키니까 판별이 잘 안됨
	 * -> 거리 변수 대신 큐에 넣을 때 크기 3의 배열 {x, y, 거리}로 넣자
	 */
	private static boolean endBfs() {
		visit = new boolean[N][N];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[sx][sy] = true;
		q.offer(new int[] { sx, sy });
		int dist = 0;

		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();

				// 태운 승객의 목적지 도착
				if (cur[0] == endList.get(custNum)[0] && cur[1] == endList.get(custNum)[1]) {
					res += dist * 2;
					sx = cur[0];
					sy = cur[1];
					return true;
				}

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] == 0) {
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					}
				}
			}

			// 연료가 바닥나면 불가능
			if (--res < 0) {
				return false;
			}
			
			dist++;
		}

		return false;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}