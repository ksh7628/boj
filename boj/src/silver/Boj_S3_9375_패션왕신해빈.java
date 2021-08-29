package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 18.
 *
 * 분류: 수학, 자료 구조, 문자열, 해시를 사용한 집합과 맵
 * 난이도: 실버3
 * 혼자 품: X
 * 풀이: 
 * 1. 의상 종류를 key로 가지고 개수를 value로 가지는 hashMap에 입력값들을 저장한다.
 * 2. 경우의 수 = (1번째 의상의 개수 + 1) * (2번째 의상의 개수 + 1) * ... * (n번째 의상의 개수 + 1) -1을 이용하여 정답을 구한다.
 * 느낀 점: 
 * 경우의 수를 구하는 공식을 스스로 생각하지 못해서 다른 사람들의 풀이법을 보니 위의 공식이 따로 있다는 것을 알게 되었다.
 * 또한 해쉬맵에서 모든 값을 순회하는 values() 메서드를 처음 사용하게 되었다.
 */
public class Boj_S3_9375_패션왕신해빈 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			int n = Integer.parseInt(br.readLine());
			Map<String, Integer> hm = new HashMap<>();
			
			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				st.nextToken();// 의상의 이름은 저장할 필요가 없다
				String key = st.nextToken();
				if (!hm.containsKey(key)) {
					hm.put(key, 1);
				} else {// 이미 해당 종류의 의상이 존재한다면 갯수 더해줌
					hm.put(key, hm.get(key) + 1);
				}
			}

			// 경우의 수 = (A종류 의상의 개수 + 1)*(B종류 의상의 개수 + 1)* ... - 1
			int res = 1;
			for (int value : hm.values()) {
				res *= value + 1;
			}
			
			sb.append(res - 1).append("\n");
		}
		
		System.out.println(sb);
		br.close();
	}
}