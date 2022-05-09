package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 10.
 * 
 * 분류: 자료 구조, 정렬, 이분 탐색, 해시를 사용한 집합과 맵
 * 난이도: 실버4
 * 소요 시간: 0h 05m
 * 혼자 품: O
 * 풀이: set에 N개의 숫자를 저장한 후, M개의 숫자들 중 set에 있다면 1, 아니면 0을 출력.
 * 느낀 점: 해싱으로 접근하면 쉬운 문제였다.
 */
public class Boj_S4_2776_암기왕 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			Set<Integer> set = new HashSet<>();
			int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");

			for (int i = 0; i < N; i++) {
				set.add(Integer.parseInt(st.nextToken()));
			}

			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");

			for (int i = 0; i < M; i++) {
				if (set.contains(Integer.parseInt(st.nextToken()))) {
					sb.append("1\n");
				} else {
					sb.append("0\n");
				}
			}
		}
		
		System.out.print(sb);
		br.close();
	}
}