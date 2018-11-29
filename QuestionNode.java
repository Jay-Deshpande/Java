// This program stores either a question or answer node in the binary tree.
// Each question node points to either another question or an answer leaf.

public class QuestionNode {
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   
   // data: Given data
   // post: Constructs a leaf with the given data
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // data: Given data
   // left: Reference to left subtree
   // right: Reference to right subtree
   // post: Constructs a branch or leaf with the given data and references
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}