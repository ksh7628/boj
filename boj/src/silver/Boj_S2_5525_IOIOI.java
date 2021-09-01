package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 30.
 *
 * 분류: 문자열
 * 난이도: 실버2
 * 소요 시간: 0h 30m
 * 혼자 품: X
 * 풀이:
 * 1. P(N)의 패턴이 최초로 나타나기 시작할 떄 cnt를 증가시키면서 P(cnt)가 P(N)이랑 같아지면 해당 문자열이 발견된 것이므로 res, i를 하나 증가시키고 cnt를 하나 감소시킨다.
 * 2. 패턴이 아니라면 cnt를 0으로 초기화해준다.
 * 느낀 점: N이 최대 100만이므로 무분별한 substring을 사용하면 제한 시간 내에 돌아가지 않으므로 O(N)에 수행될 수 있도록 로직을 이렇게 표현할 수 있다는 것을 알게 되었다.
 */
public class Boj_S2_5525_IOIOI {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		String str = br.readLine();

		int res = 0;
		int cnt = 0;
		for (int i = 1; i < M - 1; i++) {
			if (str.charAt(i - 1) == 'I' && str.charAt(i) == 'O' && str.charAt(i + 1) == 'I') {// 최초 패턴 발견
				cnt++;
				if (cnt == N) {// P(N) 발견
					cnt--;
					res++;
				}
				i++;// 패턴이 I + OI * cnt이므로 i를 한번 더 더해줌
			} else {// 발견하지 못했다면 cnt 초기화
				cnt = 0;
			}
		}

		System.out.println(res);
		br.close();
	}
}