import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 19.
 *
 * 분류: 그래프 이론, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: 
 * 1. 입력값을 통해 인접 리스트를 만든다.
 * 2. N번 반복문 동안 dfs를 통해 사이클이 형성된다면 list에 저장한다.
 * 3. 1 ~ N 까지 dfs를 순서대로 실행했으므로 list에 저장된 숫자들은 오름차순이 유지가 되므로 list의 크기와 저장된 값들을 출력한다.
 * 느낀 점: 
 * dfs로 사이클을 찾는 문제였다. 처음에는 모든 dfs가 끝난 후 정렬을 했었는데 굳이 그럴 필요가 없는게 for문에서 1번부터 순차적으로 dfs를 실행하기 때문에
 * 항상 list는 오름차순을 유지하게 된다.
 */
public class Boj_G5_2668_숫자고르기 {
	static ArrayList<Integer>[] al;// 인접 리스트
	static ArrayList<Integer> list;// 사이클이 만들어진 숫자를 저장하는 리스트
	static boolean[][] visit;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		al = new ArrayList[N + 1];
		list = new ArrayList<>();
		visit = new boolean[N + 1][N + 1];
		for (int i = 1; i <= N; i++) {
			al[i] = new ArrayList<>();
		}

		for (int i = 1; i <= N; i++) {
			int num = Integer.parseInt(br.readLine());
			al[i].add(num);
		}

		for (int i = 1; i <= N; i++) {
			dfs(i, i);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(list.size()).append("\n");
		for (int i : list) {
			sb.append(i).append("\n");
		}

		System.out.println(sb);
		br.close();
	}

	/* dfs를 통해 사이클 판별 */
	private static void dfs(int start, int x) {
		if (visit[start][start]) {// 사이클이 생긴다면 시작한 숫자를 리스트에 담고 탐색 중단
			list.add(start);
			return;
		}

		for (int i : al[x]) {
			if (visit[start][i]) {
				continue;
			}
			visit[start][i] = true;
			dfs(start, i);
		}
	}
}