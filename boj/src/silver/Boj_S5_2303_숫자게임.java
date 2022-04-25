package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 25.
 * 
 * 분류: 구현, 브루트포스 알고리즘
 * 난이도: 실버5
 * 소요 시간: 0h 31m
 * 혼자 품: O
 * 풀이: 조합을 하나씩 구해가면서 최댓값보다 작은 수는 다음 재귀를 타지 않도록 가지치기를 해서 구현함.
 * 느낀 점: 
 * 2번이나 틀렸는데 모든 조합을 구한 후 이긴 사람의 번호를 구할 때, maxValue를 갱신해주지 않아서 틀리게 됨.
 * 기초적인 조합 문제인데도 실수를 했고 실제 코딩테스트 환경에서 실수하지 않도록 잘 상기해야겠다고 느낌.
 */
public class Boj_S5_2303_숫자게임 {
	static int[][] num;
	static int[] max;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		num = new int[N][5];
		max = new int[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 5; j++) {
				num[i][j] = Integer.parseInt(st.nextToken());
			}
			max[i] = -1;
		}

		comb(0, 0, 0, 0);
		int res = 1, maxValue = max[0];
		for (int i = 1; i < N; i++) {
			if (maxValue <= max[i]) {
				maxValue = max[i];
				res = i + 1;
			}
		}

		System.out.println(res);
		br.close();
	}

	/* 조합을 구한다 */
	private static void comb(int start, int cnt, int sum, int idx) {
		if (cnt == 3) {
			// 갱신된 최댓값보다 자릿수 합이 클 경우에만 다음 사람의 숫자 카드 조합을 뽑는다
			if (max[idx] < sum % 10) {
				max[idx] = sum % 10;
				// 마지막 사람까지 탐색
				if (idx < N - 1) {
					comb(0, 0, 0, idx + 1);
				}
			}
			return;
		}

		for (int i = start; i < 5; i++) {
			comb(i + 1, cnt + 1, sum + num[idx][i], idx);
		}
	}
}