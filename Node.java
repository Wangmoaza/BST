public class Node
  {
	  private String key;
	  private int freq; 
	  private int accessCnt; // access count
	  private Node left; // pointer to left child
	  private Node right; // pointer to right child
	  private int height;
	  
	  public Node(String k)
	  {
		  key = k;
		  left = null; right = null;
		  freq = 1; accessCnt = 0; height = 1;
	  }
	  
	  public Node(String k, Node l, Node r)
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
	  
	  public String key()
	  {
		  return key;
	  }
	  
	  public Node left()
	  {
		  return left;
	  }
	  
	  public Node right()
	  {
		  return right;
	  }
	  
	  public void setLeft(Node newNode)
	  {
		  left = newNode;
	  }
	  
	  public void setRight(Node newNode)
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