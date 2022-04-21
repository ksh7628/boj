package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 21.
 * 
 * 분류: 브루트포스 알고리즘, 기하학, 피타고라스 정리
 * 난이도: 실버1
 * 소요 시간: 0h 21m
 * 혼자 품: O
 * 풀이: 반복문을 통해 NC3의 조합을 뽑고 세 변의 길이의 제곱을 구한 후 가장 큰 수가 나머지 두 수의 합과 같다면 피타고라스 정리에 의해 직각삼각형이다.
 * 느낀 점: N^3 브루트포스로 풀었는데 다른 사람의 코드를 보니 유클리드 호제법이나 N^2logN 풀이도 존재한다는 것을 알게 되었다.
 */
public class Boj_S1_1711_직각삼각형 {
	static long[][] pos;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		pos = new long[N][2];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			pos[i][0] = Long.parseLong(st.nextToken());
			pos[i][1] = Long.parseLong(st.nextToken());
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					if (isRightTriangle(i, j, k)) {
						res++;
					}
				}
			}
		}
		return res;
	}

	private static boolean isRightTriangle(int i, int j, int k) {
		long a = Math.abs(pos[i][0] - pos[j][0]) * Math.abs(pos[i][0] - pos[j][0]) + Math.abs(pos[i][1] - pos[j][1]) * Math.abs(pos[i][1] - pos[j][1]);
		long b = Math.abs(pos[j][0] - pos[k][0]) * Math.abs(pos[j][0] - pos[k][0]) + Math.abs(pos[j][1] - pos[k][1]) * Math.abs(pos[j][1] - pos[k][1]);
		long c = Math.abs(pos[k][0] - pos[i][0]) * Math.abs(pos[k][0] - pos[i][0]) + Math.abs(pos[k][1] - pos[i][1]) * Math.abs(pos[k][1] - pos[i][1]);

		long max = 0;
		max = Math.max(max, a);
		max = Math.max(max, b);
		max = Math.max(max, c);

		if (a == max && a == b + c) {
			return true;
		}
		if (b == max && b == a + c) {
			return true;
		}
		if (c == max && c == a + b) {
			return true;
		}
		return false;
	}
}