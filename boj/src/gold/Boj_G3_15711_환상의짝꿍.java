package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 4.
 * 
 * 분류: 수학, 정수론, 소수 판정, 에라토스테네스의 체
 * 난이도: 골드3
 * 소요 시간: 1h 30m 이상
 * 혼자 품: X
 * 풀이: 
 * 1. A + B가 최대 4*10^12이므로 제곱근인 200만까지의 소수를 리스트에 저장한다.
 * 2. 골드바흐의 추측(4이상인 모든 짝수는 2개의 소수의 합으로 나타낼 수 있음. 10^18까지 증명)에 따라 4이상의 짝수는 무조건 가능하다.
 * 3. 따라서 4미만의 수는 무조건 불가능하다.
 * 4. 둘 다 아니라면 리스트에 있는 소수들로 A + B가 나누어 떨어지는지 검사한다.
 * 느낀 점: 
 * 처음에는 각 테스트케이스마다 제곱근까지 나누어 떨어지는지 검사하는 방법을 사용했는데 이 경우 T가 최대 500이므로 500 * MAX = 10^9라서
 * 시간초과가 발생했다. 다른 사람의 코드를 참조하게 되었고 에라토스테네스의 체로 소수를 걸러내고 해당 소수로 모듈러 연산을 해야 된다는 것을 알게 되었다.
 */
public class Boj_G3_15711_환상의짝꿍 {
	static List<Integer> nonPrime = new ArrayList<>();
	static final int MAX = 2000001;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		setPrime();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			long A = Long.parseLong(st.nextToken());
			long B = Long.parseLong(st.nextToken());

			if (A + B < 4) {
				sb.append("NO").append("\n");
				continue;
			}

			if ((A + B) % 2 == 0) {
				sb.append("YES").append("\n");
				continue;
			}

			if (isPrime(A + B - 2)) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}

	private static void setPrime() {
		boolean[] check = new boolean[MAX];
		for (int i = 2; i * i < MAX; i++) {
			if (check[i]) {
				continue;
			}

			for (int j = i * i; j < MAX; j += i) {
				check[j] = true;
			}
		}

		for (int i = 2; i < MAX; i++) {
			if (!check[i]) {
				nonPrime.add(i);
			}
		}
	}

	private static boolean isPrime(long num) {
		for (int i = 0; i < nonPrime.size() && (long) nonPrime.get(i) * nonPrime.get(i) <= num; i++) {
			if (num % nonPrime.get(i) == 0) {
				return false;
			}
		}
		return true;
	}
}