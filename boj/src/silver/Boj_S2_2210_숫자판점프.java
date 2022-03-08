package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 8.
 * 
 * 분류: 그래프 이론, 브루트포스 알고리즘, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 실버2
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: dfs로 6자리 숫자를 만든 후 set으로 중복 여부를 판단하는 방법으로 품.
 * 느낀 점: dfs or bfs 기본 문제는 빠르면 5분 늦으면 10분안에 풀 수 있는 실력을 유지해야겠다.
 */
public class Boj_S2_2210_숫자판점프 {
	static int[][] map = new int[5][5];
	static Set<String> set = new HashSet<>();
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 5; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 5; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				dfs(i, j, Integer.toString(map[i][j]));
			}
		}

		System.out.println(set.size());
		br.close();
	}

	private static void dfs(int x, int y, String num) {
		if (num.length() == 6) {
			if (!set.contains(num)) {
				set.add(num);
			}

			return;
		}

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (nx < 0 || nx > 4 || ny < 0 || ny > 4) {
				continue;
			}

			dfs(nx, ny, num + Integer.toString(map[nx][ny]));
		}
	}
}