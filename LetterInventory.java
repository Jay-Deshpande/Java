// This program keeps track of an inventory of letters of the alphabet for a
// given input. It enables addition or subtraction with another LetterInventory.

public class LetterInventory {
   public static final int TOTAL_CHARACTERS = 'z' - 'a' + 1;
   public static final int START_VAL = 'a';
   public static final int END_VAL = 'z';
   private int[] elementData;
   private int size;
   
   // post: constructs an inventory of the input string with counts of each letter
   public LetterInventory(String data) {
      data = data.toLowerCase();
      elementData = new int[TOTAL_CHARACTERS];
      for (int i = 0; i < data.length(); i++) {
         if (data.charAt(i) >= START_VAL && data.charAt(i) <= END_VAL){
            elementData[data.charAt(i) - 'a']++;
            size++;
         }
      }
   }
   
   // post: returns the current total count in the inventory
   public int size() {
      return size;
   }
   
   // post: returns whether or not the inventory is empty
   public boolean isEmpty() {
      return (size == 0);
   }
   
   // pre: letter must be an alphabetic character (throws
   //      IllegalArgumentException if not)
   // post: returns count of how many of the letter are in the ineventory
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      if (letter < START_VAL || letter > END_VAL) {
         throw new IllegalArgumentException("character: " + letter);
      }
      return elementData[letter - 'a'];
   }
   
   // post: creates an alphabetized, bracketed version of the inventory
   public String toString() {
      String result = "[";
      for (int i = 0; i < TOTAL_CHARACTERS; i++) {
         for (int j = 0; j < elementData[i]; j++) {
            result = result + ((char) ('a' + i));
         }
      }
      result = result + "]";
      return result;
   }
   
   // pre: letter must be an alphabetic character and value must 
   //      be nonnegative (throws IllegalArgumentException if not)
   // post: sets the count for the given letter to the given value
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      if (letter < START_VAL || letter > END_VAL || value < 0) {
         throw new IllegalArgumentException("letter: " + letter + ", value: " + value);
      }
      size += (value - elementData[letter - 'a']);
      elementData[letter - 'a'] = value;
   }
    
   // post: returns new LetterInventroy object that is the sum of this and other 
   public LetterInventory add(LetterInventory other) {
      LetterInventory sum = new LetterInventory("");
      for (int i = 0; i < TOTAL_CHARACTERS; i++) {
         char letter = (char) ('a' + i);
         sum.set(letter, (this.get(letter) + other.get(letter)));
      }
      return sum;
   }
   
   // pre: the count of this at index [i] must be greater than or equal to 
   //      the count of other at index [i] (returns null if not)
   // post: returns new LetterInventory object that is the difference between
   //       this and other
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory difference = new LetterInventory("");
      for (int i = 0; i < TOTAL_CHARACTERS; i++) {
         if (this.elementData[i] < other.elementData[i]) {
            return null;
         }
         char letter = (char) ('a' + i);
         difference.set(letter, (this.get(letter) - other.get(letter)));
      }
      return difference;
   }
}