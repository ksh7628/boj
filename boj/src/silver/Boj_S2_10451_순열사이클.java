package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 순열 사이클 분할
 * 난이도: 실버2
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: 1차원 배열로 리스트를 생성하고 방문 여부를 확인해 나가면서 방문한 곳을 찾으면 사이클이 형성 됨. (dfs로도 가능)
 * 느낀 점: 사이클 판정 문제인데 dfs로 풀지 않아도 쉽게 풀 수 있는 문제였다.
 */
public class Boj_S2_10451_순열사이클 {
	static int[] p;
	static boolean[] visit;
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			p = new int[N + 1];
			visit = new boolean[N + 1];

			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i <= N; i++) {
				p[i] = Integer.parseInt(st.nextToken());
			}

			res = 0;
			for (int i = 1; i <= N; i++) {
				if (!visit[i]) {
					for (int j = i; !visit[j]; j = p[j]) {
						visit[j] = true;
					}
					
					res++;
				}
			}

			sb.append(res).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}