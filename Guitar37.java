// This program emulates a guitar with 37 strings using the GuitarString class.
// Users can press keyboard keys to play various strings. 

import java.util.*;

public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   private GuitarString[] strings = new GuitarString[37];
   private int time;
    
   // post: populates the strings array with GuitarString objects of specified frequency
   public Guitar37() {
      for (int i = 0; i < strings.length; i++) {
         strings[i] = new GuitarString((Math.pow(2.0, (i - 24) / 12.0) * 440));
      }
   }
    
   // pre: -24 >= pitch <= 12; if not, ignore note
   // post: plays string with specified pitch
   public void playNote(int pitch) {
      if (pitch >= -24 && pitch <= 12) {
         strings[pitch + 24].pluck();
      }  
   }
    
   // pre: key is pressed by the user
   // post: returns whether or not the pressed key is a string on the guitar
   public boolean hasString(char string) {
      return (KEYBOARD.indexOf(string) != -1);
   }
    
   // pre: key pressed by user must correspond to string on guitar
   //      (throws IllegalArgumentException if not)
   // post: plays specified guitar string
   public void pluck(char string) {
      if (!this.hasString(string)) {
         throw new IllegalArgumentException("key: " + string);
      }
      strings[KEYBOARD.indexOf(string)].pluck();
   }
     
   // post: returns sum of all sample from strings of guitar
   public double sample() {
      double sampleTotal = 0.0;
      for (int i = 0; i < strings.length; i++) {
         sampleTotal += strings[i].sample();
      }
      return sampleTotal;
   }
    
   // post: updates each string's buffer by one tic
   public void tic() {
      time++;
      for (int i = 0; i < strings.length; i++) {
         strings[i].tic();
      }
   }
    
   // post: returns the current time
   public int time() {
      return time;
   }
}