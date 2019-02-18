package Trees;

import java.util.LinkedList;
import java.util.Queue;

class Node {
	Node left, right;
	String value;

	public Node() {
		this.value = "";
		this.left = null;
		this.right = null;
	}

	public Node(String value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

class BTree {
	private Node root;
	private Queue<Node> breadthFirstSearch = new LinkedList<>();

	public BTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean insert(String value) {
		root = insert(root, value);
		return true;
	}

	private Node insert(Node node, String value) {
		if (node == null)
			node = new Node(value);
		else if (value.compareToIgnoreCase(node.getValue()) > 0)
			node.setRight(insert(node.getRight(), value));
		else if (value.compareToIgnoreCase(node.getValue()) < 0)
			node.setLeft(insert(node.getLeft(), value));
		return node;
	}

	public int countNodes() {
		return count(root);
	}

	private int count(Node node) {
		if (node == null)
			return 0;
		else {
			int n = 1;
			n += count(node.getLeft()) + count(node.getRight());
			return n;
		}
	}

	public void inorder() {
		inorder(root);
		System.out.println();
	}

	private void inorder(Node node) {
		if (node == null)
			return;

		inorder(node.getLeft());
		System.out.print(node.getValue());
		inorder(node.getRight());
	}

	public void postorder() {
		postorder(root);
		System.out.println();
	}

	private void postorder(Node node) {
		if (node == null)
			return;

		postorder(node.getLeft());
		postorder(node.getRight());
		System.out.print(node.getValue());
	}

	public void preorder() {
		preorder(root);
		System.out.println();
	}

	private void preorder(Node node) {
		if (node == null)
			return;

		System.out.print(node.getValue());
		preorder(node.getLeft());
		preorder(node.getRight());
	}

	public int height() {
		return height(root, 0, 0);
	}

	private int height(Node node, int currentHeight, int maxHeight) {
		if (node == null)
			return currentHeight - 1;
		else {
			int tempL = height(node.getLeft(), currentHeight + 1, maxHeight);
			maxHeight = maxHeight > tempL ? maxHeight : tempL;
			int tempR = height(node.getRight(), currentHeight + 1, maxHeight);
			maxHeight = maxHeight > tempR ? maxHeight : tempR;
		}
		return maxHeight;
	}

	public void display() {
		breadthFirstSearch.add(root);
		display(root);
		System.out.println();
	}

	private void display(Node node) {
		if (node == null || breadthFirstSearch.size() == 0)
			return;

		Node cur = breadthFirstSearch.poll();
		if (cur.getLeft() != null)
			breadthFirstSearch.add(cur.getLeft());
		if (cur.getRight() != null)
			breadthFirstSearch.add(cur.getRight());
		if (cur != null) {
			System.out.print(cur.getValue() + " ");
			display(cur);
		}
	}

	public void search(String value) {
		search(root, value);
	}

	private boolean search(Node node, String value) {
		if (node != null) {
			if (node.getValue().equals(value)) {
				System.out.println("Node " + value + " Found. Root!");
				return true;
			}
			if (node.getLeft() != null) {
				if (node.getLeft().getValue().equals(value)) {
					System.out.println("Node " + value + " Found. Left child of: " + node.getValue());
					return true;
				}
			}
			if (node.getRight() != null) {
				if (node.getRight().getValue().equals(value)) {
					System.out.println("Node " + value + " Found. Right child of: " + node.getValue());
					return true;
				}
			}
			if (search(node.getLeft(), value))
				return true;
			if (search(node.getRight(), value))
				return true;
			return false;
		}
		return false;
	}

	public void delete(String value) {
		delete(root, value, null, 'c');
	}

	private void delete(Node node, String value, Node parentNode, char position) {
		if (node == null)
			return;
		if (node.getValue().equals(value)) {
			if (position == 'l') {
				Node nodeLeft = node.getLeft();
				if (node.getRight() != null) {
					if (nodeLeft != null) {
						while (nodeLeft.getRight() != null) {
							nodeLeft = nodeLeft.getRight();
						}
						nodeLeft.setRight(node.getRight().getLeft());
						node.getRight().setLeft(node.getLeft());
					}
					if (parentNode != null)
						parentNode.setLeft(node.getRight());
					else
						root = node.getRight();
				} else {
					if (parentNode != null)
						parentNode.setLeft(node.getLeft());
					else
						root = node.getLeft();
				}
				return;
			} else if (position == 'r') {
				Node nodeRight = node.getRight();
				if (node.getLeft() != null) {
					if (nodeRight != null) {
						while (nodeRight.getLeft() != null) {
							nodeRight = nodeRight.getLeft();
						}
						nodeRight.setLeft(node.getLeft().getRight());
						node.getLeft().setRight(node.getRight());
					}
					if (parentNode != null)
						parentNode.setRight(node.getLeft());
					else
						root = node.getLeft();
				} else {
					if (parentNode != null)
						parentNode.setRight(node.getRight());
					else
						root = node.getRight();
				}
				return;
			} else {
				if (node.getRight() != null)
					delete(node, value, null, 'l');
				else
					delete(node, value, null, 'r');
				return;
			}

		} else {
			delete(node.getLeft(), value, node, 'l');
			delete(node.getRight(), value, node, 'r');
		}
		return;

	}

	public void deleteRec(String value) {
		root = deleteRec(root, value);
	}

	public Node deleteRec(Node node, String value) {
		if (node == null)
			return node;
		if (node.getValue().compareToIgnoreCase(value) > 0)
			node.setLeft(deleteRec(node.getLeft(), value));
		else if (node.getValue().compareToIgnoreCase(value) < 0)
			node.setRight(deleteRec(node.getRight(), value));
		else {
			if (node.getLeft() == null)
				return node.getRight();
			else if (node.getRight() == null)
				return node.getLeft();
			String minValue = node.getRight().getValue();
			Node nodeRight = node.getRight();
			while (nodeRight.getLeft() != null) {
				minValue = nodeRight.getValue();
				nodeRight = nodeRight.getLeft();
			}
			node.setValue(minValue);
			node.setRight(deleteRec(node.getRight(), node.getValue()));
		}
		return node;
	}

	public void replace(String from, String to) {
		if (search(root, from)) {
			deleteRec(from);
			insert(to);
		}
	}
}

public class BinaryTree {

	public static void main(String[] args) {
		BTree binaryTree = new BTree();

		binaryTree.insert("4");
		binaryTree.insert("3");
		binaryTree.insert("5");
		binaryTree.insert("2");
		binaryTree.insert("1");
		binaryTree.insert("9");
		binaryTree.insert("8");
		binaryTree.insert("7");
		binaryTree.insert("6");

		System.out.println("Count: " + binaryTree.countNodes());
		
		binaryTree.inorder();
		binaryTree.preorder();
		binaryTree.postorder();
		
		System.out.println("Height: " + binaryTree.height());
		
		binaryTree.display();
		
		binaryTree.search("7");

		binaryTree.delete("4");
		binaryTree.inorder();
		
		binaryTree.deleteRec("9");
		binaryTree.inorder();
		
		binaryTree.replace("5", "4");
		binaryTree.inorder();
	}

}
