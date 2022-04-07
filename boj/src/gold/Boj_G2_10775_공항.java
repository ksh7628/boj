package gold;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 7.
 * 
 * 분류: 자료 구조, 그리디 알고리즘, 분리 집합
 * 난이도: 골드2
 * 소요 시간: 1h 30m 이상
 * 혼자 품: X
 * 풀이: 유니온 파인드를 이용하여 find(g)번째 게이트와 find(g - 1)번째 게이트를 union 연산해주고 find(g) 값이 0일 때 종료.
 * 느낀 점: find 연산을 따로 활용할 생각을 하지 못해서 다른 사람의 코드를 참조하게 되었다.
 */
public class Boj_G2_10775_공항 {
	static int G;
	static int[] p;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());

		init();
		int res = 0;
		for (int i = 0; i < P; i++) {
			int g = Integer.parseInt(br.readLine());
			int nxtGate = find(p[g]);

			if (nxtGate < 1) {
				break;
			}

			union(nxtGate - 1, nxtGate);
			res++;
		}

		System.out.println(res);
	}

	private static void init() {
		p = new int[G + 1];
		for (int i = 1; i <= G; i++) {
			p[i] = i;
		}
	}

	private static int find(int x) {
		if (p[x] == x) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static void union(int x, int y) {
		int a = find(x), b = find(y);
		if (a != b) {
			p[b] = a;
		}
	}
}