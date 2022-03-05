package platinum;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 그래프 이론, 자료 구조, 그래프 탐색, 너비 우선 탐색, 분리 집합
 * 난이도: 플래티넘5
 * 소요 시간: 0h 58m
 * 혼자 품: O
 * 풀이: 
 * 1. 백조가 있는 호수 뿐만 아니라 모든 호수에 대해 번호를 매기는 전처리 작업 수행
 * -> 시간을 절약하기 위해 dfs 방식으로 Flood-Fill 수행
 * -> 전처리 과정에서 얼음 경계 좌표를 큐에 따로 저장
 * 2. 매번 bfs를 수행하면서 이전 큐 크기만큼 호수를 확장시킨다.
 * 2-1. 얼음을 만났다면 전처리 과정처럼 큐에 넣어준다.
 * -> 이 때, 방문 처리를 확실하게 해야 홀수 겹의 얼음들을 처리할 수 있다.
 * 2-2. 호수를 만났다면 유니온-파인드 연산을 통해 같은 호수로 인식하는 번호를 매긴다.
 * 3. 백조 두 마리가 있는 호수의 번호가 서로 같아진다면 탐색을 종료한다.
 * 느낀 점: 
 * 작년에 운 좋게 비슷한 문제인 https://www.acmicpc.net/problem/14868를 풀고 도전을 했다가 한번 실패하고
 * 다시 풀게 된 문제이다. bfs는 최근 들어 다양한 문제를 풀면서 사고력이 어느정도 키워진 것 같아서 오늘 풀게 된 것 같다.
 * 이제는 다른 알고리즘 문제를 더 풀어봐야겠다.
 */
public class Boj_P5_3197_백조의호수 {
	static ArrayDeque<int[]> bound = new ArrayDeque<>();
	static int[][] swan = new int[2][2];
	static int[][] map;
	static boolean[][] visit;
	static int[] p;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int R, C;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new int[R][C];
		visit = new boolean[R][C];

		int idx = 0;
		// 문자들로 주어지는 맵을 정수형 맵으로 변환하자
		// -> 유니온파인드 연산이 수월해짐
		// -> X(벽): -1, 백조(L), 호수(.): 0
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				// 백조의 좌표를 저장
				if (str.charAt(j) == 'L') {
					swan[idx][0] = i;
					swan[idx++][1] = j;
				} else if (str.charAt(j) == 'X') {
					map[i][j] = -1;
				}
			}
		}

		System.out.println(solution());
		br.close();
	}

	// R, C가 상당히 커서 매번 맵 전체를 bfs하면 시간 or 메모리초과 발생함
	// 백조가 있는 호수를 전처리?
	// -> 모든 호수를 탐색하면서 호수 간 번호를 매김
	// -> 전처리 과정에서 호수를 둘러싸고 있는 테두리 얼음을 큐에 저장하자
	// -> 테두리를 점점 넓혀가면서 유니온 파인드 연산으로 백조가 만날 수 있는지 구하자
	private static int solution() {
		int num = 1;

		// 호수 마다 번호를 매긴다
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == 0) {
					floodFill(i, j, num++);
				}
			}
		}

		p = new int[num];
		for (int i = 1; i < num; i++) {
			p[i] = i;
		}

		int res = 0;
		// 두 백조가 만날 때까지 bfs 진행
		while (find(map[swan[0][0]][swan[0][1]]) != find(map[swan[1][0]][swan[1][1]])) {
			res++;
			bfs();
		}

		return res;
	}

	private static int find(int x) {
		if (p[x] == x) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static void union(int x, int y) {
		int a = find(x);
		int b = find(y);

		if (a != b) {
			p[b] = a;
		}
	}

	/* 
	 * dfs 방식으로 호수마다 번호를 매긴다
	 * -> 이 때, 최초로 만나는 테두리는 방문 체크 하고 다음 탐색을 위해 큐에 넣는다
	 */
	private static void floodFill(int x, int y, int num) {
		map[x][y] = num;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (!isCheck(nx, ny)) {
				continue;
			}

			if (map[nx][ny] == 0) {
				floodFill(nx, ny, num);
			} else if (map[nx][ny] == -1) {// 얼음을 발견하면 큐에 좌표와 현재 호수 번호를 넣자
				if (!visit[nx][ny]) {// 방문 처리
					visit[nx][ny] = true;
				}

				bound.offer(new int[] { nx, ny, num });
			}
		}
	}

	/* 
	 * 매번 큐에 들어있는 얼음을 녹이면서 테두리를 확장해 나간다
	 * -> 호수끼리 만나면 유니온 파인드 연산을 통해 하나의 호수로 합친다
	 */
	private static void bfs() {
		int size = bound.size();

		for (int i = 0; i < size; i++) {
			int[] cur = bound.poll();
			map[cur[0]][cur[1]] = cur[2];

			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];

				if (!isCheck(nx, ny)) {
					continue;
				}

				// 얼음을 발견했는데 방문 처리가 안됐다면 방문 처리 후 큐에 넣자
				if (map[nx][ny] == -1) {
					if (!visit[nx][ny]) {
						visit[nx][ny] = true;
						bound.offer(new int[] { nx, ny, cur[2] });
					}
				} else {// 다른 호수를 발견했다면 두 호수를 하나의 번호로 합친다
					union(cur[2], map[nx][ny]);
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < R && 0 <= y && y < C;
	}
}