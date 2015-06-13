/*
 * HuffmanTree.java
 *
 * Created on May 21, 2007, 2:16 PM
 */

package Huffman;
import java.util.*;
/**
 * binary tree for Huffman coding
 * @author pbladek
 */
public class HuffmanTree<T extends Comparable<? super T>>
        extends BinaryTree<HuffmanData<T>>
{
    private final T MARKER = null;
    SortedMap<T, String> codeMap;
    SortedMap<String, T> keyMap;
    
    private int leafCount = 0;
    
    /**
     * Creates a new instance of HuffmanTree
     */
    public HuffmanTree() 
    {
        super();
    }
   
    /**
     * Creates a new instance of HuffmanTree
     * from an array of Huffman Data
     * @param dataArray n array of Huffman Data
     */
    public HuffmanTree(HuffmanData<T>[] dataArray) 
    {
        // your code here
        super();  
        LinkedList<BinaryNode<HuffmanData<T>>> nodeList 
                = new LinkedList<>();    
        for (HuffmanData<T> element : dataArray) {
            BinaryNode<HuffmanData<T>> node = new BinaryNode<>(element);    
            nodeList.add(node);  
        }     
        
    while(nodeList.size() > 1)    
       {    
         boolean addBack = false;
         this.add(nodeList.poll(),nodeList.poll()); 
         for(int i =0; i < nodeList.size()&!addBack; i++){
            if(nodeList.get(i).getData().compareTo(getRootData()) > 0){
                nodeList.add(i,(BinaryNode<HuffmanData<T>>)this.getRootNode());
                addBack = true;
                break;
            }
            }
         if(!addBack){
        nodeList.add(0,(BinaryNode<HuffmanData<T>>)this.getRootNode());
         }
        //new node add to nodeQueue
       }    
        keyMap = new TreeMap<>();
        codeMap = new TreeMap<>();
        setMaps(getRootNode(), "");
        System.out.println("" + leafCount);
        System.out.println("" + this.getHeight());
    }
    
    /** 
     * creates two new HuffmanTrees and adds them to the root of this tree
     * @param left 
     * @param rightt
     */
    private void add(BinaryNode<HuffmanData<T>> left,
            BinaryNode<HuffmanData<T>> right)
    {
         HuffmanTree<T> leftTree = new HuffmanTree<T>();
         leftTree.setRootNode(left); 
         HuffmanTree<T> rightTree = new HuffmanTree<T>();
         rightTree.setRootNode(right);
         setTree(new HuffmanData<T>
                 (MARKER, left.getData().getOccurances()
                 + right.getData().getOccurances()), leftTree, rightTree);
        // add to queue, root
        //sort queue
    }
    
    /** 
     * adds 2 new elements to this tree<br>
     *  smaller on the left
     * @param element1
     * @param element2
     */
    private void firstAdd(HuffmanData<T> element1, HuffmanData<T> element2)
    {
        

    }
    
    /** 
     * add a single element to the tree
     *  smaller on the left
     * @param element1
     */
     private void add(HuffmanData<T> element1)
     {

       
     }
    
     /** 
      * set up the 2 maps
      * @param node
      * @param codeString
      */
     private void setMaps(BinaryNodeInterface<HuffmanData<T>> node,
             String codeString)
     { 
        if(node == null)
            return;
        if(codeString == null)
            codeString = "";
        BinaryNodeInterface<HuffmanData<T>> left = node.getLeftChild();
        BinaryNodeInterface<HuffmanData<T>> right = node.getRightChild();
        if(left != null)
            setMaps(left, codeString + '0');
        if(right != null)
            setMaps(right, codeString + '1');   
        if(!node.hasLeftChild() && !node.hasRightChild())
        {
            codeMap.put(node.getData().getData(), codeString);
            keyMap.put(codeString, node.getData().getData());
            leafCount++;
        }          
     }
    
    /*
     * accessor for codeMap
     * @ return codeMap
     */
    public SortedMap<T, String> getCodeMap()
    {
        return codeMap;
    }
    
    /*
     * accessor for keyMap
     * @ return keyMap
     */
    public SortedMap<String, T> getKeyMap()
    {
        return keyMap;
    }

}
