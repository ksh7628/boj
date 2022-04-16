package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 16.
 * 
 * 분류: 누적 합
 * 난이도: 실버3
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: sum 배열에 A 배열의 현재 인덱스까지의 합을 저장하면 O(N + M)만에 해결할 수 있다.
 * 느낀 점: prefix sun 기초 문제로 빠르게 해결이 가능했다. 다른 응용 문제도 종종 코딩테스트에 나오는 만큼 풀어봐야겠다.
 */
public class Boj_S3_11441_합구하기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[] A = new int[N + 1];
		int[] sum = new int[N + 1];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
			sum[i] += sum[i - 1] + A[i];
		}

		int M = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			sb.append(sum[e] - sum[s] + A[s]).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}