package number_theory;

import java.util.Scanner;

/**
 * <pre>
 * number_theory 
 * Boj_G1_11401_이항계수3.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 20.
 * @version	: 0.1
 *
 * 분류: 수학, 정수론, 페르마의 소정리
 * 난이도: 골드1
 * 혼자 품: O
 * 풀이: 페르마의 소정리를 이용하여 풀이하였다. 먼저 각 팩토리얼 값의 모듈러를 취한 값을 배열에 저장하는 전처리 작업을 거친 후
 *      a^(p-2) ≡ 1/a (mod p) 를 이용하여 (n-r)!*r!의 값을 구하기 위해 모듈러 연산은 곱셈에 대해 닫혀 있으므로
 *      역원의 값을 분할 정복을 통해 구해준 후 구해둔 n!과 곱해서 답을 구해주었다.
 * 느낀 점: n값이 크기 때문에 여태 사용했던 dp로도 풀 수 없는 문제였는데 페르마의 소정리에 대해 알게 되면
 *        어렵지 않게 풀 수 있는 문제인것 같다.
 */
public class Boj_G1_11401_이항계수3 {
	static final int MOD = 1000000007;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();

		long[] fa = new long[n + 1];
		fa[0] = 1;
		for (int i = 1; i <= n; i++) {// 미리 팩토리얼 값에서 나머지연산을 취한 값들을 배열에 저장
			fa[i] = (fa[i - 1] * i) % MOD;
		}

		long fe = fermat((fa[n - k] * fa[k]) % MOD, MOD - 2);
		System.out.println((fa[n] * fe) % MOD);
		sc.close();
	}

	/* 분할 정복을 통해 오버플로우가 발생하지 않도록 모듈러 연산을 한 거듭제곱의 값을 반환하는 메서드 */
	private static long fermat(long n, int e) {
		if (e == 0) {
			return 1;
		}

		long half = fermat(n, e / 2);
		long base = (half * half) % MOD;
		if (e % 2 == 0) {// 짝수이면 그대로 나눈값 사용
			return base;
		}
		// 홀수이면 밑을 한번 더 곱해줌
		return (base * n) % MOD;
	}
}