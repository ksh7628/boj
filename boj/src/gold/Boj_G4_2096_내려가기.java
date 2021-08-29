package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 다이나믹 프로그래밍, 슬라이딩 윈도우
 * 난이도: 골드4
 * 소요 시간: time over
 * 혼자 품: X
 * 풀이: 원본 배열과 이전값 배열을 각각 선언하여 이전값 배열에 입력값을 받은 후에 내려갈 수 있는 조건들을 비교하여 최댓값, 최솟값을 각각 구한 후 원본 배열에 복사하는 식으로 풀이하였다.
 * 느낀 점: 슬라이딩 윈도우 기법이 아직 익숙하지 않아서 다른 사람의 코드를 보고 이해하게 되었다.
 */
public class Boj_G4_2096_내려가기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[] dpMax = new int[3];
		int[] dpMin = new int[3];
		int[] preMax = new int[3];
		int[] preMin = new int[3];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 3; j++) {
				int num = Integer.parseInt(st.nextToken());
				preMax[j] = preMin[j] = num;// 현재 위치의 번호
				preMax[j] += Math.max(dpMax[1], j == 1 ? Math.max(dpMax[0], dpMax[2]) : dpMax[j]);
				preMin[j] += Math.min(dpMin[1], j == 1 ? Math.min(dpMin[0], dpMin[2]) : dpMin[j]);
			}

			// 구한 값들을 원본 배열에 복사
			System.arraycopy(preMax, 0, dpMax, 0, 3);
			System.arraycopy(preMin, 0, dpMin, 0, 3);
		}

		int max = Math.max(dpMax[0], Math.max(dpMax[1], dpMax[2]));
		int min = Math.min(dpMin[0], Math.min(dpMin[1], dpMin[2]));
		System.out.println(max + " " + min);
		br.close();
	}
}