package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 25.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 트리, 최소 공통 조상
 * 난이도: 골드4
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 부모를 값으로, 자식을 인덱스로 갖는 tree배열로 풀이
 * 1. a 노드에서 루트 노드 까지 방문처리
 * 2. b 노드에서 루트 노드까지 탐색하다가 방문된 노드를 발견(LCA)
 * 느낀 점: 트리의 부모는 유일하기 때문에 인접 리스트를 사용하지 않아도 배열로 풀 수 있는 문제였다.
 */
public class Boj_G4_3584_가장가까운공통조상 {
	static int[] tree;
	static boolean[] visit;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			tree = new int[N + 1];
			visit = new boolean[N + 1];

			for (int i = 0; i < N - 1; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				tree[child] = parent;
			}

			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			sb.append(solution(a, b)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/*
	 * tree[0] = 0
	 * a 노드에서 루트 노드까지의 경로를 방문처리
	 * b 노드에서 루트 노드까지 찾아가다가 방문처리가 된곳 -> LCA
	 */
	private static int solution(int a, int b) {
		while (tree[a] != a) {
			visit[a] = true;
			a = tree[a];
		}

		int res = 0;
		while (true) {
			if (visit[b]) {
				res = b;
				break;
			}
			b = tree[b];
		}

		return res;
	}
}