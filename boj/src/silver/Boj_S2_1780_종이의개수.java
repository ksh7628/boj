package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 29.
 *
 * 분류: 분할 정복, 재귀
 * 난이도: 실버2
 * 혼자 품: O
 * 풀이: 
 * 1. 종이의 상태를 체크하여 안잘라도 된다면 시작 수를 cnt배열에 인덱스+1에 해당하는 값을 1 증가 시킨다.
 * 2. 잘라야 한다면 9분할을 하고 1~2번을 반복한다.
 * 느낀 점: 오랜만에 푼 분할 정복 문제였는데 쿼드트리(https://www.acmicpc.net/problem/1992) 문제랑 비슷해서 어렵지 않게 풀었다.
 */
public class Boj_S2_1780_종이의개수 {
	static int[][] paper;
	static int[] cnt;// -1, 0, 1의 개수를 저장
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		paper = new int[N][N];
		cnt = new int[3];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				paper[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		divide(N, 0, 0);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			sb.append(cnt[i]).append("\n");
		}
		System.out.println(sb);
		br.close();
	}

	/* 종이를 잘라야 한다면 9등분으로 나눔 */
	private static void divide(int size, int row, int col) {
		if (countCheck(size, row, col)) {// 종이를 잘라야 되는지 체크
			return;
		}

		int cutSize = size / 3;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				divide(cutSize, row + i * cutSize, col + j * cutSize);
			}
		}
	}

	/* 종이를 잘라야 한다면 false, 안잘라도 된다면 해당 숫자를 더해주고 true 반환 */
	private static boolean countCheck(int size, int row, int col) {
		int num = paper[row][col];
		for (int i = row; i < row + size; i++) {
			for (int j = col; j < col + size; j++) {
				if (num != paper[i][j]) {
					return false;
				}
			}
		}

		cnt[num + 1]++;
		return true;
	}
}