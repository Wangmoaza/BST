import java.lang.Math;

public class AVL extends BST
{
  public AVL() {  }
  
  public void insert(String key) 
  {
	  root = inserthelp(root, key);
  }
  
  protected Node inserthelp(Node rt, String k)
  {
	  // modification from lecture note
	  if (rt == null) // base case 1: insert new node
		  rt = new Node(k);
	  
	  else if (rt.key().compareTo(k) < 0) // rt is smaller than k
	  {
		  rt.setRight(inserthelp(rt.right(), k));
		  if (height(rt.right()) - height(rt.left()) > 1) // unbalanced: right is taller than left by 2
		  {
			  if (rt.right().key().compareTo(k) < 0)
				  rt = rotate_RR(rt); // k is inserted in rt's right subtree's right subtree
			  else
				  rt = rotate_RL(rt); // k is inserted in rt's right subtree's left subtree
		  }  
	  }
	  
	  else if (rt.key().compareTo(k) > 0) // rt is bigger than k
	  {
		  rt.setLeft(inserthelp(rt.left(), k));
		  if (height(rt.left()) - height(rt.right()) > 1) // unbalanced: left is taller than right by 2
		  {
			  if (rt.left().key().compareTo(k) > 0)
				  rt = rotate_LL(rt); // k is inserted in rt's left subtree's left subtree
			  else
				  rt = rotate_LR(rt); // k is inserted in rt's left subtree's right subtree
		  }
	  }
	  
	  else // base case 2: key already exists, increase frequency
		  rt.increaseFreq();
	 
	  rt.setHeight( 1 + Math.max( height(rt.left()), height(rt.right()) ) );
	  return rt;
  }
  
  private Node rotate_RR(Node rt)
  {
	  // k is inserted in rt's right subtree's right subtree
	  // one left turn around rt
	  Node child = rt.right();
	  rt.setRight(child.left());
	  child.setLeft(rt);
	  
	  // update height
	  rt.setHeight( 1 + Math.max( height(rt.left()), height(rt.right()) ) );
	  child.setHeight( 1 + Math.max( height(child.left()), height(child.right()) ) );
	  return child; // return the new root
  }
  
  private Node rotate_LL(Node rt)
  {
	  // k is inserted in rt's left subtree's left subtree
	  // one right turn around rt
	  Node child = rt.left();
	  rt.setLeft(child.right());
	  child.setRight(rt);
	  
	  // update height
	  rt.setHeight( 1 + Math.max( height(rt.left()), height(rt.right()) ) );
	  child.setHeight( 1 + Math.max( height(child.left()), height(child.right()) ) );
	  return child; // return the new root
  }
  
  private Node rotate_RL(Node rt)
  {
	  // k is inserted in rt's right subtree's left subtree
	  // one right turn around right subtree, then one left turn around rt
	  rt.setRight(rotate_LL(rt.right()));
	  Node newRoot = rotate_RR(rt);
	  return newRoot;
  }
  
  private Node rotate_LR(Node rt)
  {
	  // k is inserted in rt's left subtree's right subtree
	  // one left turn around left subtree, then one right turn around rt
	  rt.setLeft(rotate_RR(rt.left()));
	  Node newRoot = rotate_LL(rt);
	  return newRoot;
  }

  private int height(Node rt)
  {
	  if (rt == null) return 0;
	  return rt.getHeight();
  }
}
