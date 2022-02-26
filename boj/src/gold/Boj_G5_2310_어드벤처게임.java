package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 26.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 골드5
 * 소요 시간: 0h 42m
 * 혼자 품: X
 * 풀이: Room 객체 배열에 방 타입, 금액, 다음 방 번호들의 리스트를 저장하고 dfs로 n번방까지 도달 가능한지 체크한다.
 * 느낀 점: 
 * 처음에 지문을 잘못 이해해서 계속 틀리다가 다른 사람의 코드를 참조하게 되었다.
 * 주관적으로 느끼는 거지만 https://www.acmicpc.net/board/view/69872 의 글을 읽어보니 똑같은 생각이 든다.
 */
public class Boj_G5_2310_어드벤처게임 {
	static class Room {
		char type;
		int money;
		List<Integer> list;

		public Room(char type, int money, List<Integer> list) {
			super();
			this.type = type;
			this.money = money;
			this.list = list;
		}
	}

	static Room[] room;
	static boolean[] visit;
	static int n;
	static boolean isArrive;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		while (true) {
			n = Integer.parseInt(br.readLine());

			if (n == 0) {
				break;
			}

			room = new Room[n + 1];
			visit = new boolean[n + 1];

			for (int i = 1; i <= n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				char type = st.nextToken().charAt(0);
				int money = Integer.parseInt(st.nextToken());
				List<Integer> list = new ArrayList<>();

				while (true) {
					int idx = Integer.parseInt(st.nextToken());

					if (idx == 0) {
						break;
					}

					list.add(idx);
				}

				room[i] = new Room(type, money, list);
			}

			sb.append(solution()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 각 테스트케이스마다 n번 방에 도달할 수 있는지 dfs를 실행 */
	private static String solution() {
		isArrive = false;
		dfs(1, 0);
		return isArrive ? "Yes" : "No";
	}

	/* 입장시 먼저 방 타입과 돈 상태를 체크 후 다음 방에 들어간다 */
	private static void dfs(int cur, int money) {
		// 레프리콘
		// -> 돈이 부족하면 갱신, 아니면 넘어감
		if (room[cur].type == 'L' && money < room[cur].money) {
			money = room[cur].money;
		} else if (room[cur].type == 'T') {
			// 트롤 -> 통행료 지불 불가능하면 못 지나감, 가능하면 삭감한다
			if (money < room[cur].money) {
				return;
			}
			money -= room[cur].money;
		}

		// 도착
		if (cur == n) {
			isArrive = true;
			return;
		}

		visit[cur] = true;
		for (int nxt : room[cur].list) {
			if (!visit[nxt]) {
				dfs(nxt, money);
			}
		}
	}
}