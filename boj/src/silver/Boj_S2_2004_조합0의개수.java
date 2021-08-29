package silver;
import java.io.*;
import java.util.StringTokenizer;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 18.
 *
 * 분류: 수학, 정수론
 * 난이도: 실버2
 * 혼자 품: X
 * 풀이: 
 * 1. nCm = n!/(n-m)!*m! 이고 num의 개수: n!의 num의 개수 - (n-m)!의 num의 개수 - m!의 num의 개수 이므로 각각 2의 개수와 5의 개수를 구해준다.
 * 2. 0의 개수를 구해야 되므로 1에서 구한 2의 개수와 5의 개수 중 적은 값이 0의 개수가 된다.
 * 느낀 점: 
 * 입력값이 최대 20억까지 되므로 일일이 소인수분해를 하면 시간초과가 뜨기 때문에 고민을 하다가 다른 사람의 풀이를 보니 getNum2, getNum5 메서드처럼 바로 구하는
 * 로직이 있다는 것을 알게 되었다. 항상 수학문제가 그렇지만 아이디어를 떠올리는 것이 쉽지 않은데 수학적 사고력 또한 키워나가야 된다는 것을 알게 된 문제였다.
 */
public class Boj_S2_2004_조합0의개수 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		// nCm = n!/(n-m)!*m!
		// 2*5=10 이므로 2의 개수와 5의 개수중 작은것을 구하면 된다.
		int result = Math.min(getNum2(n) - getNum2(n - m) - getNum2(m), getNum5(n) - getNum5(n - m) - getNum5(m));
		System.out.println(result);
		br.close();
	}

	/* 2의 개수를 구함 */
	private static int getNum2(int num) {
		int res = 0;
		while (num > 0) {
			res += num / 2;
			num /= 2;
		}
		return res;
	}

	/* 5의 개수를 구함 */
	private static int getNum5(int num) {
		int res = 0;
		while (num > 0) {
			res += num / 5;
			num /= 5;
		}
		return res;
	}
}