import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 21.
 *
 * 분류: 다이나믹 프로그래밍, 브루트포스 알고리즘
 * 난이도: 실버5
 * 혼자 품: X
 * 풀이: 
 * 1. dp배열을 선언하고 dp[1] = 1을 저장해놓고 반복문을 돌리는데 제곱수이면 1을 저장한다.
 * 2. 제곱수가 아니면 1부터 i까지의 제곱수를 검사해서 i - i보다 작은 제곱수에서 뺀 값(i - j * j)들을 비교해가며 최소값을 갱신한다.
 * 3. 제곱수 검사가 끝났으면 dp[0]=0 이므로 구한 최소값에 1을 더해준다.
 * 느낀 점: 
 * 일단 오랜만에 푸는 dp문제였는데 실버5의 난이도인데도 점화식을 어떻게 세워야될지 감이 안와서 결국 다른 사람의 코드를 참조하게 되었다.
 * dp는 점화식을 찾는 것이 핵심이라서 점화식만 알게 된다면 이해가 금방 되지만 그 점화식을 찾는 과정이 만만치 않아서 쉬운 dp문제라도 자주 풀어야 되는 것 같다.
 * 그리고 BufferedWriter에 대해 새롭게 알게 되었는데 정수 하나만 출력할 경우 아스키 타입으로 인코딩 되어 이상한 문자 값이 나오게 되어 굳이 사용한다면
 * 문자열로 형변환해야 올바른 값이 출력된다는 것 또한 알게 되었다.
 */
public class Boj_S5_17626_FourSquares {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		int[] dp = new int[n + 1];
		dp[1] = 1;

		for (int i = 2; i <= n; i++) {
			if (Math.sqrt(i) % 1 == 0) {// 제곱수이면 1 저장
				dp[i] = 1;
				continue;
			}

			int min = n;
			for (int j = 1; j * j <= i; j++) {// 1부터 i까지의 제곱수 검사
				min = Math.min(min, dp[i - j * j]);// i-j*j 점화식을 통해 최소값 갱신
			}
			dp[i] = min + 1;// dp[0]=0 이므로 1을 더해줘야 함
		}

		bw.write(String.valueOf(dp[n]));// 정수 단일 출력 시 ascii 타입으로 인코딩 되므로 String형으로 변경
		bw.flush();
		br.close();
	}
}