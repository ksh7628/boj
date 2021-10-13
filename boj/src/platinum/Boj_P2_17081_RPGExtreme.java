package platinum;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 9. 19.
 *
 * 분류: 구현, 시뮬레이션
 * 난이도: 플래티넘2
 * 소요 시간: 4h 이상
 * 혼자 품: O
 * 풀이: 장신구 아이템만 Set으로 관리하여 중복된 장신구를 습득하지 않도록 처리하였고 나머지 구현은 지문 그대로 따라했다.
 * 느낀 점: 
 * 객체 지향 프로그래밍 연습을 하려고 풀었는데 막상 풀다보니 알고리즘 문제를 풀듯이 쫓기며 풀게 되었다.
 * 다른 부분들은 구현이 잘 되었는데 장신구 아이템인 HR과 EX를 if 2개가 아닌 if와 else if로 코드를 짜서 디버깅하는데 굉장히 오래 걸렸다.
 * 구현 및 시뮬레이션 문제는 정말 지문을 잘 읽고 주석이나 메모를 철저하게 해야 완벽하게 풀 수 있다는 것을 다시 한번 깨달았다.
 */
public class Boj_P2_17081_RPGExtreme {
	static class Monster {
		int mw, ma, mh, me;

		public Monster(int mw, int ma, int mh, int me) {
			this.mw = mw;
			this.ma = ma;
			this.mh = mh;
			this.me = me;
		}
	}

	static class Item {
		char type;
		String info;

		public Item(char type, String info) {
			this.type = type;
			this.info = info;
		}
	}

	static char[][] map;
	static String[][] ms;
	static Item[][] box;
	static char[] moving;
	static HashSet<String> inventory = new HashSet<>();
	static HashMap<String, Monster> monster = new HashMap<>();
	static int N, M, startX, startY, playerX, playerY, T;
	static int HP = 20, ATT = 2, DEF = 2, LV = 1, EXP = 0;
	static int curHp = HP, weapon, armor, curExp, reqExp = 5;
	static boolean isLive = true, bossDead;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		ms = new String[N][M];
		box = new Item[N][M];

		int mopsCnt = 0, itemCnt = 0;
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j);
				if (map[i][j] == '@') {
					map[i][j] = '.';
					playerX = startX = i;
					playerY = startY = j;
				} else if (map[i][j] == '&' || map[i][j] == 'M') {
					mopsCnt++;
				} else if (map[i][j] == 'B') {
					itemCnt++;
				}
			}
		}

		moving = br.readLine().toCharArray();

		for (int i = 0; i < mopsCnt; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			String name = st.nextToken();
			ms[x][y] = name;

			int w = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			monster.put(name, new Monster(w, a, h, e));
		}

		for (int i = 0; i < itemCnt; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			char type = st.nextToken().charAt(0);
			String info = st.nextToken();
			box[x][y] = new Item(type, info);
		}

		process();
		br.close();
	}

	private static void process() throws Exception {
		int len = moving.length;
		for (int i = 0; i < len; i++) {
			T++;
			move(moving[i]);
			explore();
			if (isEnd()) {
				break;
			}
		}

		if (isLive) {
			map[playerX][playerY] = '@';
		}

		print();
	}

	private static void move(char cmd) {
		int nx = playerX, ny = playerY;
		switch (cmd) {
		case 'L':
			ny--;
			break;
		case 'R':
			ny++;
			break;
		case 'U':
			nx--;
			break;
		case 'D':
			nx++;
			break;
		}

		if (check(nx, ny) && map[nx][ny] != '#') {
			playerX = nx;
			playerY = ny;
		}
	}

	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	private static void explore() {
		switch (map[playerX][playerY]) {
		case 'B':
			getItem();
			break;
		case '^':
			getThorn();
			break;
		case '&':
		case 'M':
			hunt();
			break;
		}
	}

	private static void getItem() {
		Item item = box[playerX][playerY];
		switch (item.type) {
		case 'W':
			weapon = Integer.parseInt(item.info);
			break;
		case 'A':
			armor = Integer.parseInt(item.info);
			break;
		case 'O':
			if (inventory.size() < 4) {
				inventory.add(item.info);
			}
			break;
		}

		map[playerX][playerY] = '.';
	}

	private static void getThorn() {
		if (inventory.contains("DX")) {
			curHp--;
		} else {
			curHp -= 5;
		}

		if (curHp <= 0) {
			playerDied();
		}
	}

	private static void hunt() {
		if (map[playerX][playerY] == 'M' && inventory.contains("HU")) {
			curHp = HP;
		}

		Monster m = monster.get(ms[playerX][playerY]);
		int tmpHp = m.mh;
		int playerDmg = 0, mosterDmg = 0;
		if (inventory.contains("CO")) {
			if (inventory.contains("DX")) {
				playerDmg = (ATT + weapon) * 3 - m.ma;
			} else {
				playerDmg = (ATT + weapon) * 2 - m.ma;
			}
		} else {
			playerDmg = ATT + weapon - m.ma;
		}
		playerDmg = playerDmg > 0 ? playerDmg : 1;
		m.mh -= playerDmg;
		if (m.mh <= 0) {
			monsterKilled(m);
			return;
		}

		if (map[playerX][playerY] == '&' || !inventory.contains("HU")) {
			mosterDmg = m.mw - (DEF + armor);
			mosterDmg = mosterDmg > 0 ? mosterDmg : 1;
			curHp -= mosterDmg;

			if (curHp <= 0) {
				m.mh = tmpHp;
				playerDied();
				return;
			}
		}

		while (true) {
			playerDmg = ATT + weapon - m.ma;
			playerDmg = playerDmg > 0 ? playerDmg : 1;
			m.mh -= playerDmg;

			if (m.mh <= 0) {
				monsterKilled(m);
				break;
			}

			mosterDmg = m.mw - (DEF + armor);
			mosterDmg = mosterDmg > 0 ? mosterDmg : 1;
			curHp -= mosterDmg;

			if (curHp <= 0) {
				m.mh = tmpHp;
				playerDied();
				break;
			}
		}
	}

	private static void playerDied() {
		curHp = 0;
		if (inventory.contains("RE")) {
			curHp = HP;
			playerX = startX;
			playerY = startY;
			inventory.remove("RE");
		} else {
			isLive = false;
		}
	}

	private static void monsterKilled(Monster m) {
		if (map[playerX][playerY] == 'M') {
			bossDead = true;
		}
		map[playerX][playerY] = '.';
		int exp = m.me;

		if (inventory.contains("HR")) {
			curHp += 3;
			curHp = curHp > HP ? HP : curHp;
		}
		if (inventory.contains("EX")) {
			exp += m.me / 5;
		}

		curExp += exp;
		if (curExp >= reqExp) {
			LV++;
			HP += 5;
			ATT += 2;
			DEF += 2;
			curHp = HP;
			curExp = 0;
			reqExp = LV * 5;
		}
	}

	private static boolean isEnd() {
		if (!isLive || bossDead) {
			return true;
		}
		return false;
	}

	private static void print() throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		sb.append("Passed Turns : ").append(T).append("\n");
		sb.append("LV : ").append(LV).append("\n");
		sb.append("HP : ").append(curHp).append("/").append(HP).append("\n");
		sb.append("ATT : ").append(ATT).append("+").append(weapon).append("\n");
		sb.append("DEF : ").append(DEF).append("+").append(armor).append("\n");
		sb.append("EXP : ").append(curExp).append("/").append(reqExp).append("\n");

		if (bossDead) {
			sb.append("YOU WIN!");
		} else if (!isLive) {
			sb.append("YOU HAVE BEEN KILLED BY ");
			if (map[playerX][playerY] == '^') {
				sb.append("SPIKE TRAP");
			} else {
				sb.append(ms[playerX][playerY]);
			}
			sb.append("..");
		} else {
			sb.append("Press any key to continue.");
		}

		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
}