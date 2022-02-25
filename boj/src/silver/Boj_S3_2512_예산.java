package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 25.
 * 
 * 분류: 이분 탐색, 매개 변수 탐색
 * 난이도: 실버3
 * 소요 시간: 0h 34m
 * 혼자 품: O
 * 풀이: 0부터 예산요청의 최댓값 사이에서 parametric search를 통해 배정할 수 있는 예산의 최댓값을 찾는다.
 * 느낀 점: 
 * 오랜만에 푸는 parametric search 문제였는데 처음에 부등호를 잘못 사용해서 틀렸다.
 * 이런 유형의 문제는 경계값을 엄밀하게 정해야 된다는 것을 다시 한번 알게 되었다.
 */
public class Boj_S3_2512_예산 {
	static int[] money;
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		money = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			money[i] = Integer.parseInt(st.nextToken());
		}
		M = Integer.parseInt(br.readLine());

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int sum = 0, max = 0;
		for (int i = 0; i < N; i++) {
			max = Math.max(max, money[i]);
		}

		int left = 0, right = max;
		while (left <= right) {
			int mid = (left + right) / 2;
			sum = 0;

			for (int i = 0; i < N; i++) {
				if (money[i] < mid) {
					sum += money[i];
				} else {
					sum += mid;
				}
			}

			if (sum <= M) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		return right;
	}
}