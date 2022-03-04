package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 4.
 * 
 * 분류: 구현
 * 난이도: 실버4
 * 소요 시간: 0h 40m
 * 혼자 품: O
 * 풀이: 
 * 5장의 카드로 포커 족보를 구현하는 문제로 카드가 5장이라는 특성을 이용해서 set에는 색깔, map에는 각 숫자가 몇 장인지 구성해서 품.
 * 느낀 점: 직관적인 문제인데도 불구하고 시간이 좀 걸렸다는 점에서 코드로 구현하는 힘을 계속 길러야 겠다고 느낌.
 */
public class Boj_S4_2621_카드게임 {
	static class Card implements Comparable<Card> {
		char color;
		int num;

		public Card(char color, int num) {
			super();
			this.color = color;
			this.num = num;
		}

		@Override
		public int compareTo(Card o) {
			return -Integer.compare(this.num, o.num);
		}
	}

	static Set<Character> set = new HashSet<>();
	static Map<Integer, Integer> map = new HashMap<>();
	static Card[] card;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		card = new Card[5];

		for (int i = 0; i < 5; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			char color = st.nextToken().charAt(0);
			int num = Integer.parseInt(st.nextToken());
			card[i] = new Card(color, num);
			set.add(color);
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		Arrays.sort(card);

		for (Card c : card) {
			if (map.containsKey(c.num)) {
				map.put(c.num, map.get(c.num) + 1);
			} else {
				map.put(c.num, 1);
			}
		}

		if (isFlush() && isStraight()) {
			return card[0].num + 900;
		} else if (isFourCard()) {
			return card[1].num + 800;
		} else if (isFullHouse()) {
			if (card[0].num == card[2].num) {
				return card[2].num * 10 + card[4].num + 700;
			}
			return card[2].num * 10 + card[0].num + 700;

		} else if (isFlush()) {
			return card[0].num + 600;
		} else if (isStraight()) {
			return card[0].num + 500;
		} else if (isTriple()) {
			return card[2].num + 400;
		} else if (isTwoPair()) {
			return card[1].num * 10 + card[3].num + 300;
		} else if (map.size() == 4) {
			for (Card c : card) {
				if (map.get(c.num) == 2) {
					return c.num + 200;
				}
			}
		}

		return card[0].num + 100;
	}

	private static boolean isFourCard() {
		if (map.get(card[1].num) >= 4) {
			return true;
		}
		return false;
	}

	private static boolean isFullHouse() {
		if (map.size() == 2 && map.get(card[2].num) == 3) {
			return true;
		}
		return false;
	}

	private static boolean isFlush() {
		if (set.size() == 1) {
			return true;
		}
		return false;
	}

	private static boolean isStraight() {
		for (int i = 0; i < 4; i++) {
			if (card[i].num - card[i + 1].num != 1) {
				return false;
			}
		}
		return true;
	}

	private static boolean isTriple() {
		if (map.get(card[2].num) == 3) {
			return true;
		}
		return false;
	}

	private static boolean isTwoPair() {
		if (map.get(card[1].num) == 2 && map.get(card[3].num) == 2) {
			return true;
		}
		return false;
	}
}