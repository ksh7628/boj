package gold;
import java.io.*;
import java.util.regex.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 12.
 *
 * 분류: 문자열, 정규 표현식
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: https://www.acmicpc.net/problem/1013(Contact) 문제랑 완전 똑같아서 풀이도 똑같이 했다.
 * 느낀 점: 정규 표현식에 익숙해져 가는 것 같다.
 */
public class Boj_G5_2671_잠수함식별 {
	static final String pattern = "(100+1+|01)+";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String str = br.readLine();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);

		if (m.matches()) {
			bw.write("SUBMARINE");
		} else {
			bw.write("NOISE");
		}

		bw.flush();
		bw.close();
		br.close();
	}
}