package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 20.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 실버1
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 하나씩 깨트리고 해당 재귀를 모두 수행했다면 다시 복구해줘서 다음 탐색에 영향을 주지 않도록 해서 품.
 * 느낀 점: 조건을 하나 놓치고 복구할 때 더해주는게 아니라 대입을 해서 오랫동안 고민한 문제였다. 문제를 잘읽고 코드 복사할 때 오탈자를 조심하자.
 */
public class Boj_S1_16987_계란으로계란치기 {
	static class Egg {
		int hp, weight;

		public Egg(int hp, int weight) {
			super();
			this.hp = hp;
			this.weight = weight;
		}
	}

	static Egg[] egg;
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		egg = new Egg[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int hp = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			egg[i] = new Egg(hp, weight);
		}

		breakEgg(0, 0);
		System.out.println(res);
		br.close();
	}

	private static void breakEgg(int start, int cnt) {
		if (start == N) {
			res = Math.max(res, cnt);
			return;
		}

		// 들고있는 계란이 깨졌거나 깨지지 않은 다른 계란이 없음
		if (egg[start].hp <= 0 || cnt == N - 1) {
			breakEgg(start + 1, cnt);
			return;
		}

		int preCnt = cnt;
		for (int i = 0; i < N; i++) {
			if (i == start || egg[i].hp <= 0) {
				continue;
			}

			egg[start].hp -= egg[i].weight;
			egg[i].hp -= egg[start].weight;

			// 들고있는 계란이 깨짐
			if (egg[start].hp <= 0) {
				cnt++;
			}
			// 친 계란이 깨짐
			if (egg[i].hp <= 0) {
				cnt++;
			}

			breakEgg(start + 1, cnt);
			// 복구
			egg[start].hp += egg[i].weight;
			egg[i].hp += egg[start].weight;
			cnt = preCnt;
		}
	}
}