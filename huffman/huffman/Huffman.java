/*
 * Huffman.java
 *
 * Created on May 21, 2007, 1:01 PM
 */

package huffmantree;
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
    private String directory;
    private HuffmanTree<Character> theTree;
    private byte[] byteArray;
    private SortedMap<Character, String> keyMap;
    private SortedMap<String, Character> codeMap;
    private String extension;
    private String desc;
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
//        args[0] = "src/huffmantree/alice.txt";
//----------------------------------------------------
// used for debugging encoding
//----------------------------------------------------
//        args = new String[2];
//        args[0] = "-d";
//        args[1] = "alice.huf";  
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
        try{
            if(!decode)
                coder.encode(textFileName);
            else
                coder.decode(textFileName);
            }
            catch(Exception e){
                System.out.println(e.getClass() + e.getMessage());  
                e.printStackTrace();
        }
            
    }

    /*
     * encode
     * @param fileName the file to encode
     */
    public void encode(String fileName)
    {
        extension = "txt";
        desc = "Text File";
        File inputFile = new File(fileName);
            if (!((inputFile.exists()) && (inputFile.canRead()))) {
                inputFile = getFile();
                fileName =inputFile.getName();
            }
            else {
            inputFile = getFile();
            fileName = inputFile.getName();  
        }
        directory = inputFile.getParent();
        int[] counter = new int[CHARMAX];  
        Scanner in = null;
        List<HuffmanChar> countList = new ArrayList<>();
        try
        {
          in = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File open error");
            return;
        }
        while(in.hasNextLine())
        {
            String line = in.nextLine();
            line += "\n";
            for (int i = 0; i < line.length(); i++){
                Character a = line.charAt(i);
                counter[a]++;
            }
            
        }
        for(int i = 0; i < counter.length; i ++)
        {
            if(counter[i] > 0)
            {
                countList.add(new HuffmanChar((char)i, counter[i]));
            }
        }
        
        charCountArray = countList.toArray(new HuffmanChar[countList.size()] );
        countList = null;
        Arrays.sort(charCountArray);
        theTree = new HuffmanTree<>(charCountArray);
        keyMap = theTree.getCodeMap();
        codeMap = theTree.getKeyMap();
        System.out.println(""+keyMap);
        System.out.println(""+ codeMap);
        try
        {
          in = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File open error");
        }
        ArrayList<Byte> arrayList = new ArrayList<>();
        String output = "";
        String outputByte = null;
        while(in.hasNextLine())
        {
             String line = in.nextLine();
             line += "\n";
             for(int i = 0; i < line.length(); i++)
             {
                output += keyMap.get(line.charAt(i));
                while(output.length() > CHARBITS)
                {
                     outputByte = output.substring(0,8);
                     output = output.substring(8);
                     arrayList.add((byte)Integer.parseInt(outputByte, 2));
                }
            }
            
        }
        while(output.length() != 0 && output.length() < 8)
            output += "0";
        arrayList.add((byte)Integer.parseInt(outputByte));
        byteArray = toArray(arrayList);
        arrayList = null;
        System.out.println(byteArray.length);
        
        


       File hufFile = writeEncodedFile(byteArray, fileName);
       System.out.println(((float)hufFile.length() / inputFile.length() * 100.0) + "%");
        writeKeyFile(fileName);
    } 
    /**
     *Takes List and converts it to primitive byte array
     * @param arrayList of Byte to convert
     * @return byte[] array
     */
    public byte[] toArray(List<Byte> arrayList)
    {
        byte[] bytesArray = new byte[arrayList.size()];
        for( int i = 0; i < arrayList.size(); i++ )
        {
            bytesArray[i] = arrayList.get(i);
        }
        return bytesArray;
    }
     /**
     *  Creates an array of bytes from a code file containing instructions for
     * a huffman tree
     * @param fileName String file with huffman tree code
     * @return HuffmanData[] Array of huffman values
     * @throws java.io.FileNotFoundException Thrown if file is not found
     * @throws java.io.IOException Thrown if there is a file error
     * @throws java.lang.ClassNotFoundException thrown if there is a class error
     */
    public HuffmanData<Character>[] decodeCode(String fileName) 
            throws FileNotFoundException, IOException, ClassNotFoundException
    {
        HuffmanData<Character>[] allHuffman = null;
        try (ObjectInputStream readObject = new ObjectInputStream(new FileInputStream(fileName))) {
            allHuffman = new HuffmanChar[readObject.available() / 3];
            byte[] recoverData = new byte[readObject.available()];
            readObject.readFully(recoverData);
            for (int i = 0; i < recoverData.length; i += 3){
                byte[] holdThree = {recoverData[i], recoverData[i + 1], recoverData[i + 2]};
                HuffmanChar temp = new HuffmanChar(holdThree);
                allHuffman[i / 3] = temp;
            }
            readObject.close();
        }
        return allHuffman;
    }
    /*
     * decode
     * @param inFileName the file to decode
     */   
    public void decode(String inFileName) 
            throws FileNotFoundException, IOException, ClassNotFoundException
    { 
        extension = "huf";
        desc = "HUF File";
        File inFile = new File(inFileName);
            if (!((inFile.exists()) && (inFile.canRead()))) {
                inFile = getFile();
                inFileName =inFile.getName();
            }
            else {
            inFile = getFile();
            inFileName = inFile.getName();  
        }
            directory = inFile.getParent();
        String outFileName = directory + "\\" + inFileName.replaceAll("\\Q.huf\\E", ".txt");
        File outFile = new File(outFileName); 
        
        int overSize = 0;
        FileReader reader = new FileReader(inFile);
        BufferedReader readCode = new BufferedReader(reader);
        String line = readCode.readLine();
        
        
        try (PrintWriter out = new PrintWriter(outFile)) {
            if (codeMap == null)
                theTree = new HuffmanTree<>(decodeCode(
                        directory + "\\" + inFileName.replaceAll("\\Q.huf\\E", ".cod")));
            codeMap = theTree.getKeyMap();
            System.out.println(codeMap);
//            ArrayList<Byte> saveData = new ArrayList<>();
//            String overflow = "";
//            while (line != null){
//                line = overflow + line;
//                overflow = "";
//                if (line.length() % 8 != 0){
//                    int overLength = line.length() % 8;
//                    overflow = line.substring(line.length() 
//                            - overLength, line.length());
//                    line = line.substring(0, line.length() - overLength);
//                }
//                while (line.length() >= 8){
//                    String holdEight = line.substring(0, 8);
//                    if (line.length() > 8)
//                        line = line.substring(8);
//                    else{
//                        line = "";
//                    }
//                    byte addValue = getByteValue(holdEight);
//                    saveData.add(addValue);
//                }
//                line = readCode.readLine();
//            }
//            if (overflow.length() > 0){
//                overSize = overflow.length();
//                while (overflow.length() < 8){
//                    overflow += '0';
//                }
//                saveData.add(getByteValue(overflow));
//            }
            ObjectInputStream readObj = new ObjectInputStream(new FileInputStream(inFile));
            System.out.println(readObj.available());
            saveDataArray = new byte[readObj.available()];
            readObj.readFully(saveDataArray);
//            saveDataArray = toArray(saveData);
//            saveData = null;
            char[] addChar = traverseTree(saveDataArray, overSize); 
            StringBuilder lineOut = new StringBuilder("");
            for (int i = 0; i < addChar.length; i++){
                if (addChar[i] == '\n'){
                    out.println(lineOut);
                    lineOut.delete(0, lineOut.length());
                }
                else{
                    lineOut.append(addChar[i]);
                }
            }
            if (lineOut.length() > 0)
                out.println(lineOut);
            lineOut = null;
            out.close();
        } 
    }
    /**
     * Takes a byte array and traverses the huffman tree to get the
     * corresponding byte value
     * @param data byte[] Array of bytes containing all the keys
     * @param overflow int overflow for the last index to ignore
     * @return 
     */
    public char[] traverseTree(byte[] data, int overflow){
        ArrayList<Character> allChars = new ArrayList<>();
        StringBuilder value = new StringBuilder("");
        StringBuilder test = new StringBuilder("");
        for (int i = 0; i < data.length; i++){
            String getVal = Integer.toBinaryString(data[i]);
            int adjust = getVal.length() - Byte.SIZE;
            if (adjust > 0)
                getVal = getVal.substring(adjust);
            else if (adjust < 0){
                while (getVal.length() < 8)
                    getVal = "0" + getVal;
            }
            if (i == data.length - 1)
                getVal = getVal.substring(0, overflow);
            value.append(getVal);
            while (value.length() > 0){
                test.append(value.substring(0, 1));
                value = new StringBuilder(value.substring(1));
                Character fromKey = codeMap.get(test.toString());
                if (fromKey != null){
                    test.delete(0, test.length());
                    allChars.add(fromKey);
                }
            }
        }
        char[] charsArray = new char[allChars.size()];
        for (int i = 0; i < allChars.size(); i++)
            charsArray[i] = allChars.get(i);
        allChars = null;
        return charsArray;
    }
    /**
     * Gets byte value for a string
     * precondition: length of string is between 0 and 8 characters
     * Note: Method will still work this precondition is for the purpose of the
     * program
     * @param getVals String of binary values to be read in
     * @return byte representation of the character at a given position
     */
    public byte getByteValue(String getVals){
        byte stringToByte = 0;
        for (int i = getVals.length() - 1; i >= 0; i--){
            int adjustment = i + 1;
            char byteAtPos = getVals.charAt(getVals.length() - adjustment);
            byte readIn = 0;
            if (byteAtPos == '1')
                readIn = (byte)1;
            readIn <<= i;
            stringToByte |= readIn;
        }
        return stringToByte;
    }
      
    /**
     * writeEncodedFile
     * @param bytes bytes for file
     * @param fileName file input
     */ 
    public File writeEncodedFile(byte[] bytes, String fileName)
    {
        String encodeFileName =
                fileName.substring(0, fileName.lastIndexOf(".")) + ".huf";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(directory + "\\" + encodeFileName))) {
            outputStream.write(bytes);
            outputStream.close();
        }
        
        catch(IOException e)
        {
            System.out.println("File open error");
        }
        return new File(encodeFileName);
    }
   
    /**
     * writeKeyFile
     * @param fileName the name of the file to write to
     */
    public void writeKeyFile(String fileName)
    {
        String keyFileName =
                fileName.substring(0, fileName.lastIndexOf(".")) + ".cod";
        saveDataArray = new byte[charCountArray.length * 3];
        for(int i = 0; i < charCountArray.length; i++)
        {
            byte[] threeByteArray = charCountArray[i].toThreeBytes();
            saveDataArray[3 * i] = threeByteArray[0];
            saveDataArray[3 * i + 1] = threeByteArray[1];
            saveDataArray[3 * i + 2] = threeByteArray[2];
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(directory + "\\" + keyFileName))) {
            for (int i = 0; i <saveDataArray.length; i++)
            {
                outputStream.writeByte(saveDataArray[i]);
               
            }
            outputStream.close();
        }
        catch(IOException e)
        {
            System.out.println("File open error");
        }
    }
     /**
     * The method to get the file.
     * @return the selected file
     */
    private File getFile(){
         String inputFileName = "x";
        File inputFile = new File(inputFileName);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter exten = new FileNameExtensionFilter(desc, extension);
        chooser.setFileFilter(exten);
        chooser.setCurrentDirectory(inputFile.getAbsoluteFile()
                .getParentFile());
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            inputFile = chooser.getSelectedFile();
            if(!inputFile.exists() || !inputFile.canRead()){
                JOptionPane.showMessageDialog(null,"Can not read this file,"
                        + " please try another");
                int choice = JOptionPane.showConfirmDialog(null, "Choose Another File?", 
                    "File Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice == JOptionPane.YES_OPTION)
                    getFile();
            }
        }
        return inputFile;
    }
 
}
