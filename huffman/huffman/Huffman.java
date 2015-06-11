/*
 * Huffman.java
 *
 * Created on May 21, 2007, 1:01 PM
 */

package huffman;
import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *<pre>
 *              Huffman Tree Compression
 * File:        Huffman.java
 * Description: The purpose of this program is to compress a txt file using a 
 *              huffman compression scheme. The program may also decode a 
 *              huffman file when a -d flag is used
 * @author      James Hua, Blake Hashimoto, Roger Vargas, Wei Jiang
 * @since       6/1/15
 * log:         6/1/15 Created the encode method
 *              6/5/15 Created the decode method
 * Special Thanks to
 * Paul Bladek
 * </pre>
 */
public class Huffman
{  
    public static final int CHARMAX = 128;
    public static final byte CHARBITS = 7;
    public static final short CHARBITMAX = 128;
    private HuffmanTree<Character> theTree;
    private byte[] byteArray;
    private SortedMap<Character, String> keyMap;
    private SortedMap<String, Character> codeMap;
    HuffmanChar[] charCountArray;
    byte[] saveDataArray;
    
    /**
     * Creates a new instance of Main
     */
    public Huffman() {}
    
    /**
     * main
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
//----------------------------------------------------
// used for debugging encoding
//----------------------------------------------------
//        args = new String[1];
//        args[0] = "alice.txt";
//----------------------------------------------------
// used for debugging encoding
//----------------------------------------------------
//        args = new String[2];
//        args[0] = "-d";
//        args[1] = "alice.txt";  
//----------------------------------------------------        
        boolean decode = false;
        String textFileName = "";
        if(args.length > 0)
        {
            if(args[0].substring(0,2).toLowerCase().equals("-d"))
            {
                decode = true;
                if(args.length > 1)
                    textFileName = args[1];
            }
            else
                textFileName = args[0];
        }
        Huffman coder = new Huffman();
        if(!decode)
            coder.encode(textFileName);
        else
            coder.decode(textFileName);
    }

    /*
     * encode
     * @param fileName the file to encode
     */
    public void encode(String fileName)
    {
        File inputFile = new File(fileName);
            if (!((inputFile.exists()) && (inputFile.canRead()))) {
                inputFile = getFile();
                fileName =inputFile.getName();
      // YOUR CODE HERE
            }
            else {
            inputFile = getFile();
            fileName = inputFile.getName();  
        }


        writeEncodedFile(byteArray, fileName);
        writeKeyFile(fileName);
    } 
 
    /**
     *  Creates an array of bytes from a code file containing instructions for
     * a huffman tree
     * @param fileName String file with huffman tree code
     * @return byte[] Array of byte values
     */
    public byte[] decodeCode(String fileName){
        return new byte[0];
    }
    /*
     * decode
     * @param inFileName the file to decode
     */   
    public void decode(String inFileName) throws FileNotFoundException, IOException
    { 
       String lineOut = "";
        File inFile = new File(inFileName);
        String outFileName = inFileName.replaceAll("\\Q.huf\\E", ".txt");
        File outFile = new File(outFileName); 
        
        FileReader reader = new FileReader(inFile);
        BufferedReader readCode = new BufferedReader(reader);
        String line = readCode.readLine();
        ArrayList<Byte> saveData = new ArrayList<>();
        Byte valueOfLine = -1;
        
        try (PrintWriter out = new PrintWriter(outFile)) {
            if (keyMap == null)
                theTree = new HuffmanTree<>(decodeCode(inFileName));
            codeMap = theTree.getKeyMap();
            String overflow = "";
            while (line != null){
                line = overflow + line;
                overflow = "";
                if (line.length() % 8 != 0){
                    int overLength = line.length() % 8;
                    overflow = line.substring(line.length() - overLength, line.length());
                    line = line.substring(0, line.length() - overLength);
                }
                while (line.length() >= 8){
                    String holdEight = line.substring(0, 8);
                    if (line.length() > 8)
                        line = line.substring(8);
                    else{
                        line = "";
                    }
                    byte stringToByte = 0;
                    for (int i = 7; i >= 0; i--){
                        char byteAtPos = holdEight.charAt(7 - i);
                        byte readIn = 0;
                        if (byteAtPos == '1')
                            readIn = (byte)1;
                        readIn <<= i;
                        stringToByte |= readIn;
                    }
                    saveData.add(stringToByte);
                }
                line = readCode.readLine();
            }
            saveDataArray = new byte[saveData.size()];
            for (int i = 0; i < saveData.size(); i++)
                saveDataArray[i] = saveData.get(i);
            saveData = null;
            
            for (int i = 0; i < saveDataArray.length; i++){
                Character addChar = codeMap.get(saveDataArray[i]);
                if (addChar == '\n'){
                    out.println(lineOut);
                    lineOut = "";
                }
                else{
                    lineOut += addChar;
                }
            }
        } 
    }
      
    /**
     * writeEncodedFile
     * @param bytes bytes for file
     * @param fileName file input
     */ 
    public void writeEncodedFile(byte[] bytes, String fileName)
    {
      

    }
   
    /**
     * writeKeyFile
     * @param fileName the name of the file to write to
     */
    public void writeKeyFile(String fileName)
    {
  
    }
         /**
     * The method to get the file.
     * @return the selected file
     */
    private static File getFile(){
        String inputFileName = "x";
        File inputFile = new File(inputFileName);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter exten = new FileNameExtensionFilter("Text Document", "txt");
        chooser.setFileFilter(exten);
        chooser.setCurrentDirectory(inputFile.getAbsoluteFile()
                .getParentFile());
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            inputFile = chooser.getSelectedFile();
            if(!inputFile.exists() || !inputFile.canRead()){
                JOptionPane.showMessageDialog(null,"Can not read this file,"
                        + " please try another");
                getFile();
            }
        }
        return inputFile;
    }
 
}

