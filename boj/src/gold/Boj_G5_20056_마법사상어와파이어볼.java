package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 25.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: 0h 37m
 * 혼자 품: O
 * 풀이: 좌표, 질량, 속력, 방향을 변수로 가지는 파이어볼 객체 큐, 좌표를 인덱스로, 질량, 속력, 방향을 가지는 파이어볼 배열 큐 사용
 * 1. 입력 값들을 객체로 만들어서 저장된 큐에서 배열 큐로 이동
 * -> 이동 시 모듈러 연산을 통해 배열 끝을 연결
 * 2. 2개 이상 저장된 배열 큐에서 파이어볼의 질량 총합, 속력 총합을 구하고 방향별로 홀수인지 짝수인지 판단하는 isOdd, isEven 변수 갱신
 * -> 둘다 true이면 홀, 짝이 섞임 / 하나만 true이면 모두 홀수이거나 모두 짝수
 * -> 1개의 경우, 바로 큐에 넣음
 * 3. 4개의 파이어볼로 문제에서 제시한 질량합 / 5, 속력합의 평균으로 큐에 넣는다.
 * -> 질량이 0인 경우 소멸시킴
 * 4. K번 이동과 합체 수행 후 남은 파이어볼의 총 질량을 구함
 * 느낀 점: 
 * 여러 번 풀어본 문제이지만 이번에도 질량이 0인 경우 소멸 조건을 한번 틀리고 알게 되었다.
 * 큐배열 2개로 풀 경우, 메모리가 가장 적게 필요하지만 시간이 좀 더 걸렸다.(memory best)
 * 큐배열 1개, 큐 1개로 풀 경우, 메모리가 좀 더 필요하지만 시간이 가장 적게 걸렸다.(time best)
 * 큐배열 1개, 리스트 배열 1개로 풀 경우, 매번 리스트 배열 모두를 초기화해서 메모리가 가장 많이 필요하고 그만큼 시간도 가장 많이 필요하다.(worst)
 */
public class Boj_G5_20056_마법사상어와파이어볼 {
	static class Fireball {
		int x, y, m, s, d;

		public Fireball(int x, int y, int m, int s, int d) {
			super();
			this.x = x;
			this.y = y;
			this.m = m;
			this.s = s;
			this.d = d;
		}

		public Fireball(int m, int s, int d) {
			super();
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}

	static ArrayDeque<Fireball>[][] fbMap;
	static ArrayDeque<Fireball> fbQueue = new ArrayDeque<>();
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };// 12시부터 시계방향으로 총 8방향
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int N, K;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		fbMap = new ArrayDeque[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				fbMap[i][j] = new ArrayDeque<>();
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			fbQueue.offer(new Fireball(x, y, m, s, d));
		}

		System.out.println(solution());
		br.close();
	}

	/* 한 좌표에 여러개의 파이어볼을 저장해야 한다 -> ArrayList or Queue */
	private static int solution() {
		while (--K >= 0) {
			moveFireball();// 1. 모든 파이어볼이 자신의 방향 d로 속력 s칸 만큼 이동한다.
			combineFireball();// 2. 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 다음과 같은 일이 일어난다.
		}

		return getAmount();
	}

	/*
	 * 이동하는 중에는 같은 칸에 여러 개의 파이어볼이 있을 수도 있다.
	 * -> 파이어볼마다 이동한 좌표의 fbList에 질량, 속력, 방향을 저장한다.
	 * 
	 * 2차원 객체 큐 2개 -> 24468KB / 476ms
	 * ★ 2차원 객체 큐 1개, 객체 큐 1개 -> 34224KB / 412ms
	 * 2차원 객체 리스트 1개, 객체 큐 1개 -> 114360KB / 556ms
	 */
	private static void moveFireball() {
		while (!fbQueue.isEmpty()) {
			Fireball fb = fbQueue.poll();
			int nx = (fb.x + (dx[fb.d] + N) * fb.s) % N;
			int ny = (fb.y + (dy[fb.d] + N) * fb.s) % N;
			fbMap[nx][ny].offer(new Fireball(fb.m, fb.s, fb.d));
		}
	}

	/*
	 * 2개 이상의 파이어볼
	 * -> 같은 칸에 있는 파이어볼은 모두 하나로 합쳐진다.
	 * -> 파이어볼은 4개의 파이어볼로 나누어진다.
	 * -> 나누어진 파이어볼의 질량, 속력, 방향은 다음과 같다.
	 * -> 질량은 ⌊(합쳐진 파이어볼 질량의 합)/5⌋이다.
	 * -> 속력은 ⌊(합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)⌋이다.
	 * -> 합쳐지는 파이어볼의 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0, 2, 4, 6이 되고, 그렇지 않으면 1, 3, 5, 7이 된다.
	 * 1개의 파이어볼
	 * -> 그대로 큐에 넣는다.
	 */
	private static void combineFireball() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (fbMap[i][j].isEmpty()) {
					continue;
				}
				int size = fbMap[i][j].size();
				
				if (size == 1) {
					Fireball fb = fbMap[i][j].poll();
					fbQueue.offer(new Fireball(i, j, fb.m, fb.s, fb.d));
					continue;
				}
				
				int mSum = 0, sSum = 0;
				boolean isOdd = false, isEven = false;
				
				while (!fbMap[i][j].isEmpty()) {
					Fireball fb = fbMap[i][j].poll();
					mSum += fb.m;
					sSum += fb.s;
					
					if (fb.d % 2 == 0) {
						isEven = true;
					} else {
						isOdd = true;
					}
				}
				
				int mAvg = mSum / 5, sAvg = sSum / size;
				// 질량이 0인 파이어볼은 소멸
				if (mAvg == 0) {
					continue;
				}
				
				int dir = isEven && isOdd ? 1 : 0;
				for (int d = dir; d < 8; d += 2) {
					fbQueue.offer(new Fireball(i, j, mAvg, sAvg, d));
				}
			}
		}
	}

	/* 남아있는 파이어볼 질량의 합 */
	private static int getAmount() {
		int sum = 0;
		while (!fbQueue.isEmpty()) {
			sum += fbQueue.poll().m;
		}
		return sum;
	}
}