// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr), Sep/23/2014
/**
 * 
 * @author Ha-Eun Hwangbo
 * @version 2016.10.21
 *
 */

public class BST 
{ // Binary Search Tree implementation

	public static int BIGNUM = 99999999;
	protected Node root;
	protected int nodeCnt;
	protected boolean NOBSTified = false;
	protected boolean OBSTified = false;
	protected Node[] nodeArr;
	
	public BST() 
	{
		root = null;
		nodeCnt = 0;
		nodeArr = null;
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
		postorder_weighted(root, info);
		
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
		if (nodeArr == null)
		{
			nodeArr = new Node[nodeCnt+1];
			nodeArr[0] = null; // set 0th element to null to start index at 1	
			inorder_nodes(root, nodeArr, 1); // now nodeArr contains all nodes of bst in increasing order
		}
		root = build_nobst(nodeArr, 1, nodeCnt);
	}	
	
	public void obst() 
	{
		// Set OBSTified to true.
		OBSTified = true;
		int[][] cost = new int[nodeCnt+2][nodeCnt+1];
		int[][] bestroot = new int[nodeCnt+2][nodeCnt+1];
		
		if (nodeArr == null)
		{
			nodeArr = new Node[nodeCnt+1];
			nodeArr[0] = null; // set 0th element to null to start index at 1
			inorder_nodes(root, nodeArr, 1); // now nodeArr contains all nodes of bst in increasing order
		}
		
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
					cost[low][high] = sumFreq_arr(nodeArr, low, high) + findMin(cost, bestroot, low, high);
				}
			}
		}
		
		// build obst from bestroot matrix
		root = build_obst(nodeArr, bestroot, 1, nodeCnt);
	}	
	
	public void print() 
	{
		inorder(root, "print", 0);
	}
	
	protected Node inserthelp(Node rt, String k)
	{
		if (rt == null) // base case 1: insert new node at the leaf
		{
			nodeCnt++;
			return new Node(k);
		}
		else if (rt.key().compareTo(k) < 0) // rt.key is smaller than k
			rt.setRight( inserthelp(rt.right(), k) );
		else if (rt.key().compareTo(k) > 0) // rt.key is larger than k
			rt.setLeft( inserthelp(rt.left(), k) );
		else // base case 2: increase frequency by 1 if the key already exists
			rt.increaseFreq();
		
		return rt;
	}
	
	protected boolean findhelp(Node rt, String k)
	{
		if (rt == null) return false; // base case 1: key not found
		
		rt.access(); // increase access count by 1 if node is probed
		if (rt.key().equals(k)) return true; // base case 2: key found
		if (rt.key().compareTo(k) < 0) 
			return findhelp(rt.right(), k);
		else 
			return findhelp(rt.left(), k);
	}
	
	protected void postorder_weighted(Node rt, int[] info)
	{
		// info is a size 2 array that contains depth of current node + 1 (info[0]) and weighted sum (info[1])
		if (rt == null) return;
		
		info[0]++;
		postorder_weighted(rt.left(), info);
		postorder_weighted(rt.right(), info);
		info[0]--;
		info[1] += info[0] * rt.getFreq();
	}
	
	protected int inorder_nodes(Node rt, Node[] arr, int idx)
	{
		if (rt == null) return idx;
		idx = inorder_nodes(rt.left(), arr, idx);
		arr[idx] = rt;
		idx++;
		idx = inorder_nodes(rt.right(), arr, idx);
		return idx;
	}
	
	protected int inorder(Node rt, String op, int sum)
	{	
		// internal method for sumFreq, resetCounters, print, sumProbes
		// inorder traversal
		if (rt == null) return sum; // base case
		sum = inorder(rt.left(), op, sum);
		
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

		sum = inorder(rt.right(), op, sum);
		return sum;
	}
	
	protected int findMin(int[][] cost, int[][] bestroot, int low, int high)
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
	
	protected Node build_obst(Node[] nodeArr, int[][] bestroot, int low, int high)
	{
		// build obst from information stored in bestroot matrix recursively
		int idx = bestroot[low][high];
		Node rt = nodeArr[idx];
		
		if (rt == null) // base case 
			return rt;
		
		rt.setLeft(build_obst(nodeArr, bestroot, low, idx-1));
		rt.setRight(build_obst(nodeArr, bestroot, idx+1, high));
		return rt;
	}
	
	protected int sumFreq_arr(Node[] nodeArr, int low, int high)
	{
		// return sum of the node frequency in nodeArr from index low to high
		int sum = 0;
		int i = -10;

		for (i = low; i <= high; i++)
			sum += nodeArr[i].getFreq();
		return sum;
	}
	
	protected Node build_nobst(Node[] nodeArr, int low, int high)
	{
		Node minRoot;
		int min = BIGNUM;
		int minIdx = 0;
		if (low < high)
		{
			// update min and minIdx by looping through all the nodes in the range
			for (int r = low; r <= high; r++)
			{
				int diff = sumFreq_arr(nodeArr, low, r-1) - sumFreq_arr(nodeArr, r+1, high); // left subtree - right subtree
				if (Math.abs(diff) < Math.abs(min))
				{
					min = diff;
					minIdx = r;
				}
				else if (diff + min == 0 && diff < min) // tie breaking: if diff < 0, then right subtree is heavier
					minIdx = r;
			}
			
			minRoot = nodeArr[minIdx];
			minRoot.setLeft(build_nobst(nodeArr, low, minIdx-1));
			minRoot.setRight(build_nobst(nodeArr, minIdx+1, high));
		}
		
		else if (low == high)// base case 1 : one node
		{
			minRoot = nodeArr[low];
			minRoot.setLeft(null);
			minRoot.setRight(null);
		}
		
		else // base case 2 : empty tree
			minRoot = null;
		
		return minRoot;
	}
}

