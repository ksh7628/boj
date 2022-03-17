package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 17.
 * 
 * 분류: 두 포인터
 * 난이도: 실버3
 * 소요 시간: 0h 13m
 * 혼자 품: O
 * 풀이: 두 개의 인덱스를 움직여가면서 현재 구간이 M보다 작으면 e증가, 같거나 크다면 s 증가 후 e에 복사, 같을 때 경우의 수 증가.
 * 느낀 점: 투 포인터 기본 문제인 만큼 쉽게 풀었는데 e가 끝까지 가면 더 이상 살필 필요가 없다는 점도 다시 알게 되었다.
 */
public class Boj_S3_2003_수들의합2 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int s = 0, e = 0, sum = 0, res = 0;
		while (e < N) {
			sum += arr[e];

			if (sum == M) {
				sum = 0;
				res++;
				e = ++s;
			} else if (sum > M) {
				sum = 0;
				e = ++s;
			} else {
				e++;
			}
		}

		System.out.println(res);
		br.close();
	}
}