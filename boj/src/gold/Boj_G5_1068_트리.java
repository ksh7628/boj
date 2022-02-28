package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 28.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 트리, 깊이 우선 탐색
 * 난이도: 골드5
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 인접 리스트 방식으로 입력받은 노드들을 저장해서 트리 구조를 만든다. 이 때, -1을 입력받는 노드는 root 변수에 저장한다.
 * 2. 루트 노드부터 탐색을 시작해서 지울 노드를 발견하면 리스트에서 지운다.
 * 3. dfs로 트리를 순회해서 더 이상 자식 노드가 존재하지 않는다면 리프 노드이므로 카운팅 해준다.
 * 느낀 점: 
 * 처음 풀 때에는 지울 노드의 리스트를 초기화 시켰는데 이 경우, 지운 노드까지 리프 노드로 취급하는 문제가 발생한다.
 * 오랫동안 고민하다가 dfs 방식으로 노드를 지운 후 다시 dfs를 돌리는 방식으로 풀게 되었다.
 * 트리 구현에 대해 슬슬 익숙해지고 있지만 아직까지 부족하다는 느낌을 받게 된 문제였다.
 */
public class Boj_G5_1068_트리 {
	static List<Integer>[] tree;
	static int N, root, delete, res;
	static boolean isFind;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		tree = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			tree[i] = new ArrayList<>();
		}

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int parent = Integer.parseInt(st.nextToken());

			if (parent == -1) {
				root = i;
				continue;
			}

			tree[parent].add(i);
		}

		delete = Integer.parseInt(br.readLine());
		deleteNode(root);
		dfs(root);
		System.out.println((root == delete) ? 0 : res);
		br.close();
	}

	/* 해당하는 노드 제거 */
	private static void deleteNode(int idx) {
		if (isFind) {
			return;
		}

		for (int i = 0; i < tree[idx].size(); i++) {
			if (tree[idx].get(i) == delete) {
				tree[idx].remove(i);
				isFind = true;
				return;
			}

			deleteNode(tree[idx].get(i));
		}
	}

	/* dfs로 끝까지 탐색한 후 리프 노드의 개수를 센다 */
	private static void dfs(int idx) {
		// 자식 노드가 없다면 리프 노드이므로 개수 카운팅
		if (tree[idx].isEmpty()) {
			res++;
			return;
		}

		// 다음 노드 탐색
		for (int next : tree[idx]) {
			dfs(next);
		}
	}
}