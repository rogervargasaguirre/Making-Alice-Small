/*
 * SaveDate.java
 *
 * Created on May 25, 2007, 11:09 AM
 */

package huffman;
import java.io.*;
/**
 * class for saving the initial sorted array
 *  of data/occurances
 * @author pbladek
 */
public class SaveData implements Serializable
{
    private char data = '\0';
    private short occurrances = 0;

    /**
     * Creates a new instance of SaveDate
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
