/*
 * SaveDate.java
 *
 * Created on May 25, 2007, 11:09 AM
 */

package huffman;
import java.io.*;
/**
 *<pre>
 *              Save Data
 * File:        SaveData.java
 * Description: UNUSED Alternative class for data and occurance storage
 * @author      James Hua, Blake Hashimoto, Roger Vargas, Wei Jiang
 * @since       6/1/15
 * Special Thanks to
 * Paul Bladek
 * </pre>
 */
public class SaveData implements Serializable
{
    private char data = '\0';
    private short occurrances = 0;

    /**
     * Creates a new instance of SaveData
     */
    public SaveData() {}
    
    /** Creates a new instance of SaveDate
     * @param c the data char
     * @param o the number of occurances
     */
    public SaveData(char c, short o)
    {
        data = c;
        occurrances = o;
    }

    /**
     * accessor for data
     * @return data
     */
    public char getData()
    {
        return data;
    }

    /**
     * accessor for occurrances
     * @return data
     */
    public short getOccurrances()
    {
        return occurrances;
    }
    /**
     * @return a string version of this class
     */
    @Override
    public String toString()
    {
        return data + ":" + occurrances + " ";
    }   
}
