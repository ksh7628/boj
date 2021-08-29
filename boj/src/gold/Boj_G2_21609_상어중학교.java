package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 6.
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색, 시뮬레이션
 * 난이도: 골드2
 * 혼자 품: O
 * 풀이: 블록 정보를 입력 받을 때 향후에 빈 공간도 따로 처리를 해줘야 하므로 빈공간을 0으로 정의하기 위해 무지개 블록을 -2로 따로 정의하였다.
 *      더 이상 블록 그룹이 존재하지 않을 때까지 오토 플레이를 무한 반복하면서 bfs를 통해 블록 그룹의 크기와 각 블록 그룹의 무지개 블록 개수를 찾고
 *      문제의 1번 조건의 우선순위에 따라 좌표값, 가장 큰 블록 그룹의 크기, 무지개 블록 개수를 갱신해주는 식으로 풀이하였다.
 *      블록 그룹을 찾을 때 무지개 블록은 계속해서 공유되기 때문에 방문 처리가 끝난 후 다시 무지개 블록에 해당하는 좌표의 방문을 해제를 꼭 해주어야 한다.
 * 느낀 점: 위에 풀이에 쓴것처럼 무지개 블록은 계속해서 공유가 되는데 이 조건을 생각안하고 풀어서 계속 틀리다가 나중에 결국 풀게 되었다.
 *        항상 시뮬레이션 유형의 문제는 요구사항을 꼼꼼하게 읽어야 된다는 것을 깨달은 좋은 문제였던것 같다.
 */
public class Boj_G2_21609_상어중학교 {
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, blockSize, rainbowCnt, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 0) {
					map[i][j] = -2;// 무지개 블록을 -2로 정의 (무지개 블록: -2, 검은색 블록: -1, 일반 블록: 1~5, 빈 공간: 0)
				}
			}
		}

		startAutoPlay();
		System.out.println(res);
		br.close();
	}

	/* 더 이상 블록 그룹을 짓지 못할때까지 오토 플레이 작동 */
	private static void startAutoPlay() {
		while (true) {
			visit = new boolean[N][N];
			blockSize = 0;
			rainbowCnt = 0;
			int row = -1, col = -1, cnt = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] > 0 && !visit[i][j]) {
						int size = searchBlockGroup(i, j);
						if (size < 2) {// 크기가 2보다 작으면 블록 그룹이 아니므로 continue
							continue;
						}
						if (blockSize < size) {// 새로 찾은 블록 그룹의 크기가 이전 블록 그룹 크기보다 크다면 값 갱신
							row = i;
							col = j;
							blockSize = size;
							cnt = rainbowCnt;
						} else if (blockSize == size && cnt <= rainbowCnt) {// 블록 그룹 크기가 같고 무지개 블록 개수가 많다면 값 갱신
							row = i;
							col = j;
							cnt = rainbowCnt;
						}
					}
				}
			}
			if (blockSize < 2) {// 블록 그룹이 없다면(어느 블록 그룹도 크기가 2보다 작으면) 오토 플레이 종료
				break;
			}
			res += deleteBlockGroup(row, col);
			gravity();
			rotation();
			gravity();
		}
	}

	/* bfs를 통해 블록 그룹의 크기를 반환 */
	private static int searchBlockGroup(int x, int y) {
		rainbowCnt = 0;
		int size = 1;
		ArrayList<int[]> rainbowList = new ArrayList<>();// 무지개 블록의 좌표를 저장하는 리스트
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny] || (map[nx][ny] != map[x][y] && map[nx][ny] != -2)) {
					continue;
				}
				if (map[nx][ny] == -2) {// 무지개 블록이 인접해 있다면 현재 블록의 무지개 블록수를 증가시키고 무지개 블록 좌표를 리스트에 저장
					rainbowCnt++;
					rainbowList.add(new int[] { nx, ny });
				}
				visit[nx][ny] = true;
				q.offer(new int[] { nx, ny });
				size++;
			}
		}
		for (int[] arr : rainbowList) {// 다음 탐색에도 무지개 블록을 공유할 수 있도록 방문처리 해제
			visit[arr[0]][arr[1]] = false;
		}
		return size;
	}

	/* 조건에 맞게 찾은 블록 그룹의 모든 블록을 제거 후 점수를 반환하는 메서드 */
	private static int deleteBlockGroup(int x, int y) {
		int size = 1;
		visit = new boolean[N][N];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		int sameBlock = map[x][y];
		map[x][y] = 0;
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny] || (map[nx][ny] != sameBlock && map[nx][ny] != -2)) {
					continue;
				}
				map[nx][ny] = 0;// 블록 제거
				visit[nx][ny] = true;
				q.offer(new int[] { nx, ny });
				size++;
			}
		}
		return size * size;
	}

	/* 중력 작용 */
	private static void gravity() {
		for (int i = N - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != 0 && map[i][j] != -1) {
					int row = i;
					while (row + 1 < N && map[row + 1][j] == 0) {
						map[row + 1][j] = map[row][j];
						map[row][j] = 0;
						row++;
					}
				}
			}
		}
	}

	/* map을 90도 반시계 방향으로 회전 */
	private static void rotation() {
		int[][] tmp = new int[N][N];
		for (int j = N - 1; j >= 0; j--) {
			for (int i = 0; i < N; i++) {
				tmp[N - 1 - j][i] = map[i][j];
			}
		}

		// 회전 완료 후 원본 배열로 복사
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = tmp[i][j];
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= N;
	}
}