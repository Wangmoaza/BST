public class Node<Key>
  {
	  private Key key;
	  private int freq; 
	  private int accessCnt; // access count
	  private Node<Key> left; // pointer to left child
	  private Node<Key> right; // pointer to right child
	  private int height;
	  
	  public Node(Key k)
	  {
		  key = k;
		  left = null; right = null;
		  freq = 1; accessCnt = 0; height = 1;
	  }
	  
	  public Node(Key k, Node<Key> l, Node<Key> r)
	  {
		  key = k;
		  left = l; right = r;
		  freq = 1; accessCnt = 0;
	  }
	  
	  public void setHeight(int h)
	  {
		  height = h;
	  }
	  
	  public int getHeight()
	  {
		  return height;
	  }
	  
	  public Key key()
	  {
		  return key;
	  }
	  
	  public Node<Key> left()
	  {
		  return left;
	  }
	  
	  public Node<Key> right()
	  {
		  return right;
	  }
	  
	  public void setLeft(Node<Key> newNode)
	  {
		  left = newNode;
	  }
	  
	  public void setRight(Node<Key> newNode)
	  {
		  right = newNode;
	  }
	  
	  public int getFreq()
	  {
		  return freq;
	  }
	  
	  public void increaseFreq()
	  {
		  freq++;
	  }
	  
	  public int accessCount() 
	  {
		  return accessCnt;
	  }
	  
	  public void access() // increase accessCnt by 1
	  {
		  accessCnt++;
	  }
	  
	  public void resetCounters()
	  {
		  freq = 0;
		  accessCnt = 0;
	  }
	  
	  @Override
	  public String toString()
	  {
		return "[" + key + ":" + freq + ":" + accessCnt + "]";
	  }
  }