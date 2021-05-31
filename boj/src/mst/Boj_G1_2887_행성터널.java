package mst;

import java.io.*;
import java.util.*;

public class Boj_G1_2887_행성터널 {
	static class Point {
		int n, x, y, z;

		public Point(int n, int x, int y, int z) {
			super();
			this.n = n;
			this.x = x;
			this.y = y;
			this.z = z;
		}

	}

	static class Edge implements Comparable<Edge> {
		int from, to, d;

		public Edge(int from, int to, int d) {
			super();
			this.from = from;
			this.to = to;
			this.d = d;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.d, o.d);
		}

	}

	static ArrayList<Point> al;
	static ArrayList<Edge> el;
	static int[] p;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		al = new ArrayList<>();
		el = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			al.add(new Point(i, x, y, z));
		}

		listSort();
		make();
		System.out.println(kruskal());
		br.close();
	}

	private static void listSort() {
		Collections.sort(al, (o1, o2) -> o1.x - o2.x);
		for (int i = 0; i < N - 1; i++) {
			int d = Math.abs(al.get(i).x - al.get(i + 1).x);
			el.add(new Edge(al.get(i).n, al.get(i + 1).n, d));
		}

		Collections.sort(al, (o1, o2) -> o1.y - o2.y);
		for (int i = 0; i < N - 1; i++) {
			int d = Math.abs(al.get(i).y - al.get(i + 1).y);
			el.add(new Edge(al.get(i).n, al.get(i + 1).n, d));
		}

		Collections.sort(al, (o1, o2) -> o1.z - o2.z);
		for (int i = 0; i < N - 1; i++) {
			int d = Math.abs(al.get(i).z - al.get(i + 1).z);
			el.add(new Edge(al.get(i).n, al.get(i + 1).n, d));
		}

		Collections.sort(el);
	}

	private static void make() {
		p = new int[N];
		for (int i = 0; i < N; i++) {
			p[i] = i;
		}
	}

	private static int find(int x) {
		if (x == p[x]) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static void union(int x, int y) {
		int a = find(x);
		int b = find(y);
		if (a != b) {
			p[b] = a;
		}
	}

	private static int kruskal() {
		int res = 0;
		for (int i = 0; i < el.size(); i++) {
			Edge e = el.get(i);
			if (find(e.from) != find(e.to)) {
				res += e.d;
				union(e.from, e.to);
			}
		}

		return res;
	}
}