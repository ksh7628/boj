package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 22.
 * 
 * 분류: 누적 합
 * 난이도: 실버2
 * 소요 시간: 0h 13m
 * 혼자 품: O
 * 풀이: 
 * 1. 문자열을 순회하면서 현재 등장하는 문자는 sum 배열에 이전 값 + 1로 저장하고 아닌 문자는 이전 값을 그대로 가져온다.
 * 2. 각 쿼리마다 l이 0이면 sum[r][문자]를 구하면 되고 그렇지 않다면 r - (l - 1)의 문자 개수를 구하면 됨.
 * 느낀 점: 알파벳을 인덱스로 나타내면 되는 부분합 문제.
 */
public class Boj_S2_16139_인간컴퓨터상호작용 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();

		int[][] sum = new int[S.length()][26];
		sum[0][S.charAt(0) - 'a']++;
		for (int i = 1; i < S.length(); i++) {
			char c = S.charAt(i);

			for (int j = 0; j < 26; j++) {
				if (c - 'a' - j == 0) {
					sum[i][j] = sum[i - 1][j] + 1;
				} else {
					sum[i][j] = sum[i - 1][j];
				}
			}
		}

		int q = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < q; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int idx = st.nextToken().charAt(0) - 'a';
			int l = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			sb.append(l == 0 ? sum[r][idx] : sum[r][idx] - sum[l - 1][idx]).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}