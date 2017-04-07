package test;

import java.util.HashSet;
import java.util.Set;

public class Solution2 {

	public static void main(String[] args) {

		Solution2 solution = new Solution2();

		Integer[] values = { 5, 6, 3, 1, 2, 4 };
		int n = 6;
		int node1 = 2;
		int node2 = 4;

		System.out.println(solution.getDistance(values, n, node1, node2));

	}

	public int getDistance(Integer[] values, int n, int n1, int n2) {

		TreeNode root = null;

		for (int i = 0; i < values.length; i++) {
			root = buildNode(root, values[i]);
		}

		LCAData lcaData = new LCAData(null, 0);

		int distance = findDistance(root, lcaData, n1, n2, new HashSet<Integer>());

		if (lcaData.lca != null) {
			return distance;
		} else {
			return -1;
		}
	}

	class LCAData {
		TreeNode lca;
		int count;

		public LCAData(TreeNode parent, int count) {
			this.lca = parent;
			this.count = count;
		}
	}

	class TreeNode {
		TreeNode left;
		TreeNode right;
		int data;

	}

	public TreeNode buildNode(TreeNode node, int data) {
		if (node == null) {
			node = new TreeNode();
			node.data = data;
		} else {
			if (data < node.data) {
				node.left = buildNode(node.left, data);
			} else {
				node.right = buildNode(node.right, data);
			}
		}
		return node;
	}

	public int findDistance(TreeNode node, LCAData lcaData, int n1, int n2,
			Set<Integer> set) {

		if (node == null) {
			return 0;
		}

		// when both were found
		if (lcaData.count == 2) {
			return 0;
		}

		// when only one of them is found
		if ((node.data == n1 || node.data == n2) && lcaData.count == 1) {
			// second element to be found is not a duplicate node of the tree.
			if (!set.contains(node.data)) {
				lcaData.count++;
				return 1;
			}
		}

		int foundInCurrent = 0;
		// when nothing was found (count == 0), or a duplicate tree node was
		// found (count == 1)
		if (node.data == n1 || node.data == n2) {
			if (!set.contains(node.data)) {
				set.add(node.data);
				lcaData.count++;
			}
			// replace the old found node with new found node, in case of
			// duplicate. this makes distance the shortest.
			foundInCurrent = 1;
		}

		int foundInLeft = findDistance(node.left, lcaData, n1, n2, set);
		int foundInRight = findDistance(node.right, lcaData, n1, n2, set);

		// second node was child of current, or both nodes were children of
		// current
		if (((foundInLeft > 0 && foundInRight > 0)
				|| (foundInCurrent == 1 && foundInRight > 0) || (foundInCurrent == 1 && foundInLeft > 0))
				&& lcaData.lca == null) {
			// least common ancestor has been obtained
			lcaData.lca = node;
			return foundInLeft + foundInRight;
		}

		// first node to match is the current node. none of its children are
		// part of second node.
		if (foundInCurrent == 1) {
			return foundInCurrent;
		}

		// ancestor has been obtained, aka distance has been found. simply
		// return the distance obtained
		if (lcaData.lca != null) {
			return foundInLeft + foundInRight;
		}

		// one of the children of current node was a possible match.
		return (foundInLeft + foundInRight) > 0 ? (foundInLeft + foundInRight) + 1
				: (foundInLeft + foundInRight);
	}
}
