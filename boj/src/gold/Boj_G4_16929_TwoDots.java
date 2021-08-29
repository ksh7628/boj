package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 19.
 *
 * 분류: 그래프 이론, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 이중 for문을 돌려 모든 좌표를 하나씩 dfs로 사이클을 검사하다가 사이클이 만들어지면 반복문을 즉시 종료한다.
 * 2. dfs를 통해 사이클을 판별하는데 이전 좌표와 현재 좌표를 인자로 받아서 다음 좌표로 갈 수 있다면 이전 좌표를 현재 좌표로, 현재 좌표를 다음 좌표로 인자에 넣어준다.
 * 3. 시작 좌표로부터 모든 탐색이 끝났을 때 다시 처음으로 돌아온다면 사이클이 만들어진 것이고 그렇지 않다면 사이클이 만들어지지 않은 것이다. 
 * 느낀 점: 
 * 처음에는 이 방식이 아닌 visit배열을 계속 초기화 시켜주면서 dfs의 길이가 4이상이고 탐색을 시작한 좌표와 다음 탐색을 위한 좌표가 같아질 때 사이클을 판별하는 식으로
 * 코드를 작성했었는데, 이전 방식을 사용한다면 시간복잡도가 최악의 경우 O((N*M)^2)로 뛰기 때문에 다른 문제에서 N, M값이 커져버리면 시간초과가 날 것이다.
 * 그래서 다른 사람의 코드를 참조해보니 이전 좌표와 현재 좌표를 계속 갱신시켜주면서 사이클을 판별하는 방법이 있다는 것을 알게 되었다. 이 방법의 경우 O(N*M)으로 모두
 * 해결이 되기에 좋은 방법을 터득하게 되었다.
 */
public class Boj_G4_16929_TwoDots {
	static char[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static boolean isCycle;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		loop: for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (isCycle) {// 이전 탐색에서 사이클이 발생했다면 반복문 종료
					break loop;
				}
				if (!visit[i][j] && dfs(-1, -1, i, j)) {
					isCycle = true;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		if (isCycle) {
			sb.append("Yes");
		} else {
			sb.append("No");
		}

		System.out.print(sb);
		br.close();
	}

	/* dfs를 통해 사이클을 판별 */
	private static boolean dfs(int px, int py, int x, int y) {
		if (visit[x][y]) {// 사이클이 형성됨
			return true;
		}

		visit[x][y] = true;
		for (int dir = 0; dir < 4; dir++) {
			int nx = x + dx[dir];
			int ny = y + dy[dir];

			// 1.배열 범위를 벗어났거나 2.현재 점의 색깔과 인접한 점의 색깔이 다르거나 3.방금 전 탐색한 좌표랑 현재 탐색하는 좌표가 같다면 continue
			if (check(nx, ny) || map[x][y] != map[nx][ny] || (px == nx && py == ny)) {
				continue;
			}

			if (dfs(x, y, nx, ny)) {// 다음 탐색을 계속해서 사이클이 형성된다면 true 반환
				return true;
			}
		}

		return false;// 사이클이 형성 되지 않음
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}