package gold;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 백트래킹
 * 난이도: 골드4
 * 소요 시간: time over
 * 혼자 품: X
 * 풀이: 
 * 1. 좋은수열은 무조건 1부터 시작하기 때문에 처음에 "1"을 넣어준 후 1~3까지 하나씩 붙여준다.
 * 2. 1에서 만들어진 수열을 뒤에서부터 두 개의 부분수열로 나누어 부분수열의 크기를 1~str.length()/2까지 선언해나가며 두 부분수열이 같은지 다른지를 비교한다.
 * 3. 같다면 나쁜 수열이므로 다음 수를 붙여보고 다르다면 좋은수열이므로 그 수열을 재귀적으로 계속 붙여나간 후 크기가 N이 되면 좋은수열을 출력한다.
 * 4. 최초에 출력된 수열이 가장 작은 좋은수열이므로 출력 후 종료 플래그 변수를 true로 하여 나머지 수열들은 출력하지 않는다. 
 * 느낀 점: 
 * 처음에 풀 땐 중간에서부터 비교를 해줘서 수열이 조금만 길어져도 좋은수열 판단을 못하게 되었다.
 * 올바른 로직을 찾지 못해 다른 사람의 코드를 참조하게 되었는데 수를 하나씩 붙여나가면 앞쪽은 비교할 필요가 없기 때문에 뒷쪽 부분수열 두개만 비교해주는 것이 올바른 로직인걸 알게 되었다.
 * 백트래킹 자체는 익숙해졌으나 문제에서 요구하는 사항을 구현하는게 쉽지 않았다.
 */
public class Boj_G4_2661_좋은수열 {
	static int N;
	static boolean flag;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		goodSequence("1");
		br.close();
	}

	// N자리 좋은 수열을 만들게 되면 출력
	private static void goodSequence(String str) {
		if (flag) {
			return;
		}

		if (str.length() == N) {
			flag = true;
			System.out.println(str);
			return;
		}

		for (int i = 1; i <= 3; i++) {
			if (isPossible(str + i)) {// 좋은 수열을 만들 수 있다면 추가
				goodSequence(str + i);
			}
		}
	}

	// 매개변수로 받은 문자열이 좋은 수열인지 체크
	private static boolean isPossible(String str) {
		int len = str.length();
		for (int i = 1; i <= len / 2; i++) {
			// 젤 뒷자리 부분 수열 2개를 크기 1 ~ N/2까지 비교
			if (str.substring(len - 2 * i, len - i).equals(str.substring(len - i, len))) {
				return false;
			}
		}
		return true;
	}
}