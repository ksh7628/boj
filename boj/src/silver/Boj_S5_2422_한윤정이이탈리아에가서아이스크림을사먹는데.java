package silver;

import java.io.*;
import java.util.*;

/**
 * @author : KimSeonhong
 * @date : 2022. 5. 8.
 * 
 * 분류: 브루트포스 알고리즘
 * 난이도: 실버5
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: 
 * 1. set에 섞어먹으면 안 되는 조합의 번호를 저장한다. 해싱이 가능하도록 둘 중 작은 수에 1000을 곱하고 큰 수를 더한 번호를 저장.
 * 2. NC3의 조합을 구해서 set에 있는 번호를 제외한 경우의 수를 센다.
 * 느낀 점: 주어지는 섞어먹으면 안 되는 조합의 번호가 오름차순이 아닐 수도 있기 때문에 min과 max를 따로 구해야 한다는 것을 알게 됨.
 */
public class Boj_S5_2422_한윤정이이탈리아에가서아이스크림을사먹는데 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			set.add(Math.min(a, b) * 1000 + Math.max(a, b));
		}

		int res = 0;
		for (int i = 1; i <= N - 2; i++) {
			for (int j = i + 1; j <= N - 1; j++) {
				if (set.contains(i * 1000 + j)) {
					continue;
				}
				for (int k = j + 1; k <= N; k++) {
					if (set.contains(j * 1000 + k)) {
						continue;
					}
					if (set.contains(i * 1000 + k)) {
						continue;
					}
					res++;
				}
			}
		}

		System.out.println(res);
		br.close();
	}
}