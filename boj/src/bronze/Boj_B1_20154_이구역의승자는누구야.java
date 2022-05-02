package bronze;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 2.
 * 
 * 분류: 수학, 문자열
 * 난이도: 브론즈1
 * 소요 시간: 0h 15m
 * 혼자 품: O
 * 풀이: 현재 단계에서 남은 숫자가 홀수라면 숫자의 개수 / 2 + 1개로 진행하고 짝수라면 숫자의 개수 / 2로 진행하면서 하나가 남을 때까지 반복한다.
 * 느낀 점: 브론즈 문제라고 얕봤다가 문제를 제대로 읽지 않아서 2번이나 틀리게 되었다. 그리고 MOD 10 연산을 하는데 짝수, 홀수만 판단하면 되므로 굳이 해주지 않아도 됨.
 */
public class Boj_B1_20154_이구역의승자는누구야 {
	static final int[] cnt = { 3, 2, 1, 2, 3, 3, 3, 3, 1, 1, 3, 1, 3, 3, 1, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int[] num = new int[str.length()];

		for (int i = 0; i < num.length; i++) {
			num[i] = cnt[str.charAt(i) - 'A'];
		}

		int e = num.length;
		while (e > 1) {
			int idx = 0;
			for (int i = 0; i < e - 1; i += 2) {
				num[idx++] = num[i] + num[i + 1];
			}

			if (e % 2 == 1) {
				num[idx] = num[e - 1];
				e = e / 2 + 1;
			} else {
				e /= 2;
			}
		}

		System.out.println(num[0] % 2 == 1 ? "I'm a winner!" : "You're the winner?");
		br.close();
	}
}