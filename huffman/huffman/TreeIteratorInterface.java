/*
 * TreeIteratorInterface.java
 *
 * Created on May 21, 2007, 1:31 PM
 */

package huffman;
import java.util.*;
/**
 *<pre>
 *              Tree Iterator Interface
 * File:        TreeIteratorInterface.java
 * Description: Provide methods that a tree iterator must implemen
 * @author      James Hua, Blake Hashimoto, Roger Vargas, Wei Jiang
 * @since       6/1/15
 * log:         6/1/15 Created the encode method
 *              6/5/15 Created the decode method
 * Special Thanks to
 * Paul Bladek
 * </pre>
 */
public interface TreeIteratorInterface<T>
{
    public Iterator<T> getInOrderIterator();
}
