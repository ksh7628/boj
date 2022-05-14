package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 15.
 * 
 * 분류: 자료 구조, 문자열, 해시를 사용한 집합과 맵, 트리를 사용한 집합과 맵
 * 난이도: 실버3
 * 소요 시간: 0h 04m
 * 혼자 품: O
 * 풀이: set에 N개의 문자열을 넣은 후, M개의 문자열을 입력받으면서 set에 존재하는 개수만큼 세서 출력.
 * 느낀 점: set 기본 문제.
 */
public class Boj_S3_14425_문자열집합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Set<String> set = new HashSet<>();
		while (N-- > 0) {
			set.add(br.readLine());
		}

		int res = 0;
		while (M-- > 0) {
			if (set.contains(br.readLine())) {
				res++;
			}
		}

		System.out.println(res);
		br.close();
	}
}