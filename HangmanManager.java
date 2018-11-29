// This program manages a game of evil hangman. The computer delays picking
// a word until it has to. The game continues until the user is out of guesses
// or the user gets the word.

import java.util.*;

public class HangmanManager {
   private int guesses;
   private String pattern;
   private Set<String> validWords;
   private SortedSet<Character> lettersGuessed;
   
   // dictionary refers to the original set of all words
   // length refers to the length of the desired words
   // max refers to the max wrong guesses the user can make
   // pre: length must be greater than zero, max must be nonnegative
   //      (throws IllegalArgumentException if not)
   // post: creates a collection of dictionary words of the specified length
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException("length: " + length + " max: " + max);
      }
      guesses = max;
      pattern = "";
      for (int i = 0; i < length; i++) {
         pattern += " -";
      }
      validWords = new TreeSet<String>();
      for (String s : dictionary) {
         if (s.length() == length) {
            validWords.add(s);
         }
      }
      lettersGuessed = new TreeSet<Character>();
   }
   
   // post: returns current set of words being considered
   public Set<String> words() {
      return validWords;
   }
   
   // post: returns how many guesses the player has left
   public int guessesLeft() {
      return (guesses - lettersGuessed.size());
   }
   
   // post: returns current set of letters user has guessed in alphabetical order
   public SortedSet<Character> guesses() {
      return lettersGuessed;
   }
   
   // pre: set of considered words must contain at least one word
   //      (throws new IllegalArgumentException if not)
   // post: returns the current pattern to be displayed
   //       (pattern will contain dashes for letters not yet guessed)
   public String pattern() {
      if (validWords.isEmpty()) {
         throw new IllegalStateException("Number of words in set: " 
            + validWords.size());
      }
      return pattern.trim();
   }
   
   // pre: the user must have at least one guess remaining and the set of 
   //      valid words must contain at least one word
   //      (throws IllegalStateException if not)
   //      user must not have already guessed that letter
   //      (throws IllegalArgumentException if not)
   //      guess refers to the user's guessed letter
   // post: returns the frequency of the guessed letter in the word
   //       updates the user's guess count
   public int record(char guess) {
      if (guesses < 1 || validWords.isEmpty()) {
         throw new IllegalStateException("Guesses left: " + guesses 
            + " Number of words in set: " + validWords.size());
      } else if (!validWords.isEmpty() && lettersGuessed.contains(guess)) {
         throw new IllegalArgumentException("You have already guessed " + guess);
      }
      Map<String, SortedSet<String>> groupings 
         = new TreeMap <String, SortedSet<String>>();
      createPatterns(guess, groupings);
      return findNextSet(guess, groupings);
   }
   
   // pre: guess refers to the user's guessed letter.
   //      groupings maps the pattern of an individual word to all words
   //      with that pattern.
   // post: creates groups of words with the same patterns 
   private void createPatterns(char guess, Map<String, SortedSet<String>> groupings) {
      for (String word : validWords) {
         String oneWordPattern = "";
         for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
               oneWordPattern += " " + guess;
            } else {
               oneWordPattern += " -";
            }
         }
         oneWordPattern.trim();
         if (!groupings.containsKey(oneWordPattern)) {
            groupings.put(oneWordPattern, new TreeSet<String>());
         }
         groupings.get(oneWordPattern).add(word);
      }
   }
   
   // pre: guess refers to the user's guessed letter.
   //      groupings maps the pattern of an individual word to all words
   //      with that pattern.
   // post: returns the count of the guessed letter in the chosen word.
   //       chooses the next set of words for the game
   private int findNextSet(char guess, Map<String, SortedSet<String>> groupings) {
      lettersGuessed.add(guess);
      int largestGroup = 0;
      String largestGroupPattern = "";
      for (String tempPattern : groupings.keySet()) {
         if (groupings.get(tempPattern).size() > largestGroup) {
            largestGroup = groupings.get(tempPattern).size();
            largestGroupPattern = tempPattern;
            validWords = groupings.get(tempPattern);
         }
      }
      pattern = updatePattern(largestGroupPattern, pattern);
      int count = 0;
      for (int i = 0; i < largestGroupPattern.length(); i++) {
         if (largestGroupPattern.charAt(i) == guess) {
            count++;
         }
      }
      return count;
   }
   
   // pre: pattern refers to the previous word pattern.
   //      largestGroupPattern refers to the new pattern of the 
   //      guessed letters in the words.
   // post: returns an update of the previous pattern and the 
   //       pattern of the guessed letter in the word
   private String updatePattern(String largestGroupPattern, String pattern) {
      boolean letterWasGuessed = false;
      String tempPattern = "";
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) != largestGroupPattern.charAt(i) 
               && largestGroupPattern.charAt(i) != '-') {
            tempPattern += largestGroupPattern.charAt(i);
            letterWasGuessed = true;
         } else {
            tempPattern += pattern.charAt(i);
         }
      }
      if (letterWasGuessed) {
         guesses++;
      }
      return tempPattern;
   }
}