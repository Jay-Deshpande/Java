// This program utilizes a dictionary to find all anagram phrases that
// match the given word or phrase.  

import java.util.*;

public class Anagrams {
   private Set<String> allWords;

   // dictionary: Given collection of words 
   // pre: Throws IllegalArgumentException if dictionary is null
   // post: Initializes a new anagram solver using the given dictionary of words
   public Anagrams(Set<String> dictionary) {
      if (dictionary == null) {
         throw new IllegalArgumentException("The dictionary must exist");
      }
      allWords = dictionary;
   }
   
   // phrase: Given phrase
   // pre: Throws IllegalArgumentException if phrase is null
   // post: Returns an alphabetically sorted set containing all words from the
   //       dictionary that can be made using the letters in phrase
   public SortedSet<String> getWords(String phrase) {
      if (phrase == null) {
         throw new IllegalArgumentException("The phrase must exist");
      }
      SortedSet<String> words = new TreeSet<String>();
      LetterInventory letters = new LetterInventory(phrase);
      for (String currentWord : allWords) {
         if (letters.contains(currentWord)) {
            words.add(currentWord);
         }
      }
      return words;
   }
   
   // phrase: Given phrase
   // pre: Throws IllegalArgumentException if phrase is null
   // post: Prints all anagrams that can be formed using every letter in phrase
   public void print(String phrase) {
      if (phrase == null) {
         throw new IllegalArgumentException("The phrase must exist");
      }
      print(phrase, 0);
   }
   
   // phrase: Given phrase
   // max: Maximum number of words to print in each anagram (if 0, no maximum)
   // pre: Throws IllegalArgumentException if phrase is null or if max is less than zero
   // post: Prints all anagrams that can be formed using every letter in phrase
   //       and that include at most max words total 
   public void print(String phrase, int max) {
      if (phrase == null || max < 0) {
         throw new IllegalArgumentException("The phrase must exist and max must be nonnegative");
      }
      ArrayList<String> anagrams = new ArrayList<String>();
      SortedSet<String> possibleWords = getWords(phrase);
      LetterInventory letters = new LetterInventory(phrase);
      print(max, anagrams, possibleWords, letters);
   }
   
   // max: Maximum number of words to print in each anagram (if 0, no maximum)
   // anagrams: List of anagrams formed using all of the letters of a given phrase
   // possibleWords: Words in the dictionary that can be formed using remaining letters
   // letters: All letters from the given phrase
   // post: Prints all anagrams that can be formed using every letter in letters and
   //       that include at most max words total
   private void print(int max, ArrayList<String> anagrams,
         SortedSet<String> possibleWords, LetterInventory letters) {
      if (letters.isEmpty() && anagrams.size() <= max || letters.isEmpty() && max == 0) {
         System.out.println(anagrams.toString());
      } else {
         for (String chosenWord : possibleWords) { 
            if (letters.contains(chosenWord)) {
               anagrams.add(chosenWord);
               letters.subtract(chosenWord);
               print(max, anagrams, possibleWords, letters);
               letters.add(chosenWord);
               anagrams.remove(anagrams.size() - 1);
            }
         }
      }
   }
}