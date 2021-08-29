package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 8.
 *
 * 분류: 그래프 이론, 그래프 탐색, 트리, 깊이 우선 탐색
 * 난이도: 골드3
 * 혼자 품: O
 * 풀이:  1. 인접한 정점들을 인접 리스트에 저장한다.
 *       2. 루트에서 bfs를 통해 가장 먼 정점을 찾는다. 이때 정점간 거리를 리스트에 저장한 후 모든 탐색이 끝나면 정렬을 통해 가장 먼 정점을 찾는다.
 *       3. 2에서 구한 정점에서 한번 더 bfs를 통해 가장 먼 정점을 찾는다. 마찬가지로 리스트를 통해 가장 먼 정점을 찾는데 이 정점간 거리가 트리의 지름이 된다.
 * 느낀 점: bfs를 2번 실행하고 실행할때마다 리스트에 정보를 담아서 다시 정렬을 해서 그런지 성능이 썩 좋지 못하다. 다른 사람들의 코드를 보니 dfs로 위의 2,3 과정을
 *        한번만 실행을 하는 점과 리스트로 정렬을 굳이 안하고 거리 변수를 하나두고 최대값을 갱신해주는 식으로 풀이한것을 보니 내 풀이가 좋은 풀이는 아닌 것 같아서
 *        나중에 다시 풀어봐야겠다고 느낀 문제이다. 
 */
public class Boj_G3_1167_트리의지름 {
	static class Tree implements Comparable<Tree> {
		int v, d;// 정점, 거리

		Tree(int v, int d) {
			this.v = v;
			this.d = d;
		}

		@Override
		public int compareTo(Tree t) {
			return Integer.compare(t.d, this.d);
		}
	}

	static ArrayList<Tree>[] adjList;// 인접 리스트
	static int[] dist;// 방문 체크 및 현재 정점까지의 거리를 저장하는 배열
	static int V, x;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		V = Integer.parseInt(br.readLine());
		adjList = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) {
			adjList[i] = new ArrayList<>();
		}
		for (int i = 0; i < V; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			while (true) {
				int to = Integer.parseInt(st.nextToken());
				if (to == -1) {// 값이 -1이라면 break
					break;
				}
				int dist = Integer.parseInt(st.nextToken());
				adjList[from].add(new Tree(to, dist));
			}
		}
		bfs(1);// 1. 루트에서 가장 먼 정점(x)을 찾음
		System.out.println(bfs(x));// 정점 x에서 가장 먼 정점간 거리 = 트리의 지름
		br.close();
	}

	/* bfs를 통해 가장 먼 정점을 찾고 그 정점까지의 거리를 반환하는 메서드 */
	private static int bfs(int vertex) {
		ArrayList<Tree> treeList = new ArrayList<>();// 현재 정점에서 방문하는 정점과 그 정점에 대한 거리를 저장하는 리스트
		dist = new int[V + 1];
		Arrays.fill(dist, -1);
		ArrayDeque<Integer> q = new ArrayDeque<>();
		dist[vertex] = 0;
		q.offer(vertex);
		while (!q.isEmpty()) {
			int idx = q.poll();
			for (Tree t : adjList[idx]) {
				if (dist[t.v] >= 0) {
					continue;
				}
				dist[t.v] = dist[idx] + t.d;
				q.offer(t.v);
				treeList.add(new Tree(t.v, dist[t.v]));
			}
		}
		Collections.sort(treeList);// 거리의 내림차순으로 정렬
		x = treeList.get(0).v;
		return treeList.get(0).d;
	}
}