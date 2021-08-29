package gold;
import java.io.*;
import java.util.regex.Pattern;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 12.
 *
 * 분류: 문자열, 정규 표현식
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: 문제에서 정규 표현식 패턴을 주기 때문에 해당 패턴에 일치하는 문자열이면 YES, 아니라면 NO를 출력한다.
 * 느낀 점: 첫 정규 표현식 문제를 풀어보았는데 패턴이 주어지기 때문에 regex에 있는 클래스와 메서드를 활용하여 쉽게 풀었다.
 */
public class Boj_G5_1013_Contact {
	static final String pattern = "(100+1+|01)+";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= T; tc++) {
			String str = br.readLine();
			if (Pattern.matches(pattern, str)) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}
}