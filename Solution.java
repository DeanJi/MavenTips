package test;

public class Solution {

	public static void main(String[] args) {

		Integer[] values = { 5, 6, 3, 1, 2, 4 };
		int n = 6;
		int node1 = 2;
		int node2 = 4;
		int dinstance = getDistance(values, n, node1, node2);
		System.out.println(dinstance);

		/*
		 * 
		 * 5 / \ 3 6 / \ 1 4 \ 2
		 */

	}

	public static int getDistance(Integer[] values, int n, int node1, int node2) {
		int distance = -1;

		BSTNode root = null;

		if (values != null && values.length > 0 && values.length == n) {

			for (int i = 0; i < n; i++) {
				root = BSTNode.insert(root, values[i]);
			}

			BSTNode.preorder(root);
			System.out.println();
			BSTNode.inorder(root);
			System.out.println();
			BSTNode.postorder(root);

		}

		return distance;
	}

	static class BSTNode {

		BSTNode left;
		BSTNode right;
		Integer data;

		public static BSTNode insert(BSTNode node, Integer data) {
			if (node == null) {
				node = new BSTNode();
				node.data = data;
			} else {
				if (data < node.data) {
					node.left = insert(node.left, data);
				} else {
					node.right = insert(node.right, data);
				}
			}
			return node;
		}

		public static void visit(BSTNode node) {
			System.out.print(node.data + " ");
		}

		public static void preorder(BSTNode node) {
			if (node != null) {
				visit(node);
				preorder(node.left);
				preorder(node.right);
			}
		}

		public static void inorder(BSTNode node) {
			if (node != null) {
				inorder(node.left);
				visit(node);
				inorder(node.right);
			}
		}

		public static void postorder(BSTNode node) {
			if (node != null) {
				postorder(node.left);
				postorder(node.right);
				visit(node);
			}
		}

	}

}
