import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 28.
 *
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드3
 * 소요 시간: time over
 * 혼자 품: O
 * 풀이: 
 * 1. 상어 수만큼 상어 객체 값을 큐에 넣고 각 상어마다 방향에 따른 우선순위 방향값을 priorityDir 배열에 저장
 * 2. 상어들을 동시에 이동시켜야 되므로 큐에서 원래 좌표를 뺀 후 이동한 좌표값으로 변경하여 큐에 넣음, map에 상어 번호의 최솟값을 저장
 * 3. 상어들이 이동하기 전에 남아있는 냄새들의 시간을 1씩 빼줌, 0이 될 경우 냄새의 소유번호를 0으로 저장
 * 4. 큐 크기 만큼 반복문을 수행하면서 map의 값과 상어 번호를 비교하여 같을 경우에만 새로운 냄새를 남기고 큐에 넣음
 * 5. 위 과정을 1000까지 반복하여 그 전에 1번 상어만 남으면 t를 출력, 1000을 넘어가면 -1을 출력
 * 느낀 점: 
 * 냄새를 남기고 냄새가 남아있는 곳의 시간을 빼주는 과정을 순서대로 진행하지 못해서 1시간 반을 넘겨서 풀게 되었다.
 * 지문을 꼼꼼히 읽으면서 해석 또한 잘해야 된다고 느끼게 되었다.
 */
public class Boj_G3_19237_어른상어 {
	static class Shark {
		int num, x, y;

		public Shark(int num, int x, int y) {
			super();
			this.num = num;
			this.x = x;
			this.y = y;
		}
	}

	static ArrayDeque<Shark> shark = new ArrayDeque<>();
	static int[][] map, smellOwner, time;
	static int[][][] priorityDir;
	static int[] sharkDir;
	static int[] dx = { -1, 1, 0, 0 };// 상하좌우
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, k;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		smellOwner = new int[N][N];
		time = new int[N][N];
		priorityDir = new int[4][4][M + 1];
		sharkDir = new int[M + 1];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] != 0) {
					shark.offer(new Shark(map[i][j], i, j));
					smellOwner[i][j] = map[i][j];
					time[i][j] = k;
				} else {
					map[i][j] = 401;// 최솟값을 계산하기 위해 상어 번호 최댓값보다 큰 수 저장
				}
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= M; i++) {
			sharkDir[i] = Integer.parseInt(st.nextToken()) - 1;
		}

		for (int k = 1; k <= M; k++) {
			for (int i = 0; i < 4; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 4; j++) {
					priorityDir[i][j][k] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		for (int t = 1; t <= 1000; t++) {
			int size = shark.size();
			for (int i = 0; i < size; i++) {
				Shark s = shark.poll();
				int dir = sharkDir[s.num];
				moveShark(s, dir);
			}

			decreaseTime();
			leaveShark();

			if (shark.size() == 1) {// 상어가 한 마리(1번 상어)만 남게 된다면 걸린 시간을 리턴
				return t;
			}
		}
		return -1;// 1000초가 지나도 상어가 두 마리 이상이라면 -1 리턴
	}

	// 각 상어들을 이동시킴
	private static void moveShark(Shark s, int dir) {
		boolean isMove = false;// 상어가 이동할 수 있는 빈칸 여부를 판단

		for (int d = 0; d < 4; d++) {
			int nx = s.x + dx[priorityDir[dir][d][s.num]];
			int ny = s.y + dy[priorityDir[dir][d][s.num]];

			if (check(nx, ny) && smellOwner[nx][ny] == 0) {
				isMove = true;
				map[s.x][s.y] = 401;// 원래 위치에 상어 번호의 최댓값으로 변경
				map[nx][ny] = Math.min(map[nx][ny], s.num);// 최솟값 갱신
				sharkDir[s.num] = priorityDir[dir][d][s.num];
				shark.offer(new Shark(s.num, nx, ny));
				break;
			}
		}

		if (!isMove) {// 빈 칸이 없다면 자기 번호와 똑같은 냄새가 있는 칸으로 이동
			for (int d = 0; d < 4; d++) {
				int nx = s.x + dx[priorityDir[dir][d][s.num]];
				int ny = s.y + dy[priorityDir[dir][d][s.num]];

				if (check(nx, ny) && smellOwner[nx][ny] == s.num) {
					map[s.x][s.y] = 401;
					map[nx][ny] = Math.min(map[nx][ny], s.num);
					sharkDir[s.num] = priorityDir[dir][d][s.num];
					shark.offer(new Shark(s.num, nx, ny));
					return;
				}
			}
		}
	}

	// 각 상어의 냄새에 남아있는 시간을 1씩 감소
	private static void decreaseTime() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (time[i][j] > 0) {
					if (--time[i][j] == 0) {// 감소한 시간이 0인 칸은 번호를 0으로 변경
						smellOwner[i][j] = 0;
					}
				}
			}
		}
	}

	// map에 상어의 번호와 값이 다른 상어들을 큐에서 뺌
	private static void leaveShark() {
		int size = shark.size();
		for (int i = 0; i < size; i++) {
			Shark s = shark.poll();
			if (map[s.x][s.y] == s.num) {// 같은 번호일 경우에만 냄새를 남기고 큐에 다시 넣음
				smellOwner[s.x][s.y] = s.num;
				time[s.x][s.y] = k;
				shark.offer(s);
			}
		}
	}

	// 배열 범위 체크
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}