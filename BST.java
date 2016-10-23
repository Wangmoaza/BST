// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr), Sep/23/2014
/**
 * 
 * @author Ha-Eun Hwangbo
 * @version 2016.10.21
 *
 */

import java.util.ArrayList;

public class BST 
{ // Binary Search Tree implementation

	public static int BIGNUM = 99999999;
	protected Node<String> root;
	protected int nodeCnt;
	protected boolean NOBSTified = false;
	protected boolean OBSTified = false;
	
	public BST() 
	{
		root = null;
		nodeCnt = 0;
	}
	  
	public int size() 
	{
		return nodeCnt;
	}
	
	public void insert(String key) 
	{
		root = inserthelp(root, key);
	}

	public boolean find(String key)
	{
		return findhelp(root, key);
	}

	public int sumFreq() 
	{
		return inorder(root, "freq", 0);
	}
	
	public int sumProbes() 
	{
		return inorder(root, "probe", 0);
	}
	
	public int sumWeightedPath() 
	{
		int[] info = {1, 0}; // {depth, weighted sum}
		inorder_weighted(root, info);
		
		return info[1]; // sum of weighted path lengths
	}
	
	public void resetCounters()
	{
		inorder(root, "reset", 0);
	}
	
	public void nobst() 
	{
		// Set NOBSTified to true.
		NOBSTified = true;
		ArrayList<Node<String>> nodeList = new ArrayList<>();
		nodeList.add(null); // set 0th element to null to start index at 1	
		inorder_nodes(root, nodeList); // now nodeList contains all nodes of bst in increasing order

		root = build_nobst(nodeList, 1, nodeCnt);
	}	
	
	public void obst() 
	{
		// Set OBSTified to true.
		OBSTified = true;
		int[][] cost = new int[nodeCnt+2][nodeCnt+1];
		int[][] bestroot = new int[nodeCnt+2][nodeCnt+1];
		
		ArrayList<Node<String>> nodeList = new ArrayList<>();
		nodeList.add(null); // set 0th element to null to start index at 1	
		inorder_nodes(root, nodeList); // now nodeList contains all nodes of bst in increasing order
		
		// construct cost and bestroot matrix
		for (int low = nodeCnt + 1; low >= 1; low--) // bottom-up
		{
			for (int high = low-1; high <= nodeCnt; high++) // left to right
			{
				if (low > high) // empty tree
				{
					cost[low][high] = 0;
					bestroot[low][high] = 0;
				}

				else
				{
					cost[low][high] = sumFreq_list(nodeList, low, high) + findMin(nodeList, cost, bestroot, low, high);
				}
			}
		}
		
		// build obst from bestroot matrix
		root = build_obst(nodeList, bestroot, 1, nodeCnt);
	}	
	
	public void print() 
	{
		inorder(root, "print", 0);
	}
	
	protected Node<String> inserthelp(Node<String> rt, String k)
	{
		if (rt == null) // base case 1: insert new node at the leaf
		{
			nodeCnt++;
			return new Node<String>(k);
		}
		else if (rt.key().compareTo(k) < 0) // rt.key is smaller than k
			rt.setRight( inserthelp(rt.right(), k) );
		else if (rt.key().compareTo(k) > 0) // rt.key is larger than k
			rt.setLeft( inserthelp(rt.left(), k) );
		else // base case 2: increase frequency by 1 if the key already exists
			rt.increaseFreq();
		
		return rt;
	}
	
	protected boolean findhelp(Node<String> rt, String k)
	{
		if (rt == null) return false; // base case 1: key not found
		
		rt.access(); // increase access count by 1 if node is probed
		if (rt.key().equals(k)) return true; // base case 2: key found
		if (rt.key().compareTo(k) < 0) 
			return findhelp(rt.right(), k);
		else 
			return findhelp(rt.left(), k);
	}
	
	protected void inorder_weighted(Node<String> rt, int[] info)
	{
		// info is a size 2 array that contains depth of current node + 1 (info[0]) and weighted sum (info[1])
		if (rt == null) return;
		
		info[0]++;
		inorder_weighted(rt.left(), info);
		info[1] += info[0] * rt.getFreq();
		inorder_weighted(rt.right(), info);
		info[0]--;
	}
	
	protected void inorder_nodes(Node<String> rt, ArrayList<Node<String>> list)
	{
		if (rt == null) return;
		inorder_nodes(rt.left(), list);
		list.add(rt);
		inorder_nodes(rt.right(), list);
	}
	
	protected int inorder(Node<String> rt, String op, int sum)
	{	
		// internal method for sumFreq, resetCounters, print, sumProbes
		// inorder traversal
		if (rt == null) return sum; // base case
		inorder(rt.left(), op, sum);
		
		// operations
		if (op.equals("print"))
			System.out.println(rt);
		else if (op.equals("freq"))
			sum += rt.getFreq();
		else if (op.equals("probe"))
			sum += rt.accessCount();
		else if (op.equals("reset"))
			rt.resetCounters();
		else
			System.out.println("Invalid operation");

		inorder(rt.right(), op, sum);
		return sum;
	}
	
	protected int findMin(ArrayList<Node<String>> nodeList, int[][] cost, int[][] bestroot, int low, int high)
	{
		// return min cost and save min producing root to bestroot
		int min = BIGNUM;
		int minIndex = -1;
		for (int r = low; r <= high; r++)
		{
			int c = cost[low][r-1] + cost[r+1][high];
			if (c < min)
			{
				min = c;
				minIndex = r;
			}
		}
		
		bestroot[low][high] = minIndex; // set bestroot
		return min;
	}
	
	protected Node<String> build_obst(ArrayList<Node<String>> nodeList, int[][] bestroot, int low, int high)
	{
		// build obst from information stored in bestroot matrix recursively
		int idx = bestroot[low][high];
		Node<String> rt = nodeList.get(idx);
		
		if (rt == null) // base case 
			return rt;
		
		rt.setLeft(build_obst(nodeList, bestroot, low, idx-1));
		rt.setRight(build_obst(nodeList, bestroot, idx+1, high));
		return rt;
	}
	
	protected int sumFreq_list(ArrayList<Node<String>> nodeList, int low, int high)
	{
		// return sum of the node frequency in nodeList from index low to high
		int sum = 0;
		for (int i = low; i <= high; i++)
			sum += nodeList.get(i).getFreq();
		
		return sum;
	}
	
	protected Node<String> build_nobst(ArrayList<Node<String>> nodeList, int low, int high)
	{
		Node<String> minRoot;
		int min = BIGNUM;
		int minIdx = 0;
		if (low < high)
		{
			// update min and minIdx by looping through all the nodes in the range
			for (int r = low; r <= high; r++)
			{
				int diff = sumFreq_list(nodeList, low, r-1) - sumFreq_list(nodeList, r+1, high); // left subtree - right subtree
				if (Math.abs(diff) < Math.abs(min))
				{
					min = diff;
					minIdx = r;
				}
				else if (diff + min == 0 && diff < min) // tie breaking: if diff < 0, then right subtree is heavier
					minIdx = r;
			}
			
			minRoot = nodeList.get(minIdx);
			minRoot.setLeft(build_nobst(nodeList, low, minIdx-1));
			minRoot.setRight(build_nobst(nodeList, minIdx+1, high));
		}
		
		else if (low == high)// base case 1 : one node
		{
			minRoot = nodeList.get(low);
			minRoot.setLeft(null);
			minRoot.setRight(null);
		}
		
		else // base case 2 : empty tree
			minRoot = null;
		
		return minRoot;
	}
}

