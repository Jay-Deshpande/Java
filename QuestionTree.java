// This program plays the game 20 Questions with a user by asking the user questions 
// and attempting to guess the user's object. When the program guesses wrong it learns 
// from a user provided question and answer. If the program guesses right it wins!

import java.io.*;
import java.util.*;

public class QuestionTree {
   private QuestionNode overallRoot;
   private UserInterface ui;
   private int totalGames;
   private int gamesWon;

   // ui: Input/output user interface guidelines
   // pre: Throws IllegalArgumentException if ui is null
   // post: Initializes a new question tree with the word "computer"
   public QuestionTree(UserInterface ui) {
      if (ui == null) {
         throw new IllegalArgumentException("User Interface must exist");
      }
      overallRoot = new QuestionNode("computer", null, null);
      this.ui = ui;
   }
   
   // post: Plays one complete guessing game with the user by asking the user 
   //       questions, prompting the user for answers, and eventually trying
   //       to guess the user's object
   public void play() {
      totalGames++;
      overallRoot = play(overallRoot);
   }
   
   // root: Current node on the tree
   // post: Returns the leafnode answer that the computer guesses
   private QuestionNode play(QuestionNode root) {
      if (root.left == null && root.right == null) {
         ui.print("Would your object happen to be " + root.data + "?");
         if (ui.nextBoolean()) {
            ui.println("I win!");
            gamesWon++;
            return root;
         } else {
            ui.print("I lose. What is your object?");
            String userObject = ui.nextLine();
            QuestionNode object = new QuestionNode(userObject, null, null);
            ui.print("Type a yes/no question to distinguish your item from " + root.data + ":");
            String userQuestion = ui.nextLine();
            ui.print("And what is the answer for your object?");
            Boolean userAnswer = ui.nextBoolean();
            QuestionNode newRoot;
            if (userAnswer) {
               newRoot = new QuestionNode(userQuestion, object, root);
            } else {
               newRoot = new QuestionNode(userQuestion, root, object);
            }
            return newRoot;
         }
      } else {
         ui.print(root.data);
         if (ui.nextBoolean()) {
            root.left = play(root.left);
         } else {
            root.right = play(root.right);
         }
         return root;
      }
   }
   
   // output: File in which the information is saved
   // pre: Throws IllegalArgumentException if output is null
   // post: Saves the current state of the tree to the output file in preorder traversal order
   public void save(PrintStream output) {
      save(output, overallRoot);
   }
   
   // output: File in which the information is saved
   // root: The root at which to start saving the tree state
   // pre: Throws IllegalArgumentException if output is null
   // post: Saves the current state of the tree to the output file in preorder traversal order
   private void save(PrintStream output, QuestionNode root) {
      if (output == null) {
         throw new IllegalArgumentException("The PrintStream object must exist");
      }
      if (root.left == null && root.right == null) {
         output.println("A:" + root.data);
      } else {
         output.println("Q:" + root.data);
         save(output, root.left);
         save(output, root.right);
      }
   }
   
   // input: Scanner object that reads from a file
   // pre: Throws IllegalArgumentException if input is null
   // post: Replaces the current tree with another tree contained in a file
   public void load(Scanner input) {
      overallRoot = loader(input);
   }
   
   // input: Scanner object that reads from a file
   // pre: Throws IllegalArgumentException if input is null
   // post: Replaces the current tree with another tree contained in a file
   //       Returns the root of the new tree
   private QuestionNode loader(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException("The Scanner object must exist");
      }
      String currentLine = input.nextLine();
      String[] parts = currentLine.split(":");
      QuestionNode root = new QuestionNode(parts[1]);
      if (parts[0].equals("Q")) {
         root.left = loader(input);
         root.right = loader(input);
      }
      return root;
   }
   
   // post: Returns the total number of games that have been played on the
   //       tree during the current program execution
   public int totalGames() {
      return totalGames;
   }
   
   // post: Returns the total number of games that the computer has won by
   //       correctly guessing the user's object during the current program execution
   public int gamesWon () {
      return gamesWon;
   }
}