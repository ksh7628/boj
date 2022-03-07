package gold;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 7.
 * 
 * 분류: 수학, 분할 정복을 이용한 거듭제곱
 * 난이도: 골드2
 * 소요 시간: 0h 34m
 * 혼자 품: O
 * 풀이: https://www.acmicpc.net/problem/10830과 동일한 방식에 피보나치 공식을 추가해서 품.
 * 느낀 점: 
 * 오버플로우만 주의하면 그 전에 풀었던 분할 정복을 이용한 거듭제곱 유형과 동일.
 * 분할 정복 거듭제곱법을 알고 나니 어려운 문제가 아니란걸 깨달았지만 그전까지는 풀 엄두도 못낸 문제였다.
 */
public class Boj_G2_11444_피보나치수6 {
	static final int MOD = 1000000007;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println(getFibo(n));
		br.close();
	}

	private static long getFibo(long x) {
		// 피보나치 행렬
		long[][] matrix = { { 1, 1 }, { 1, 0 } };
		matrix = divide(matrix, x);
		return matrix[0][1];
	}

	private static long[][] divide(long[][] matrix, long exp) {
		if (exp == 1) {
			return matrix;
		}

		long[][] half = divide(matrix, exp / 2);
		long[][] temp = multiply(half, half);

		if (exp % 2 == 0) {
			return temp;
		}

		return multiply(temp, matrix);
	}

	private static long[][] multiply(long[][] A, long[][] B) {
		long[][] R = new long[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					R[i][j] = (R[i][j] + (A[i][k] % MOD * B[k][j] % MOD)) % MOD;
				}
			}
		}

		return R;
	}
}