package implement;

import java.io.*;

/**
 * <pre>
 * implement 
 * Boj_B1_1652_누울자리를찾아라.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 5. 31.
 * @version	: 0.1
 *
 * 분류: 구현, 문자열
 * 난이도: 브론즈1
 * 혼자 품: O
 * 풀이: nxn 문자를 배열로 저장한 후 가로, 세로에 대해 각각 반복문을 통해 세어주면서 2칸의 공간이 마련됐다면
 *      누울 수 있는 횟수를 증가시켜 준 후, 짐이 있는 칸을 만날때까지 인덱스를 증가시켜 주는 방식으로 풀이하였다.
 * 느낀 점: 다시 알고리즘에 흥미를 붙이기 위해 푼 쉬운 구현 문제였다.
 *        난이도를 막론하고 다시 하루에 한 문제 이상을 푸는 것을 목표로 해야겠다.
 */
public class Boj_B1_1652_누울자리를찾아라 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		char[][] map = new char[n][n];
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine().toCharArray();
		}

		int rowCnt = 0, colCnt = 0;// 가로로 누울 수 있는 자리 개수, 세로로 누울 수 있는 자리 개수
		for (int i = 0; i < n; i++) {// 가로를 셈
			for (int j = 1; j < n; j++) {
				if (map[i][j - 1] == '.' && map[i][j] == '.') {// 2칸의 빈 공간이 있다면
					rowCnt++;// 카운트 한 후
					while (j < n && map[i][j] != 'X') {// 짐들이 있는 칸을 만날때까지 인덱스 증가
						j++;
					}
				}
			}
		}
		for (int j = 0; j < n; j++) {// 세로를 셈(가로와 로직 동일)
			for (int i = 1; i < n; i++) {
				if (map[i - 1][j] == '.' && map[i][j] == '.') {
					colCnt++;
					while (i < n && map[i][j] != 'X') {
						i++;
					}
				}
			}
		}

		System.out.println(rowCnt + " " + colCnt);
		br.close();
	}
}