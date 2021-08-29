package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 27.
 *
 * 분류: 수학, 정수론, 유클리드 호제법
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 수들을 num에 저장하고 처음 두 수의 차의 최대공약수를 구한다.
 * 2. 반복문을 통해 이전 최대공약수와 다음 차의 최대공약수를 구해가며 gcdNum 값을 갱신한다.
 * 3. 리스트에 1을 제외한 최종 gcdNum의 약수들을 저장하여 정렬한 후 출력한다.
 * 느낀 점: 
 * 두 수의 차들의 최대공약수를 뽑아내는 발상이 쉽게 떠오르지 않아서 오랫동안 못 푼 문제였는데 겨우 풀게 되었다.
 * 항상 수학 문제는 발상이 잘 되지 않으면 풀기가 어렵다고 느낀다.
 */
public class Boj_G5_2981_검문 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] num = new int[N];
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(br.readLine());
		}

		int gcdNum = Math.abs(num[1] - num[0]);// 두 수의 차의 최대공약수
		for (int i = 2; i < N; i++) {// 방금 전 최대공약수, 다음 두 수의 차의 최대공약수를 구해서 갱신을 반복
			gcdNum = gcd(gcdNum, Math.abs(num[i] - num[i - 1]));
		}

		getDivisor(gcdNum);
		br.close();
	}

	/* 두 수의 최대공약수를 구함 */
	private static int gcd(int a, int b) {
		int tmp = 0;
		while (b != 0) {
			tmp = a % b;
			a = b;
			b = tmp;
		}
		return a;
	}

	/* 1을 제외한 약수들을 출력 */
	private static void getDivisor(int num) {
		ArrayList<Integer> al = new ArrayList<>();
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0) {
				al.add(i);
				if (num / i != i) {// i로 나눈 수와 i가 같다면 두번 넣게 되므로 같지 않을 때에만 리스트에 저장
					al.add(num / i);
				}
			}
		}
		al.add(num);// 자기 자신도 약수이므로 저장

		Collections.sort(al);// 순서대로 출력하기 위해 정렬
		StringBuilder sb = new StringBuilder();
		for (int i : al) {
			sb.append(i).append(" ");
		}
		System.out.println(sb);
	}
}