package silver;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 7.
 * 
 * 분류: 문자열, 정렬, 파싱
 * 난이도: 실버4
 * 소요 시간: 0h 27m
 * 혼자 품: O
 * 풀이: 
 * 1. 정규 표현식을 사용. 문자열에 있는 모든 숫자를 찾은 후, 0으로 시작하는 숫자들을 모두 제거 하고 전부 0으로 이루어진 숫자는 0을, 아니라면 현재 숫자를 리스트에 넣는다.
 * 2. 길이가 같으면 문자열 오름차순, 다르다면 길이의 오름차순으로 정렬한다.
 * 느낀 점: 정규 표현식을 사용한다는 생각이 들면 어렵지 않은 문제. 숫자의 범위에 주의해야 되므로 문자열로 풀게 됨.
 */
public class Boj_S4_2870_수학숙제 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		List<String> list = new ArrayList<>();
		Pattern pattern = Pattern.compile("[\\d]+");

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				String num = matcher.group().replaceAll("^0+", "");
				list.add(num.length() == 0 ? "0" : num);
			}
		}

		Collections.sort(list, new Comparator<>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() == o2.length()) {
					return o1.compareTo(o2);
				}
				return o1.length() - o2.length();
			}
		});

		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}