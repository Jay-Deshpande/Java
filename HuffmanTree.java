// This program enables the compression of sequences of ASCII characters in order to 
// reduce the number of bytes a file occupies. 

import java.io.*;
import java.util.*;

public class HuffmanTree {
   private HuffmanNode root;
   
   // counts: Collection of character frequencies
   // post: Creates a Huffman Tree representing the compressed bit values of
   //       each character
   public HuffmanTree(int[] counts) {
      PriorityQueue<HuffmanNode> sortedChars = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] > 0) { // ignore characters that don't occur
            HuffmanNode node = new HuffmanNode(i, counts[i]);
            sortedChars.add(node);
         }
      }
      sortedChars.add(new HuffmanNode(counts.length, 1)); // end of file character
      while (sortedChars.size() > 1) {
         HuffmanNode first = sortedChars.remove();
         HuffmanNode second = sortedChars.remove();
         HuffmanNode parent = new HuffmanNode(-1, first.count + second.count, first, second);
         sortedChars.add(parent);
      }
      root = sortedChars.remove();
   }
   
   // output: File in which the information is saved
   // post: Writes sequence of the pattern - line with ASCII 
   //       value of a character followed by a line with the 
   //       Huffman-produced code for that character.
   public void write(PrintStream output) {
      write(output, root, "");
   }
   
   // output: File in which the information is saved
   // node: Current node in the Huffman Tree
   // code: Huffman compressed code representing a character
   // post: Writes line with ASCII value of a character followed by a line
   //       with the Huffman-produced code for that character
   private void write(PrintStream output, HuffmanNode node, String code) {
      if (node.data == -1) { // intermediate node
         write(output, node.left, code + "0");
         write(output, node.right, code +"1");
      } else {
         output.println(node.data);
         output.println(code);
      }
   }
   
   // input: Representation of a Huffman tree stored in standard format
   // post: Reconstructs a Huffman tree from a code file
   public HuffmanTree(Scanner input) {
      root = new HuffmanNode(-1, 0);
      while (input.hasNextLine()) {
         int data = Integer.parseInt(input.nextLine());
         String code = input.nextLine(); // throws NoSuchElementException if line does not exist
         HuffmanNode node = root;
         for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
               if (node.left == null) {
                  node.left = new HuffmanNode(-1, 0);
               }
               node = node.left;
            } else {
               if (node.right == null) {
                  node.right = new HuffmanNode(-1, 0);
               }
               node = node.right;
            }
         }
         node.data = data;
      }
   }
   
   // input: Huffman bit representation of a sequence of characters 
   // output: File in which the information is saved
   // eof: Represents the end of file character at which the decoding stops
   // post: Reads bits from the input and writes the corresponding characters to the output
   //       until the end of file character is reached.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      // Decodes the characters until eof is reached
      while(decodeChar(input, output, eof, root)) {}
   }
   
   // input: Huffman bit representation of a sequence of characters 
   // output: File in which the information is saved
   // eof: Represents the end of file character at which the decoding stops
   // node: Current node in the Huffman tree
   // post: Reads bits from the input and writes the corresponding character to the output
   //       Returns false if eof is encountered, otherwise returns true
   private boolean decodeChar(BitInputStream input, PrintStream output, 
         int eof, HuffmanNode node) {
      if (node.data >= 0) {
         if (node.data == eof) {
            return false;
         }
         output.write(node.data);
         return true;
      }
      if (input.readBit() == 0) {
         return decodeChar(input, output, eof, node.left);
      } else {
         return decodeChar(input, output, eof, node.right);
      }
   } 
   
   // This class stores ASCII values of characters and their relative frequencies
   // for use in a HuffmanTree
   private class HuffmanNode implements Comparable<HuffmanNode> {
      private int count; // frequency of character
      private int data; // ASCII value of character, -1 for intermediate node
      private HuffmanNode left;
      private HuffmanNode right;
      
      // data: Given ASCII value
      // count: Given frequency
      public HuffmanNode(int data, int count) {
         this(data, count, null, null);
      }
      
      // data: Given ASCII value
      // count: Given frequency
      // left: Reference to left subtree
      // right: Reference to right subtree
      public HuffmanNode(int data, int count, HuffmanNode left, HuffmanNode right) {
         this.data = data;
         this.count = count;
         this.left = left;
         this.right = right;
      }
      
      // other: HuffmanNode with which to compare
      // post: Returns difference between the two HuffmanNode character frequencies
      public int compareTo(HuffmanNode other) {
         return (this.count - other.count);
      }
   }
}