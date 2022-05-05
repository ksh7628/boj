package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 6.
 * 
 * 분류: 수학, 구현, 정렬
 * 난이도: 실버3
 * 소요 시간: 0h 36m
 * 혼자 품: O
 * 풀이: 절사평균과 보정평균을 구한 후 소수점 아홉번째자리가 1인 매우 작은 소수를 더해준 후 셋째자리까지 반올림 해준다.
 * 느낀 점: 0.5에 매우 근접하면서 작은 소수를 String.format로 반올림을 해줄 경우, 0이 되는 경우를 방지해야 된다는 사실을 알게 됨.
 */
public class Boj_S3_6986_절사평균 {
	static double[] num;
	static int N, K;
	static double res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		num = new double[N];
		for (int i = 0; i < N; i++) {
			num[i] = Double.parseDouble(br.readLine());
		}

		solution();
		br.close();
	}

	private static void solution() {
		Arrays.sort(num);
		setAvg1();
		System.out.println(String.format("%.2f", res / (N - 2 * K) + 1e-9));
		setAvg2();
		System.out.println(String.format("%.2f", res / N + 1e-9));
	}

	private static void setAvg1() {
		for (int i = K; i < N - K; i++) {
			res += num[i];
		}
	}

	private static void setAvg2() {
		res += num[K] * K + num[N - 1 - K] * K;
	}
}