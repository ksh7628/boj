package gold;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 27.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 트리, 재귀
 * 난이도: 골드5
 * 소요 시간: 1h 25m
 * 혼자 품: X
 * 풀이: 
 * 1. Node 클래스를 정의한다 -> 작으면 왼쪽, 크면 오른쪽 규칙에 따라 재귀적으로 insert 연산을 수행한다.
 * 2. 입력받은 값들로 이진 검색 트리를 구성하고 후위 순회로 출력하기 위해 왼쪽->오른쪽->부모 순으로 재귀적으로 수행한다.
 * 느낀 점: 
 * 처음에는 insert 메소드를 만들지 않고 main문에서 해결하려 했는데 재귀적 사고를 하지 못해서 이진 검색 트리 삽입 연산에 대해
 * 검색하고 나서야 이해를 하게 되었다. 트리도 중요한 자료구조인 만큼 잘 알고 있어야겠다.
 */
public class Boj_G5_5639_이진검색트리 {
	static class Node {
		int data;
		Node left, right;

		public Node(int data) {
			this.data = data;
		}

		public Node(int data, Node left, Node right) {
			super();
			this.data = data;
			this.left = left;
			this.right = right;
		}

		/* 이진 트리 삽입 연산 */
		void insert(int data) {
			if (data < this.data) {
				if (this.left == null) {
					this.left = new Node(data);
				} else {
					this.left.insert(data);
				}
			} else {
				if (this.right == null) {
					this.right = new Node(data);
				} else {
					this.right.insert(data);
				}
			}
		}
	}

	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Node bst = new Node(Integer.parseInt(br.readLine()));
		String input = "";

		while ((input = br.readLine()) != null) {
			bst.insert(Integer.parseInt(input));
		}

		postOrder(bst);
		System.out.print(sb);
		br.close();
	}

	/* 후위 순회 */
	private static void postOrder(Node node) {
		if (node == null) {
			return;
		}

		// Left -> Right -> Parent 순으로 재귀 순회
		postOrder(node.left);
		postOrder(node.right);
		sb.append(node.data).append("\n");
	}
}