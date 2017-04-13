package test;

import java.util.ArrayList;

public class MySolution {

	public static void main(String[] args) {

		Integer[] values = { 5, 6, 3, 1, 2, 4 };
		int n = 6;
		int n1 = 2;
		int n2 = 4;

		MySolution ms = new MySolution();
		int distance = ms.calculateDistince(values, n, n1, n2);
		System.out.println(distance);

	}

	int calculateDistince(Integer[] values, int n, int n1, int n2) {

		int distance = -1;

		if (values != null && values.length == n) {

			Node node = new Node();
			Node root = null;

			for (int i = 0; i < n; i++) {
				root = node.addNode(values[i], root);
			}

			Node node1 = node.findNode(n1, root);
			Node node2 = node.findNode(n2, root);

			if (node1 != null && node2 != null) {
				ArrayList<Node> node1ParentList = new ArrayList<Node>();
				node1ParentList.add(node1);
				node.generateParentList(node1, node1ParentList);

				ArrayList<Node> node2ParentList = new ArrayList<Node>();
				node2ParentList.add(node2);
				node.generateParentList(node2, node2ParentList);

				distance = findDistance(node1ParentList, node2ParentList);
			}
		}

		return distance;

	}

	int findDistance(ArrayList<Node> node1ParentList,
			ArrayList<Node> node2ParentList) {

		int distance = 0;
		boolean distanceFound = false;

		for (int i = 0; i < node1ParentList.size(); i++) {
			for (int j = 0; j < node2ParentList.size(); j++) {
				if (node1ParentList.get(i).value == node2ParentList.get(j).value) {
					distance = i + j;
					distanceFound = true;
					break;
				}
			}
			if (distanceFound) {
				break;
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

		ArrayList<Node> generateParentList(Node node, ArrayList<Node> list) {

			if (node != null && node.parent != null) {
				list.add(node.parent);
				generateParentList(node.parent, list);
			}

			return list;
		}
	}
}
