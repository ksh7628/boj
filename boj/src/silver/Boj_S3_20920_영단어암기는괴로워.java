package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 3.
 * 
 * 분류: 자료 구조, 문자열, 정렬, 트리를 사용한 집합과 맵
 * 난이도: 실버3
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: map을 사용해서 길이가 M 이상인 단어의 개수를 센 후, Word 객체로 list에 저장한 후 정렬해서 출력한다.
 * 느낀 점: 맵 + 정렬 문제.
 */
public class Boj_S3_20920_영단어암기는괴로워 {
	static class Word implements Comparable<Word> {
		String s;
		int cnt;

		public Word(String s, int cnt) {
			super();
			this.s = s;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Word o) {
			if (this.cnt == o.cnt) {
				if (this.s.length() == o.s.length()) {
					return this.s.compareTo(o.s);
				}
				return -Integer.compare(this.s.length(), o.s.length());
			}
			return -Integer.compare(this.cnt, o.cnt);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			String str = br.readLine();

			if (str.length() >= M) {
				map.put(str, map.getOrDefault(str, 0) + 1);
			}
		}

		List<Word> list = new ArrayList<>();
		for (String key : map.keySet()) {
			list.add(new Word(key, map.get(key)));
		}

		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (Word w : list) {
			sb.append(w.s).append("\n");
		}
		
		System.out.print(sb);
		br.close();
	}
}