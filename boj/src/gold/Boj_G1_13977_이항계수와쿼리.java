package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 16.
 *
 * 분류: 수학, 정수론, 조합론, 페르마의 소정리, 모듈로 곱셈 역원
 * 난이도: 골드1
 * 혼자 품: X
 * 풀이: 
 * 1. 1부터 400만 팩토리얼까지의 값을 전처리 수행한다.
 * 2. a^(p-2) ≡ 1/a (mod p) 를 이용하여 (n-r)!*r!의 값을 구하기 위해 분할 정복을 통해 역원의 값을 구한다.
 * 3. 2에서 구한 역원의 값과 n!을 곱해준다.
 * 느낀 점: 
 * 예전에 페르마의 소정리를 배웠는데 마지막에 n!을 곱해준다는 사실을 잊었다. 결국 기억이 잘 나지 않아서 예전에 푼 코드를 참조하였다.
 * 비록 코딩 테스트랑은 거리가 먼 문제이지만 개념을 확실하게 알게 된 문제였다.
 */
public class Boj_G1_13977_이항계수와쿼리 {
	static final int MOD = 1000000007;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long[] fa = new long[4000001];// 미리 최대 n값인 400만까지의 팩토리얼 값을 저장
		fa[0] = 1;// 0! = 1
		for (int i = 1; i <= 4000000; i++) {
			fa[i] = (fa[i - 1] * i) % MOD;
		}

		StringBuilder sb = new StringBuilder();
		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int n = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());

			long fe = fermat((fa[n - k] * fa[k]) % MOD, MOD - 2);// n!의 역원
			sb.append((fa[n] * fe) % MOD).append("\n");
		}

		System.out.println(sb);
		br.close();
	}

	/* 분할 정복을 통해 역원의 값을 구해줌 */
	private static long fermat(long num, int exp) {
		if (exp == 0) {
			return 1;
		}

		long half = fermat(num, exp / 2);
		long base = (half * half) % MOD;

		if (exp % 2 == 0) {// 짝수이면 나눈 값 그대로 사용
			return base;
		}

		return (base * num) % MOD;// 홀수이면 밑을 한번 더 곱해줌
	}
}