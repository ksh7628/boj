package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 10.
 * 
 * 분류: 수학, 정수론, 유클리드 호제법
 * 난이도: 실버4
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: 
 * 1. 두 가로수간 거리 차이 배열을 만들어서 각 거리의 차를 저장한다.
 * 2. 거리 차이 배열을 모두 비교하여 최대공약수를 구한다. 최대공약수가 1이 된다면 더 탐색하지 않는다.
 * 3. 반복문을 통해 현재 배열의 차이값 / 최대공약수 - 1의 값을 누적시켜 최소 심을 횟수를 구한다.
 * 느낀 점: 
 * 처음 푼 방식은 결과의 최솟값을 구할 때 인덱스를 움직여가며 결과값을 구했었는데 시간이 생각보다 많이 걸려서
 * 다른 방법을 찾아보니 풀이 3번의 방법을 통해 시간을 절약할 수 있게 되었다.
 * 수학적으로 생각해서 효율성을 높히는 방법을 잘 고려 해야겠다고 느낀 문제였다.
 */
public class Boj_S4_2485_가로수 {
	static int[] dis;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dis = new int[N - 1];
		int t1 = Integer.parseInt(br.readLine());

		// 차이 값 저장
		for (int i = 0; i < N - 1; i++) {
			int t2 = Integer.parseInt(br.readLine());
			dis[i] = t2 - t1;
			t1 = t2;
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int g = dis[0];
		for (int i = 1; i < N - 1; i++) {
			// 최대공약수가 1이라면 더 이상 구할 필요가 없다.
			if (g == 1) {
				break;
			}

			g = gcd(g, dis[i]);
		}

		int res = 0;
		// 차이값 / 최대공약수 - 1 = 간격 간 심을 수 있는 나무의 최소 횟수
		for (int i = 0; i < N - 1; i++) {
			res += (int) (dis[i] / g) - 1;
		}
		
//		** 인덱스를 움직여 가면서 모두 체크하는 방식(비효율적) **
//		int idx = 1, newTree = tree[0] + dist, res = 0;
//		while (idx < N) {
//			if (newTree < tree[idx]) {
//				res++;
//			} else {
//				idx++;
//			}
//
//			newTree += dist;
//		}

		return res;
	}

	// 유클리드 알고리즘
	private static int gcd(int a, int b) {
		int tmp = 0;

		while (b != 0) {
			tmp = a % b;
			a = b;
			b = tmp;
		}

		return a;
	}
}