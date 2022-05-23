package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 23.
 * 
 * 분류: 자료 구조, 해시를 사용한 집합과 맵, 트리를 사용한 집합과 맵
 * 난이도: 실버3
 * 소요 시간: 0h 06m
 * 혼자 품: O
 * 풀이: 집합 A의 원소와 B의 원소를 key로 해시맵에 하나씩 저장해 나가면서 이미 존재하는 key면 값을 2로 아니라면 1로 저장한 후 값이 1인 경우만 카운팅해준다.
 * 느낀 점: map뿐만 아니라 set으로도 풀 수 있는 문제.
 */
public class Boj_S3_1269_대칭차집합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());

		Map<Integer, Integer> map = new HashMap<>();

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < A; i++) {
			int key = Integer.parseInt(st.nextToken());
			map.put(key, map.getOrDefault(key, 0) + 1);
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < B; i++) {
			int key = Integer.parseInt(st.nextToken());
			map.put(key, map.getOrDefault(key, 0) + 1);
		}

		int res = 0;
		for (int key : map.keySet()) {
			if (map.get(key) == 1) {
				res++;
			}
		}
		
		System.out.println(res);
		br.close();
	}
}