package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 23.
 * 
 * 분류: 누적 합, 슬라이딩 윈도우
 * 난이도: 실버3
 * 소요 시간: 0h 24m
 * 혼자 품: O
 * 풀이: 매 차례마다 현재 위치 값을 더하고 직전 구간의 처음 값을 빼는 식으로 누적해서 값을 구한다
 * 느낀 점: 변수를 잘못 출력해서 많이 틀린 문제... 예제 케이스도 우연찮게 다 맞아 떨어졌는데 코딩 실수를 줄여야겠다.
 */
public class Boj_S3_21921_블로그 {
	static int[] vc;
	static int N, X;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		vc = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			vc[i] = Integer.parseInt(st.nextToken());
		}

		solution();
		br.close();
	}

	/* 슬라이딩 윈도우 기법으로 직전값을 계속 갱신시키면서 최댓값을 비교한다 */
	private static void solution() {
		int sum = 0;
		for (int i = 0; i < X; i++) {
			sum += vc[i];
		}

		int res = sum, cnt = 1;
		for (int i = X; i < N; i++) {
			// 이전날을 빼고 오늘차를 더한다
			sum += vc[i] - vc[i - X];

			if (res < sum) {// 최댓값 갱신 후 기간 초기화
				res = sum;
				cnt = 1;
			} else if (res == sum) {// 최댓값이 같다면 기간 갯수 증가
				cnt++;
			}
		}

		StringBuilder sb = new StringBuilder();

		// 최대 방문자 수가 0명
		if (res == 0) {
			sb.append("SAD");
		} else {
			sb.append(res).append("\n").append(cnt);
		}

		System.out.print(sb);
	}
}