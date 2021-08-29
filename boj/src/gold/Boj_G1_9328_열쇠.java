package gold;


import java.io.*;
import java.util.*;

/**
 * <pre>
 * bitmasking 
 * Boj_G1_9328_열쇠.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 21.
 * @version	: 0.2
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 비트마스킹
 * 난이도: 골드1
 * 혼자 품: X
 * 풀이: 문제에서 빌딩 가장자리의 빈 공간이나 문을 통해 빌딩 안팎을 드나들 수 있다고 하였으므로 입력받은 배열의 크기를 2칸씩 늘려서
 *      따로 가장자리 공간을 만들어주었다. 열쇠가 총 26종류 이므로 열쇠를 관리할 1차원 boolean 배열을 하나 선언해주었다.
 *      bfs로 빌딩을 탐색하면서 각각의 경우에 대해 처리해주었고, 열쇠를 찾았을 경우 문의 좌표를 저장하는 리스트에서 열쇠에
 *      해당하는 문들을 전부 없애주는 식으로 구현하였다.
 * 느낀 점: 굉장히 해맨 문제이다. 일단 가장자리를 만들어준다는 생각 자체를 하지 못했고 백준에 있는 1194번 달이차오른다가자 문제랑은
 *        구현 방법이 달라서 계속 해매다가 결국 다른 코드를 보고 참조하여 다시 작성하였다. 평소에 떠올리지 못하는 발상들을 빨리 깨우쳐서
 *        다른 문제를 풀때 적용해보고 싶다는 생각이 들었다.
 */
public class Boj_G1_9328_열쇠 {
	static List<int[]>[] doors;// 문의 좌표를 저장하는 리스트
	static char[][] map;
	static boolean[][] visit;
	static boolean[] keys;// 열쇠 배열
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int H, W, cnt;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			doors = new ArrayList[26];
			map = new char[H + 2][W + 2];// 가장자리를 따로 만들기 위해 입력받은 배열 크기 + 2
			visit = new boolean[H + 2][W + 2];

			for (int i = 0; i < 26; i++) {
				doors[i] = new ArrayList<>();
			}
			for (int i = 0; i < H + 2; i++) {// 가장자리를 언제든지 오고 갈수있게 처음에 '.'로 초기화
				for (int j = 0; j < W + 2; j++) {
					map[i][j] = '.';
				}
			}
			for (int i = 1; i <= H; i++) {// 가장자리를 제외한 맵의 정보를 입력받음
				String input = br.readLine();
				for (int j = 1; j <= W; j++) {
					map[i][j] = input.charAt(j - 1);
				}
			}

			keys = new boolean[26];
			String keyInfo = br.readLine();
			if (!keyInfo.equals("0")) {
				for (int i = 0; i < keyInfo.length(); i++) {// 입력받은 열쇠는 보유했다고 true로 저장
					keys[keyInfo.charAt(i) - 'a'] = true;
				}
			}

			cnt = 0;
			bfs();
			sb.append(cnt).append("\n");
		}
		System.out.print(sb);
		br.close();
	}

	/* bfs로 탐색 */
	private static void bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[0][0] = true;
		q.offer(new int[] { 0, 0 });// 처음에 아무 가장자리에서 시작해도 됨

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0], y = cur[1];
			for (int dir = 0; dir < 4; dir++) {
				int nx = x + dx[dir];
				int ny = y + dy[dir];
				// 배열 범위를 벗어났거나 벽이거나 방문했다면 continue
				if (check(nx, ny) || map[nx][ny] == '*' || visit[nx][ny]) {
					continue;
				}
				if (map[nx][ny] >= 'A' && map[nx][ny] <= 'Z') {// 문이라면
					if (keys[map[nx][ny] - 'A']) {// 해당 열쇠를 가지고 있다면 문을 없애고 방문처리 후 큐에 넣어줌
						map[nx][ny] = '.';
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					} else {// 해당 열쇠를 가지고 있지 않다면 리스트에 넣어줌
						doors[map[nx][ny] - 'A'].add(new int[] { nx, ny });
					}
				} else if (map[nx][ny] >= 'a' && map[nx][ny] <= 'z') {// 열쇠라면
					keys[map[nx][ny] - 'a'] = true;// 열쇠 보유 처리를 하고 방문처리 후 큐에 넣어줌
					visit[nx][ny] = true;
					q.offer(new int[] { nx, ny });

					for (int i = 0; i < 26; i++) {// A ~ Z
						if (doors[i].size() != 0 && keys[i]) {// 해당 문의 좌표가 있고 그 문에 해당하는 열쇠가 있다면
							for (int j = 0; j < doors[i].size(); j++) {// 해당 문의 좌표들의 개수만큼 반복
								int tx = doors[i].get(j)[0];
								int ty = doors[i].get(j)[1];
								map[tx][ty] = '.';// 문을 없애고 방문처리 후 큐에 넣어줌
								visit[tx][ty] = true;
								q.offer(new int[] { tx, ty });
							}
						}
					}
				} else if (map[nx][ny] == '$') {// 문서라면 방문처리후 큐에 넣어주고 횟수 증가
					visit[nx][ny] = true;
					q.offer(new int[] { nx, ny });
					++cnt;
				} else {// 나머지 경우라면 지나갈 수 있는 경우이므로 방문처리후 큐에 넣어줌
					visit[nx][ny] = true;
					q.offer(new int[] { nx, ny });
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= H + 2 || y < 0 || y >= W + 2;
	}
}