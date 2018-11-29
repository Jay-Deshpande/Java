// This program models the physics of a guitar string using the
// Karplus-Strong Algorithm. 

import java.util.*;

public class GuitarString {
   public static final int SAMPLE_RATE = StdAudio.SAMPLE_RATE;
   public static final double DECAY_FACTOR = 0.996;
   private Queue<Double> buffer;
   private int capacity; 
   
   // pre: frequency must be greater than 0 
   //      (throws IllegalArgumentException if not)
   // post: constructs a GuitarString at rest -
   //       all queue values initialized to 0 
   public GuitarString(double frequency) {
      if (frequency <= 0) {
         throw new IllegalArgumentException("frequency: " + frequency); 
      }
      capacity = (int) Math.round(SAMPLE_RATE/frequency);
      if (capacity < 2) {
         throw new IllegalArgumentException("capacity: " + capacity); 
      }
      buffer = new LinkedList<Double>();
      for (int i = 0; i < capacity; i++) {
         buffer.add(0.0);
      }
   }
   
   // pre: array init must contain at least 2 values
   //      (throws IllegalArgumentException if not)
   // post: buffer is populated by the values from the array
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException("init.length: " + init.length);
      }
      buffer = new LinkedList<Double>();
      for (int i = 0; i < init.length; i++) {
         buffer.add(init[i]);
      }
      capacity = init.length;
   }
   
   // post: buffer is populated by random numbers between
   //       -0.5 inclusive and 0.5 exclusive
   public void pluck() {
      Random rand = new Random();
      while (buffer.size() > 0) {
         buffer.remove();
      }
      for (int i = 0; i < capacity; i++) {
         buffer.add(rand.nextDouble() - 0.5);
      }
   }
   
   // post: front value is removed and new decayed value is added
   //       to end of buffer
   public void tic() {
      double val1 = buffer.remove();
      double val2 = buffer.peek();
      buffer.add(((val1 + val2) / 2) * DECAY_FACTOR); 
   }
   
   // post: returns front value of buffer
   public double sample() {
      return buffer.peek();
   }
}