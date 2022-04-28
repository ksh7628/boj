package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 28.
 * 
 * 분류: 그래프 이론, 자료 구조, 문자열, 그래프 탐색, 해시를 사용한 집합과 맵, 순열 사이클 분할
 * 난이도: 실버1
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: dfs로 사이클을 판단해서 사이클 개수를 구한다.
 * 느낀 점: 사이클을 찾는 로직만 구하면 간단한 문제.
 */
public class Boj_S1_5107_마니또 {
	static Map<String, String> map;
	static Set<String> set;
	static int res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1;; tc++) {
			int N = Integer.parseInt(br.readLine());
			if (N == 0) {
				break;
			}

			map = new HashMap<>();
			set = new HashSet<>();
			String[] name = new String[N];
			res = 0;

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				String a = name[i] = st.nextToken();
				String b = st.nextToken();
				map.put(a, b);
			}

			for (String s : name) {
				if (!set.contains(s)) {
					dfs(s);
				}
			}

			sb.append(tc).append(" ").append(res).append("\n");
		}
		
		System.out.print(sb);
		br.close();
	}

	private static void dfs(String s) {
		set.add(s);
		if (!set.contains(map.get(s))) {
			dfs(map.get(s));
		} else {
			res++;
		}
	}
}