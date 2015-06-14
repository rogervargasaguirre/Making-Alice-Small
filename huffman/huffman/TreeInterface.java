/*
 * HuffmanData.java
 *
 * Created on May 21, 2007, 2:17 PM
 */

package huffman;

/**
 *<pre>
 *              TreeInterface.java
 * File:        TreeInterface.java
 * Description: Basic interface for binary tree
 * @author      James Hua, Blake Hashimoto, Roger Vargas, Wei Jiang
 * @since       6/1/15
 * Special Thanks to
 * Carrano
 * </pre>
 */
public interface TreeInterface<T>
{
   public T getRootData();
   public int getHeight(); 
   public  int getNumberOfNodes();
   public boolean isEmpty(); 
   public void clear();   
}
