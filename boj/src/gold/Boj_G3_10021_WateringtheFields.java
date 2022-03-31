package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 31.
 * 
 * 분류: 그래프 이론, 최소 스패닝 트리
 * 난이도: 골드3
 * 소요 시간: 0h 28m
 * 혼자 품: O
 * 풀이: 크루스칼 알고리즘으로 풀이. C 이상이 아닌 간선은 합치지 않음. 합친 간선의 개수가 N-1보다 작다면 -1 출력.
 * 느낀 점: 간선 길이를 정렬해주지 않아서 맞왜틀 하다가 뒤늦게 고치게 되었다. 그리고 프림 알고리즘으로 푼다면 효율성이 증가한다.
 */
public class Boj_G3_10021_WateringtheFields {
	static class Node implements Comparable<Node> {
		int x, y;
		long dist;

		public Node(int x, int y, long dist) {
			super();
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			return Long.compare(this.dist, o.dist);
		}
	}

	static Node[] node;
	static int[][] pos;
	static int[] p;
	static int N, C, S;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		S = N * (N - 1) / 2;

		node = new Node[S];
		pos = new int[N][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			pos[i][0] = Integer.parseInt(st.nextToken());
			pos[i][1] = Integer.parseInt(st.nextToken());
		}

		int idx = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				node[idx++] = new Node(i, j, getDistance(pos[i], pos[j]));
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static long solution() {
		init();
		Arrays.sort(node);
		long res = 0, cnt = 0;
		
		for (Node n : node) {
			// C이상이고 아직 안합친 간선이어야만 MST가 보장된다.
			if (n.dist >= C && union(n.x, n.y)) {
				res += n.dist;
				if (++cnt == N - 1) {
					return res;
				}
			}
		}

		return -1;
	}

	private static void init() {
		p = new int[S];
		for (int i = 0; i < S; i++) {
			p[i] = i;
		}
	}

	private static boolean union(int x, int y) {
		int a = find(x), b = find(y);
		if (a != b) {
			p[b] = a;
			return true;
		}
		return false;
	}

	private static int find(int x) {
		if (x == p[x]) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static int getDistance(int[] a, int[] b) {
		return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
	}
}