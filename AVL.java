import java.lang.Math;

public class AVL extends BST
{
  public AVL() { }
  
  public void insert(String key) 
  {
	  inserthelp(root, key);
  }
 
  public int height(Node<String> rt)
  {
	  if (rt == null) return 0;
	  return 1 + Math.max( height(rt.left()), height(rt.right()) );
  }
  
  private Node<String> inserthelp(Node<String> rt, String k)
  {
	  if (rt == null)
		  rt = new Node<String>(k);
	  
	  else if (rt.key().compareTo(k) < 0) // rt is smaller than k
	  {
		  rt.setRight(inserthelp(rt.right(), k));
		  if (height(rt.right()) - height(rt.left()) > 1)
		  {
			  if (rt.right().key().compareTo(k) < 0)
				  rt = rotate_RR(rt); // k is inserted in rt's right subtree's right subtree
			  else
				  rt = rotate_RL(rt); // k is inserted in rt's right subtree's left subtree
		  }  
	  }
	  
	  else if (rt.key().compareTo(k) > 0)
	  {
		  rt.setLeft(inserthelp(rt.left(), k));
		  if (height(rt.left()) - height(rt.right()) > 1)
		  {
			  if (rt.left().key().compareTo(k) > 0)
				  rt = rotate_LL(rt); // k is inserted in rt's left subtree's left subtree
			  else
				  rt = rotate_LR(rt); // k is inserted in rt's left subtree's right subtree
		  }
	  }
	  
	  else
		  rt.increaseFreq();
	  
	  return rt;
  }
  
  private Node<String> rotate_RR(Node<String> rt)
  {
	  //TODO
	  return rt;
  }
  
  private Node<String> rotate_LL(Node<String> rt)
  {
	  //TODO
	  return rt;
  }
  
  private Node<String> rotate_RL(Node<String> rt)
  {
	  //TODO
	  return rt;
  }
  
  private Node<String> rotate_LR(Node<String> rt)
  {
	  //TODO
	  return rt;
  }

}
