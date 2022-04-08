package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 8.
 * 
 * 분류: 구현, 비트마스킹
 * 난이도: 골드3
 * 소요 시간: 0h 59m
 * 혼자 품: O
 * 풀이: 서브넷 마스크를 앞에서부터 비트 단위로 모든 주소와 비교해가며 찾은 후 임의의 IP 주소에 서브넷 마스크를 씌우면 네트워크 주소를 찾을 수 있다.
 * 느낀 점: 비트마스킹 연산 우선순위가 아직 헷갈리는 편인데 실제 시험환경에서는 괄호를 치도록 하자.
 */
public class Boj_G3_2064_IP주소 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] net = new int[n];
		int ipAddress = 0, subnetMask = 0;

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), ".");
			int tmp = 0;

			for (int j = 0; j < 4; j++) {
				int ip = Integer.parseInt(st.nextToken());
				// 8자리 밀어주고 ip 추가
				tmp <<= 8;
				tmp += ip;
			}

			net[i] = tmp;
		}

		// 32자리 비트 검사해가면서 서브넷 마스크 주소를 찾는다
		for (int i = 31; i >= 0; i--) {
			int bit = 1 << i;
			boolean isCheck = false;

			for (int j = 1; j < n; j++) {
				// 앞자리부터 비트를 비교해서 비트가 다르다면 m자리를 찾음
				if ((net[0] & bit) != (net[j] & bit)) {
					isCheck = true;
					break;
				}
			}

			// m자리를 찾았으므로 종료
			if (isCheck) {
				break;
			} else {// 아니라면 앞에서부터 비트를 1로 채운다
				subnetMask |= bit;
			}
		}

		// 네트워크 주소 = ip 주소 OR 서브넷 마스크 주소
		ipAddress = net[0] & subnetMask;
		int checkBit = 255;
		StringBuilder sb = new StringBuilder();

		// 앞자리부터 8개씩 끊어서 저장
		for (int i = 3; i >= 0; i--) {
			sb.append(checkBit & (ipAddress >> 8 * i));
			if (i > 0) {
				sb.append(".");
			}
		}
		sb.append("\n");
		for (int i = 3; i >= 0; i--) {
			sb.append(checkBit & (subnetMask >> 8 * i));
			if (i > 0) {
				sb.append(".");
			}
		}

		System.out.println(sb);
		br.close();
	}
}