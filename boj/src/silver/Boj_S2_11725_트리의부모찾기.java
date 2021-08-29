package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 10.
 *
 * 분류: 그래프 이론, 그래프 탐색, 트리, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 실버2
 * 혼자 품: O
 * 풀이: 
 * 1. 입력 받은 노드 관계를 무방향 인접 리스트에 저장
 * 2. bfs를 통해 부모 노드를 찾아서 배열에 저장 
 * 느낀 점: 
 * 처음에 해쉬맵을 사용해서 채점을 시도했는데 예외 케이스가 자꾸 발생해서 인접 리스트를 사용한 bfs를 이용해서 풀었다.
 * 트리 기초를 다지는 문제라서 어렵지 않게 푼것 같다.
 */
public class Boj_S2_11725_트리의부모찾기 {
	static List<Integer>[] tree;// 인접 리스트
	static int[] parent;// 부모 노드 배열
	static boolean[] visit;
	static int N;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		tree = new ArrayList[N + 1];
		visit = new boolean[N + 1];
		parent = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			tree[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			tree[x].add(y);// 방향성이 없으므로 양방향으로 만들어 줌
			tree[y].add(x);
		}

		bfs();
		printParent();
		br.close();
	}

	/* bfs를 통해 각 노드의 부모를 배열에 저장 */
	private static void bfs() {
		Queue<Integer> q = new LinkedList<>();
		visit[1] = true;// 루트 노드에서 시작
		q.offer(1);

		while (!q.isEmpty()) {
			int pos = q.poll();
			for (int i : tree[pos]) {
				if (visit[i]) {
					continue;
				}
				visit[i] = true;
				q.offer(i);
				parent[i] = pos;// i노드의 부모를 pos로 저장
			}
		}
	}

	/* 2번 노드부터 N번 노드까지의 부모를 출력 */
	private static void printParent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i <= N; i++) {
			sb.append(parent[i]).append("\n");
		}
		System.out.println(sb);
	}
}