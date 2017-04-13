package test;

import java.util.ArrayList;

public class MySolution {

	public static void main(String[] args) {

		int[] values = { 5, 6, 3, 1, 2, 4 };
		int n = 6;
		int node1 = 2;
		int node2 = 4;

		MySolution ms = new MySolution();
		int distance = ms.calculateDistance(values, n, node1, node2);
		System.out.println(distance);

	}

	int calculateDistance(int[] values, int n, int node1, int node2) {

		int distance = -1;

		if (values != null && values.length == n) {

			Node node = new Node();
			Node root = null;

			for (int i = 0; i < n; i++) {
				root = node.addNode(values[i], root);
			}

			Node fromNode = node.findNode(node1, root);
			Node toNode = node.findNode(node2, root);

			if (fromNode != null && toNode != null) {
				ArrayList<Node> fromNodeParentList = new ArrayList<Node>();
				fromNodeParentList.add(fromNode);
				node.generateParentList(fromNode, fromNodeParentList);

				ArrayList<Node> toNodeParentList = new ArrayList<Node>();
				toNodeParentList.add(toNode);
				node.generateParentList(toNode, toNodeParentList);

				distance = findDistance(fromNodeParentList, toNodeParentList);
			}
		}

		return distance;

	}

	int findDistance(ArrayList<Node> fromNodeParentList,
			ArrayList<Node> toNodeParentList) {

		int distance = 0;
		boolean distanceFound = false;

		if (fromNodeParentList != null && toNodeParentList != null) {

			for (int i = 0; i < fromNodeParentList.size(); i++) {
				for (int j = 0; j < toNodeParentList.size(); j++) {
					if (fromNodeParentList.get(i).value == toNodeParentList
							.get(j).value) {
						distance = i + j;
						distanceFound = true;
						break;
					}
				}
				if (distanceFound) {
					break;
				}
			}

		}

		return distance;
	}

	class Node {

		Node parent;
		Node lchild;
		Node rchild;

		int value;

		Node addNode(int value, Node node) {

			if (node == null) {
				node = new Node();
				node.value = value;
			} else {
				if (value < node.value) {
					node.lchild = addNode(value, node.lchild);
					node.lchild.parent = node;
				} else {
					node.rchild = addNode(value, node.rchild);
					node.rchild.parent = node;
				}
			}
			return node;
		}

		Node findNode(int value, Node node) {
			if (node != null) {
				if (value == node.value) {
					return node;
				} else if (value < node.value) {
					return findNode(value, node.lchild);
				} else if (value >= node.value) {
					return findNode(value, node.rchild);
				}
			}
			return null;
		}

		void generateParentList(Node node, ArrayList<Node> parentList) {

			if (parentList == null) {
				parentList = new ArrayList<Node>();
			}
			if (node != null && node.parent != null) {
				parentList.add(node.parent);
				generateParentList(node.parent, parentList);
			}

		}
	}
}
