package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 26.
 * 
 * 분류: 자료 구조, 문자열, 트리를 사용한 집합과 맵, 해시를 사용한 집합과 맵
 * 난이도: 실버1
 * 소요 시간: 0h 12m
 * 혼자 품: O
 * 풀이: 입력이 끝날때 까지 map에 이름과 등장 빈도를 저장하고 key값을 오름차순으로 정렬한 후 백분율을 소수점 4째자리까지 출력한다.
 * 느낀 점: keySet()을 list에 저장해서 정렬하는 방법과 toArray()를 사용해서 배열로 정렬하는 방법을 모두 알게 되었다.
 */
public class Boj_S1_4358_생태학 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Map<String, Integer> map = new HashMap<>();
		String input = "";
		int total = 0;

		while ((input = br.readLine()) != null) {
			total++;

			if (!map.containsKey(input)) {
				map.put(input, 1);
			} else {
				map.put(input, map.get(input) + 1);
			}
		}

		StringBuilder sb = new StringBuilder();
		Object[] name = map.keySet().toArray();
		Arrays.sort(name);

		for (Object key : name) {
			sb.append(key).append(" ").append(String.format("%.4f", 1.0 * map.get(key) / total * 100)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}