import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 19.
 *
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 0 ~ 10번 선수까지 포지션을 선정하여 반복문을 돌린다. 이때, 현재 선수의 j번째 포지션 능력치가 0이거나 이미 j번 포지션이 선점되었다면 다음으로 넘어간다.(가지치기)
 * 2. 그렇지 않다면 j번 포지션을 선점 및 점수를 누적해주고 백트래킹을 위해 j번 포지션 선점을 해제한다.
 * 3. 모든 포지션이 배치완료 되었다면 최댓값을 계속 갱신시킨다.
 * 느낀 점: 
 * 오랜만에 푼 간단한 백트래킹 문제였다. 중간에 가지치기를 해주는데 문제에 대놓고 드러나 있어서 어렵지 않게 푼 문제였다. 
 */
public class Boj_G4_3980_선발명단 {
	static int[][] ability;
	static boolean[] used;
	static int max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int C = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= C; tc++) {
			ability = new int[11][11];
			used = new boolean[11];

			for (int i = 0; i < 11; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 11; j++) {
					ability[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			max = 0;
			setPosition(0, 0);
			sb.append(max).append("\n");
		}

		System.out.println(sb);
		br.close();
	}

	/* 각 포지션 */
	private static void setPosition(int idx, int sum) {
		if (idx == 11) {// 모든 포지션의 배치가 완료되었다면 최댓값 갱신
			max = Math.max(max, sum);
			return;
		}

		for (int j = 0; j < 11; j++) {
			// idx번 선수의 j번 포지션 능력치가 0이거나 이미 j번 포지션이 선정되어 있다면 continue
			if (ability[idx][j] == 0 || used[j]) {
				continue;
			}

			used[j] = true;
			setPosition(idx + 1, sum + ability[idx][j]);
			used[j] = false;// 백트래킹을 위해 현재 선택한 포지션 다시 해제
		}
	}
}