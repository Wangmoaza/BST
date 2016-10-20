// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr), Sep/23/2014

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
	
	private Node<String> inserthelp(Node<String> rt, String k)
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
	
	public boolean find(String key)
	{
		return findhelp(root, key);
	}
	
	private boolean findhelp(Node<String> rt, String k)
	{
		if (rt == null) return false;
		rt.access(); // increase access count by 1
		if (rt.key().equals(k)) return true;
		if (rt.key().compareTo(k) < 0) 
			return findhelp(rt.right(), k);
		else 
			return findhelp(rt.left(), k);
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
		//TODO
	}
	
	public void resetCounters()
	{
		inorder(root, "reset", 0);
	}
	
	public void nobst() 
	{
		//TODO
		// Set NOBSTified to true.
	}	
	
	public void obst() 
	{
		//TODO
		// Set OBSTified to true.
	}	
	
	public void print() 
	{
		inorder(root, "print", 0);
	}
	
	private int inorder(Node<String> rt, String op, int sum)
	{	
		if (rt == null) return sum;
		inorder(rt.left(), op, sum);
		switch (op)
		{
		case "print":
			System.out.println(rt);
			break;
		case "freq":
			sum += rt.getFreq();
			break;
		case "probe":
			sum += rt.accessCount();
			break;
		case "reset":
			rt.resetCounters();
		default:
			System.out.println("Invalid operation");
		}
		inorder(rt.left(), op, sum);
		return sum;
	}

  
}

