package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 31.
 * 
 * 분류: 브루트포스 알고리즘, 비트마스킹, 백트래킹
 * 난이도: 골드4
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: nCk의 조합을 뽑아서 읽을 수 있는지 검사한 후 카운팅해서 최댓값을 갱신한다.
 * 느낀 점: 
 * 처음에는 n*len(n-8)Ck로 접근해서 시간초과가 났다. 다른 사람의 코드를 보니 위 풀이대로 접근해야 시간초과가 안난다는 것을 깨닫게 되었다.
 */
public class Boj_G4_1062_가르침 {
	static String[] word;
	static int N, K, res;
	static char c;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		word = new String[N];
		for (int i = 0; i < N; i++) {
			word[i] = br.readLine();
		}

		// 모든 단어는 anta로 시작해서 tica로 끝난다
		// -> 모든 단어는 a, c, i, n, t를 포함한다.
		// -> 포함하는 알파벳을 비트로 표현
		c = 'a';
		int bit = 1 << 'a' - c;
		bit |= 1 << 'c' - c;
		bit |= 1 << 'i' - c;
		bit |= 1 << 'n' - c;
		bit |= 1 << 't' - c;

		// 26이면 모든 단어 표현 가능
		// 5미만이면 아무 단어도 표현 못함
		if (K == 26) {
			res = N;
		} else if (K >= 5) {
			comb(0, 5, bit);
		}
		System.out.println(res);
		br.close();
	}

	/* nCk의 조합을 검사해서 읽을 수 있는 단어만 체크 */
	private static void comb(int start, int cnt, int bit) {
		if (cnt == K) {
			int sum = 0;
			for (int i = 0; i < N; i++) {
				boolean isRead = true;
				for (int j = 4; j < word[i].length() - 4; j++) {
					// 해당 단어에 있는 알파벳과 현재 비트랑 일치 하지 않는다
					// -> 해당 단어는 읽을 수 없다
					if ((bit & 1 << word[i].charAt(j) - c) == 0) {
						isRead = false;
						break;
					}
				}
				
				if (isRead) {
					sum++;
				}
			}

			res = Math.max(res, sum);
			return;
		}

		for (int i = start; i < 26; i++) {
			// 아직 포함 안된 알파벳이라면 비트에 포함
			if ((bit & 1 << i) == 0) {
				comb(i + 1, cnt + 1, bit | 1 << i);
			}
		}
	}
}