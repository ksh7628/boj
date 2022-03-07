package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 7.
 * 
 * 분류: 수학, 분할 정복, 분할 정복을 이용한 거듭제곱, 선형대수학
 * 난이도: 골드4
 * 소요 시간: 0h 40m
 * 혼자 품: O
 * 풀이: 분할 정복을 이용해서 지수가 홀수일 때랑 짝수일 때 계산을 달리 해서 해결.
 * 느낀 점: 예전에 풀었던 https://www.acmicpc.net/problem/1629 문제와 접근법이 똑같아서 쉽게 풀 수 있었다.
 */
public class Boj_G4_10830_행렬제곱 {
	static int N;
	static final int MOD = 1000;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		long B = Long.parseLong(st.nextToken());

		int[][] matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int[][] res = divide(matrix, B);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 행렬 원소가 1000에 B가 1일 경우가 존재 -> 0으로 만들어줘야 함
				sb.append(res[i][j] % MOD).append(" ");
			}
			sb.append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 분할 정복 */
	private static int[][] divide(int[][] matrix, long exp) {
		// 지수가 1이면 자기 자신을 반환
		if (exp == 1) {
			return matrix;
		}

		// 절반으로 나눠서 계산
		int[][] half = divide(matrix, exp / 2);
		int[][] temp = multiply(half, half);

		// 지수가 짝수라면 더 곱해줄 필요 X
		if (exp % 2 == 0) {
			return temp;
		}

		// 지수가 홀수라면 한번 더 곱해줌
		return multiply(temp, matrix);
	}

	/* 두 행렬의 곱 */
	private static int[][] multiply(int[][] A, int[][] B) {
		int[][] R = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					R[i][j] = (R[i][j] + (A[i][k] % MOD * B[k][j] % MOD)) % MOD;
				}
			}
		}

		return R;
	}
}