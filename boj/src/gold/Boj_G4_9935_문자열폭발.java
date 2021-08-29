package gold;
import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 14.
 *
 * 분류: 자료구조, 문자열, 스택
 * 난이도: 골드4
 * 소요 시간: time over
 * 혼자 품: X
 * 풀이: 
 * 1. 입력받은 원본 문자열에서 문자를 하나씩 sb에 넣는다.
 * 2. sb의 길이가 폭발 문자열의 길이보다 크거나 같다면 폭발 문자열 여부를 반복문을 통해 체크한다.
 * 3-1. 폭발 문자열이 아니라면 내부 반복문을 종료하고 다시 원본 문자열을 체크하러 간다.
 * 3-2. 폭발 문자열이라면 sb에서 폭발 문자열을 지워준다.
 * 느낀 점: 
 * 처음에는 substring 메서드를 사용하여 풀이를 하려 했는데 원본 문자열의 길이가 최대 100만이라서 O(N)만에 수행되는 로직을 설계해야 했다.
 * 분류에 스택이 있다는 것을 알고 스택을 써서 풀이하려 했지만 어떻게 수행을 해나가야 될지 감이 안잡혀서 다른 사람의 코드를 참조하게 되었고
 * 스택을 써서 푼다면 마지막에 다시 스택에 있는 문자들을 문자열로 처리해주어야 하는데 StringBuilder를 사용한다면 마지막 과정을 생략하고
 * 바로 출력할 수 있다는 것을 알게 되었다. 문자열은 웬만한 기업의 코딩테스트에서 자주 출제되는 자료구조인 만큼 확실하게 알고 가야겠다고 느끼게 되었다.
 */
public class Boj_G4_9935_문자열폭발 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		String rmStr = br.readLine();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			sb.append(c);

			if (sb.length() >= rmStr.length()) {// sb의 길이가 폭발 문자열의 길이보다 크거나 같다면
				boolean flag = true;// 삭제 여부를 체크하는 변수
				for (int j = 0; j < rmStr.length(); j++) {
					char sbc = sb.charAt(sb.length() - rmStr.length() + j);
					char rmc = rmStr.charAt(j);
					if (sbc != rmc) {// 폭발 문자열이랑 일치하지 않으면 flag를 false로 변경
						flag = false;
						break;
					}
				}
				
				if (flag) {// 일치한다면 sb에서 폭발 문자열을 지워줌
					sb.delete(sb.length() - rmStr.length(), sb.length());
				}
			}
		}

		if (sb.length() == 0) {
			sb.append("FRULA");
		}
		System.out.println(sb);
		br.close();
	}
}