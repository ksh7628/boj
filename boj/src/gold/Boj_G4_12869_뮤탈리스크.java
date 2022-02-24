package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 24.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드4
 * 소요 시간: 0h 51m
 * 혼자 품: O
 * 풀이: top-down방식의 DP로 접근하여 체력을 인덱스로 줘서 최솟값보다 큰 경우이거나, 이미 방문한 경우를 제외하고 재귀를 수행해서 최솟값을 갱신시킨다.
 * 느낀 점: 
 * 보통 DP문제는 bottom-up 방식으로 접근했는데 이 문제는 top-down 방식이 더 좋은 것 같다.
 * 그런데 시간이 다른사람들보다 많이 걸려서 다시 풀어봐야 될 것 같다.
 */
public class Boj_G4_12869_뮤탈리스크 {
	static boolean[][][] dp;
	static int[] hp, damage;
	static int N, res = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		hp = new int[3];
		damage = new int[N];

		int dmg = 9;
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			hp[i] = Integer.parseInt(st.nextToken());
			damage[i] = dmg;
			dmg /= 3;
		}

		// N=3이 아닐 경우 나머지 뮤탈리스크 체력 0으로
		for (int i = N; i < 3; i++) {
			hp[i] = 0;
		}

		// hp 상태를 저장하는 dp배열
		dp = new boolean[61][61][61];
		dfs(hp[0], hp[1], hp[2], 0);

		System.out.println(res);
		br.close();
	}

	/*
	 * top-down dp
	 * 체력이 음수가 되면 0으로 -> 인덱스 범위안으로
	 * 기저 조건 -> 1. 최솟값 벗어남, 2. 이미 공격한 방식
	 */
	private static void dfs(int hp1, int hp2, int hp3, int cnt) {
		hp1 = Math.max(0, hp1);
		hp2 = Math.max(0, hp2);
		hp3 = Math.max(0, hp3);

		// 최솟값 벗어남
		if (cnt >= res) {
			return;
		}

		// 모두 잡음 -> 최솟값 갱신
		if (hp1 == 0 && hp2 == 0 && hp3 == 0) {
			res = Math.min(res, cnt);
			return;
		}
		
		// 이미 공격한 방식
		if (dp[hp1][hp2][hp3]) {
			return;
		}

		// 변수 3개를 정렬
		int max = Math.max(hp1, Math.max(hp2, hp3));
		int min = Math.min(hp1, Math.min(hp2, hp3));
		int mid = hp1 + hp2 + hp3 - max - min;

		// 내림차순으로
		hp1 = max;
		hp2 = mid;
		hp3 = min;

		// 방문처리 후 재귀
		dp[hp1][hp2][hp3] = true;
		dfs(hp1 - 9, hp2 - 3, hp3 - 1, cnt + 1);
		dfs(hp1 - 9, hp2 - 1, hp3 - 3, cnt + 1);
		dfs(hp1 - 3, hp2 - 9, hp3 - 1, cnt + 1);
		dfs(hp1 - 3, hp2 - 1, hp3 - 9, cnt + 1);
		dfs(hp1 - 1, hp2 - 9, hp3 - 3, cnt + 1);
		dfs(hp1 - 1, hp2 - 3, hp3 - 9, cnt + 1);
	}
}