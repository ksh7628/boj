package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 18.
 * 
 * 분류: 구현, 브루트포스 알고리즘
 * 난이도: 실버2
 * 소요 시간: 0h 45m
 * 혼자 품: O
 * 풀이: 
 * 1. 최댓값과 최솟값을 미리 구한 후 최솟값부터 최댓값까지 반복문을 수행한다.
 * 2. N * M map을 모두 탐색하면서 현재 높이에서 k값을 뺀 diff로 두 가지 경우를 판단한다.
 * 2-1. 차이가 0보다 크다면 첫번째 작업 수행
 * 2-2. 차이가 0보다 작다면 두번째 작업 수행
 * 2-3. 같은 경우에는 연산 할 필요 X
 * 3. map을 한 바퀴 돌때마다 시간과 땅의 높이를 갱신한다.
 * 느낀 점: 
 * 맵 크기와 블록의 높이를 고려했을 때 브루트포스로 충분히 풀리는 문제였다.
 * 하지만 예전에 몇 번 고민만 하고 풀지 못한 문제여서 다음에 다시 풀어봐야겠다.
 */
public class Boj_S2_18111_마인크래프트 {
	static int[][] map;
	static int N, M, B;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		map = new int[N][M];

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				min = Math.min(min, map[i][j]);
				max = Math.max(max, map[i][j]);
			}
		}

		solution(min, max);
		br.close();
	}

	// min부터 max까지 반복문을 돌면서 차이값들을 비교
	private static void solution(int min, int max) {
		int minTime = Integer.MAX_VALUE, maxHeight = 0;

		for (int k = min; k <= max; k++) {
			int time = 0;
			int inventory = B;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					int diff = map[i][j] - k;

					if (diff > 0) {// 블럭 제거
						time += Math.abs(diff) * 2;
						inventory += Math.abs(diff);
					} else if (diff < 0) {// 블럭 추가
						time += Math.abs(diff);
						inventory -= Math.abs(diff);
					}
				}
			}

			// 인벤토리 블럭 개수가 음수가 아니고 시간이 같거나 적게 걸렸다면 갱신
			if (inventory >= 0 && minTime >= time) {
				minTime = time;
				maxHeight = k;
			}
		}

		System.out.println(minTime + " " + maxHeight);
	}
}
