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
		
		return info[1];
	}
	
	public void resetCounters()
	{
		inorder(root, "reset", 0);
	}
	
	public void nobst() 
	{
		NOBSTified = true;
		//TODO
		ArrayList<Node<String>> nodeList = new ArrayList<>();
		nodeList.add(null); // set 0th element to null to start index at 1	
		inorder_nodes(root, nodeList); // now nodeList contains all nodes of bst in increasing order
		
		// Set NOBSTified to true.
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
				else if (low == high)
				{
					cost[low][high] = nodeList.get(low).getFreq();
				}
				else
				{
					cost[low][high] = obst_sumFreq(nodeList, low, high) + findMin(nodeList, cost, bestroot, low, high);
				}
			}
		}
		
		// build obst
		root = build_obst(root, nodeList, bestroot, 1, nodeCnt);
	}	
	
	public void print() 
	{
		inorder(root, "print", 0);
	}
	
	protected Node<String> inserthelp(Node<String> rt, String k)
	{
		if (rt == null)
		{
			nodeCnt++;
			return new Node<String>(k);
		}
		else if (rt.key().compareTo(k) < 0) // rt.key is smaller than k
			rt.setRight( inserthelp(rt.right(), k) );
		else if (rt.key().compareTo(k) > 0) // rt.key is larger than k
			rt.setLeft( inserthelp(rt.left(), k) );
		else // rt.key == k
			rt.increaseFreq();
		
		return rt;
	}
	
	protected boolean findhelp(Node<String> rt, String k)
	{
		if (rt == null) return false;
		rt.access(); // increase access count by 1
		if (rt.key().equals(k)) return true;
		if (rt.key().compareTo(k) < 0) 
			return findhelp(rt.right(), k);
		else 
			return findhelp(rt.left(), k);
	}
	
	protected void inorder_weighted(Node<String> rt, int[] info)
	{
		// info is a size 2 array that contains depth of current node (info[0]) and weighted sum (info[1])
		if (rt == null)
			return;
		
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
		if (rt == null) return sum;
		inorder(rt.left(), op, sum);
		
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

		inorder(rt.left(), op, sum);
		return sum;
	}
	
	protected int obst_sumFreq(ArrayList<Node<String>> nodeList, int low, int high)
	{
		int sum = 0;
		for (int i = low; i <= high; i++)
			sum += nodeList.get(i).getFreq();
		return sum;
	}
	
	protected int findMin(ArrayList<Node<String>> nodeList, int[][] cost, int[][] bestroot, int low, int high)
	{
		// return min and save min producing root to bestroot
		int min = -1;
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
		
		bestroot[low][high] = minIndex;
		return min;
	}
	
	protected Node<String> build_obst(Node<String> rt, ArrayList<Node<String>> nodeList, int[][] bestroot, int low, int high)
	{
		// FIXME
		int idx = bestroot[low][high];
		rt = nodeList.get(idx);
		
		if (rt == null) return rt;
		
		build_obst(rt, nodeList, bestroot, 1, idx-1);
		build_obst(rt, nodeList, bestroot, idx+1, nodeCnt);
		
		rt.setLeft(nodeList.get(bestroot[1][idx-1]));
		rt.setRight(nodeList.get(bestroot[idx+1][nodeCnt]));
		return rt;
	}
}

