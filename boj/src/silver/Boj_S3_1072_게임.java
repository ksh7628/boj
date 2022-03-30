package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 30.
 * 
 * 분류: 수학, 이분 탐색
 * 난이도: 실버3
 * 소요 시간: 0h 25m
 * 혼자 품: O
 * 풀이: 이분 탐색을 통해 승률이 변하는 최소 게임수를 구한다.
 * 느낀 점: 
 * Y/X*100으로 하니 틀렸는데 Y*100/X로 고치니까 통과되었다.
 * -> double a = 0.58, a * 100 -> 0.579999... (int) (a*100) -> 57
 * 추가로 부등식을 세워서 이분 탐색 없이 바로 풀 수 있다는 것을 알게 되었다.
 */
public class Boj_S3_1072_게임 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int X = Integer.parseInt(st.nextToken());
		int Y = Integer.parseInt(st.nextToken());
		int target = (int) (1.0 * Y * 100 / X);

		int left = 1, right = 1000000000;
		while (left <= right) {
			int middle = (left + right) / 2;
			int per = (int) (1.0 * (Y + middle) * 100 / (X + middle));

			if (per <= target) {
				left = middle + 1;
			} else {
				right = middle - 1;
			}
		}

		System.out.println(left > 1000000000 ? -1 : left);
		br.close();
	}
}