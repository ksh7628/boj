package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 8.
 * 
 * 분류: 그래프 이론, 자료 구조, 그래프 탐색, 분리 집합
 * 난이도: 골드3
 * 소요 시간: 0h 20m
 * 혼자 품: O
 * 풀이: Edge 배열에 두 노드와 너비를 저장해서 너비의 내림차순으로 정렬한 후 매번 union 연산을 하고 주어진 c, v의 find 값이 같으면 현재 너비를 출력한다.
 * 느낀 점: MST와 유사하면서도 종료 조건이 다른 문제.
 */
public class Boj_G3_11085_군사이동 {
	static class Edge implements Comparable<Edge> {
		int start, end, width;

		public Edge(int start, int end, int width) {
			super();
			this.start = start;
			this.end = end;
			this.width = width;
		}

		@Override
		public int compareTo(Edge o) {
			return -Integer.compare(this.width, o.width);
		}
	}

	static Edge[] edge;
	static int[] parent;
	static int p, c, v;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		p = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());

		edge = new Edge[w];
		init();

		st = new StringTokenizer(br.readLine(), " ");
		c = Integer.parseInt(st.nextToken());
		v = Integer.parseInt(st.nextToken());

		for (int i = 0; i < w; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int width = Integer.parseInt(st.nextToken());
			edge[i] = new Edge(start, end, width);
		}

		System.out.println(solution());
	}

	private static int solution() {
		Arrays.sort(edge);
		int res = 0;

		for (Edge e : edge) {
			union(e.start, e.end);

			if (find(c) == find(v)) {
				res = e.width;
				break;
			}
		}

		return res;
	}

	private static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]);
	}

	private static void union(int x, int y) {
		int a = find(x), b = find(y);
		if (a != b) {
			parent[b] = a;
		}
	}

	private static void init() {
		parent = new int[p];
		for (int i = 0; i < p; i++) {
			parent[i] = i;
		}
	}
}