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
	  // modification from lecture note
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
	  // k is inserted in rt's right subtree's right subtree
	  // one left turn around rt
	  Node<String> child = rt.right();
	  rt.setRight(child.left());
	  child.setLeft(rt);
	  return child; // return the new root
  }
  
  private Node<String> rotate_LL(Node<String> rt)
  {
	  // k is inserted in rt's left subtree's left subtree
	  // one right turn around rt
	  Node<String> child = rt.left();
	  rt.setLeft(child.right());
	  child.setRight(rt);
	  return child; // return the new root
  }
  
  private Node<String> rotate_RL(Node<String> rt)
  {
	  // k is inserted in rt's right subtree's left subtree
	  // one right turn around right subtree, then one left turn around rt
	  rt.setRight(rotate_LL(rt.right()));
	  Node<String> newRoot = rotate_RR(rt);
	  return newRoot;
  }
  
  private Node<String> rotate_LR(Node<String> rt)
  {
	  // k is inserted in rt's left subtree's right subtree
	  // one left turn around left subtree, then one right turn around rt
	  rt.setLeft(rotate_RR(rt.left()));
	  Node<String> newRoot = rotate_LL(rt);
	  return newRoot;
  }

}
