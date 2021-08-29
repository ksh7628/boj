package silver;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 13.
 *
 * 분류: 수학, 그리디 알고리즘
 * 난이도: 실버5
 * 혼자 품: O
 * 풀이: 
 * 1. 입력 받은 수들의 합을 시그마 합 공식  S = N*(N+1)/2 에 따라 루트값을 구해서 N에 저장(√S ≈ N^2+N)
 * 2. N을 다시 시그마 합 공식에 넣어서 S보다 크다면 1을 빼줌
 * 느낀 점: 
 * 시그마 합 공식을 안다면 간단하게 풀리는 문제였다.
 */
public class Boj_S5_1789_수들의합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long S = Long.parseLong(br.readLine());
		long N = (long) Math.sqrt(S * 2);
		if (N * (N + 1) / 2 > S) {
			N--;
		}
		System.out.println(N);
		br.close();
	}
}