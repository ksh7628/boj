package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 7. 10.
 * 
 * 분류: 자료 구조, 문자열, 해시를 사용한 집합과 맵, 트리를 사용한 집합과 맵
 * 난이도: 실버3
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: 2중 for문을 사용해서 모든 부분 문자열을 구해가면서 set에 저장한 후, set의 크기를 출력한다.
 * 느낀 점: 입력받는 문자열 길이가 최대 1000이기 때문에 2중 for문으로도 해결 가능했다.
 */
public class Boj_S3_11478_서로다른부분문자열의개수 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();

		Set<String> set = new HashSet<>();

		for (int i = 0; i < str.length(); i++) {
			for (int j = i + 1; j <= str.length(); j++) {
				set.add(str.substring(i, j));
			}
		}
		
		System.out.println(set.size());
		br.close();
	}
}