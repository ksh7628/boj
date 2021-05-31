package two_pointer;
import java.io.*;
import java.util.StringTokenizer;

/**
 * <pre>
 * two_pointer 
 * Boj_G4_15961_회전초밥.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 15.
 * @version	: 0.1
 * 
 * 분류: 두 포인터, 슬라이딩 윈도우
 * 난이도: 골드4
 * 혼자 품: X
 * 풀이: 처음에 N*k만큼 전수조사를 하려고 했으나 시간복잡도가 너무 커지기 때문에 다른 방법을 생각하다가
 *      투 포인터 알고리즘을 사용하면 시간내에 프로그램이 돌아갈수 있다는 것을 알게되어 투 포인터 알고리즘을 사용했다.
 *      레일에 있는 초밥을 차례로 먹다가 쿠폰에 적힌 초밥 차례가 되었을 때 쿠폰을 아직 사용하지 않았다면 1을 더해주고
 *      그렇지 않으면 k번 선택했을 때 중복되지 않은 초밥 접시의 개수를 갱신하는 식으로 풀이했다.
 * 느낀 점: 투 포인터 알고리즘을 사용해 본적이 없어서 헤매다가 구글 검색을 통해 겨우 이해했다.
 *        투 포인터 알고리즘의 기초 문제부터 풀면서 알고리즘을 학습하는 방향으로 가야겠다.
 */
public class Boj_G4_15961_회전초밥 {
	static int[] sushi;
	static int N, d, k, c;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		sushi = new int[N];
		for (int i = 0; i < N; i++) {
			sushi[i] = Integer.parseInt(br.readLine());
		}
		System.out.println(twoPointer());
		br.close();
	}

	private static int twoPointer() {
		int res = 0, sum = 0;
		int[] used = new int[d + 1];
		for (int i = 0; i < k; i++) {
			if (used[sushi[i]]++ == 0) {
				++sum;
			}
		}
		res = sum;

		for (int i = 1; i < N; i++) {
			if (res <= sum) {
				if (used[c] == 0) {
					res = sum + 1;
				} else {
					res = sum;
				}
			}

			if (--used[sushi[i - 1]] == 0) {
				--sum;
			}
			if (used[sushi[(i + k - 1) % N]] == 0) {
				++sum;
			}
			++used[sushi[(i + k - 1) % N]];
		}

		return res;
	}
}