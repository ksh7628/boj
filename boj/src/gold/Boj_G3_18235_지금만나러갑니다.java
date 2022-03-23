package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 24.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 0h 44m
 * 혼자 품: O
 * 풀이: 2개의 큐를 사용한 bfs로 품. 두개의 큐 모두 값을 꺼낼 때마다 상대방이 해당 좌표에 도달했는지 체크해야 한다.
 * 느낀 점: 
 * 이미 갔던 좌표라도 다시 방문할 수 있다는 점을 늦게 깨닫게 됨.
 * dist 배열에 저장된 값이 아닌 depth 변수(day)로 확인을 해야 한다는 것도 알게 됨.
 */
public class Boj_G3_18235_지금만나러갑니다 {
	static int[] map;
	static int[][] dist;
	static int N, A, B;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());

		map = new int[N + 1];
		dist = new int[2][N + 1];
		System.out.println(bfs());
		br.close();
	}

	/*
	 * 2개의 큐를 사용
	 * 오리 먼저 이동 후 육리 이동
	 * 오리, 육리 각각 큐에서 값을 꺼냈을 때 상대방이 도착했는지 확인
	 */
	private static int bfs() {
		ArrayDeque<Integer> fq = new ArrayDeque<>();
		ArrayDeque<Integer> sq = new ArrayDeque<>();
		dist[0][A] = 1;
		dist[1][B] = 1;
		fq.offer(A);
		sq.offer(B);

		int day = 1;
		while (!fq.isEmpty() && !sq.isEmpty()) {
			int fs = fq.size();
			int ss = sq.size();
			day++;

			for (int i = 0; i < fs; i++) {
				int cur = fq.poll();
				if (dist[1][cur] == day - 1) {
					return day - 2;
				}

				int nxt = cur + (1 << (day - 2));
				if (isCheck(nxt) && dist[0][nxt] != day) {
					dist[0][nxt] = day;
					fq.offer(nxt);
				}

				nxt = cur - (1 << (day - 2));
				if (isCheck(nxt) && dist[0][nxt] != day) {
					dist[0][nxt] = day;
					fq.offer(nxt);
				}
			}

			for (int i = 0; i < ss; i++) {
				int cur = sq.poll();
				if (dist[0][cur] == day - 1) {
					return day - 2;
				}

				int nxt = cur + (1 << (day - 2));
				if (isCheck(nxt) && dist[1][nxt] != day) {
					dist[1][nxt] = day;
					sq.offer(nxt);
				}

				nxt = cur - (1 << (day - 2));
				if (isCheck(nxt) && dist[1][nxt] != day) {
					dist[1][nxt] = day;
					sq.offer(nxt);
				}
			}
		}

		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x) {
		return 1 <= x && x <= N;
	}
}