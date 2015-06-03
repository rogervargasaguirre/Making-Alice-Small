# Making-Alice-Small

Things to do:

Encoding:
open the file
read it through and attach "\n" to the end of each line
count the character types in each line and save to array
go through that array and put each element in an array with a special class
construct aHuffmanTree
set up a SortedMap<Character(char represenation of the chars), String (binary digits of pathway to each node)>
set up another SortedMap<String, Character> this time
read in the original file again using the map to set up a binary String version of the coded text
translate this into an array of bytes
save this to a file with the name original.huf
the array of character/count should also be saved as original.cod
output <filename>.huf : <compression ratio>% compression

Decoding:
open the .cod file and recreate array of character/count
pass to the HuffmanTree class constructor
this will reconstruct the original tree with its maps
the .huf file will then be read into an array of bytes
to do this, go through each bit to find the leaf nodes, 
when \n is encountered the String should be written back out to a file with the same name as the original file
this file should be replaced with "x.txt"
