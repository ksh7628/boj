package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 19.
 * 
 * 분류: 구현, 브루트포스 알고리즘, 백트래킹
 * 난이도: 골드4
 * 소요 시간: 0h 30m
 * 혼자 품: O
 * 풀이: 
 * 1. 가로선 1 -> 2를 잇는 선과 2 -> 1을 잇는 선은 같은 선이다. 따라서 초기 가로선을 저장할 때 한 좌표에만 표시하면 됨.
 * 2. 최대 3개까지 가로선 좌표의 조합을 구한다.
 * 2-1. 아직 선을 안놓았고 연속된 가로선이 없다면 선을 그릴 수 있으므로 다음 좌표로 간다. 해당 재귀를 모두 수행했다면 선을 다시 지운다.
 * 2-2. 매번 선을 그릴 때마다 사다리를 타고 출발지와 도착지가 같은 열인지 검사한다. 같다면 최소 횟수를 갱신한다.
 * 2-3. 이전에 구한 최솟값보다 현재 가로선을 그린 횟수가 같거나 크다면 탐색하지 않는다.
 * 2-4. 횟수를 3번까지 사용했다면 -1을 출력해야 하므로 더 이상 탐색하지 않는다.
 * 느낀 점: 실수할 여지가 있는 문제였고 몇 번 풀어본 끝에 문제의 로직을 확실히 이해하게 되었다.
 */
public class Boj_G4_15684_사다리조작 {
	static boolean[][] ladder;
	static int N, H, res;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		// 행: 0-based, 열: 1-based
		ladder = new boolean[H][N + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken());
			ladder[a][b] = true;
		}

		res = INF;
		brute(0, 1, 0);
		System.out.println(res == INF ? -1 : res);
		br.close();
	}

	/* 가로선을 그릴 수 있는 곳을 다 그려가면서 최소 횟수를 구한다 */
	private static void brute(int x, int y, int cnt) {
		// 구한 최솟값보다 같거나 크다면 최솟값 갱신이 안됨
		if (cnt >= res) {
			return;
		}

		// 자기 위치로 갈 수 있다면 사다리 완성
		if (isGoal()) {
			res = cnt;
			return;
		}

		// 횟수를 3번까지 사용했다면 더 이상 사용 불가
		if (cnt == 3) {
			return;
		}

		// 조합
		for (int i = x; i < H; i++) {
			for (int j = y; j < N; j++) {
				// 아직 선을 안놓았고 연속된 가로선이 안만들어진다면 선을 그릴 수 있다
				if (!ladder[i][j - 1] && !ladder[i][j] && !ladder[i][j + 1]) {
					ladder[i][j] = true;
					brute(i, j, cnt + 1);
					ladder[i][j] = false;
				}
			}
			y = 1;
		}
	}

	/* 각 자리에서 사다리를 탔을 때 모두 제자리로 도착하는지 체크 */
	private static boolean isGoal() {
		for (int j = 1; j <= N; j++) {
			int x = 0, y = j;

			while (x < H) {
				if (y > 0 && ladder[x][y - 1]) {
					y--;
				} else if (y <= N && ladder[x][y]) {
					y++;
				}
				x++;
			}

			if (j != y) {
				return false;
			}
		}

		return true;
	}
}