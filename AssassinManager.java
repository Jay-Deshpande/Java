// This program manages a game of Assassin. Players that are 
// killed are moved to the graveyard. The game continues until 
// there is only one person left in the kill ring.

import java.util.*;

public class AssassinManager {
   private AssassinNode killRingFront; 
   private AssassinNode graveyardFront;
   
   // names: List of names for assassin kill ring
   // pre: names must exist and contain at least one name
   //      (throw IllegalArgument Exception if not)
   // post: Initializes new kill ring with names passed in
   public AssassinManager(ArrayList<String> names) {
      if (names == null || names.size() < 1) {
         throw new IllegalArgumentException("There are no names in the list");
      }
      graveyardFront = null;
      killRingFront = new AssassinNode(names.get(0));
      AssassinNode current = killRingFront;
      for (int i = 1; i < names.size(); i++) {
         current.next = new AssassinNode(names.get(i));
         current = current.next;
      }
   }
   
   // post: Prints the formatted names of who is stalking who in the kill ring
   public void printKillRing() {
      AssassinNode current = killRingFront;
      while (current.next != null) {
         System.out.println("  " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("  " + current.name + " is stalking " + killRingFront.name);
   }
   
   // post: Prints the formatted names of people in the graveyard and their killers
   public void printGraveyard() {
      AssassinNode current = graveyardFront;
      while (current != null) {
         System.out.println("  " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   // name: Name to check if in kill ring
   // post: Returns true if given name is in the kill ring
   //       Otherwise returns false
   public boolean killRingContains(String name) {
      return groupContains(name, killRingFront);
   }
      
   // name: Name to check if in graveyard
   // post: Returns true if given name is in the graveyard
   //       Otherwise returns false
   public boolean graveyardContains(String name) {
      return groupContains(name, graveyardFront);
   }
   
   // name: Name to check if in either graveyard or kill ring
   // chosenGroup: Reference to either graveyardFront or killRingFront
   // post: Returns true if given name is in the specified group
   //       Otherwise returns false
   private boolean groupContains(String name, AssassinNode chosenGroup) {
      AssassinNode current = chosenGroup;
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // post: Returns true if game is over (only one person left in kill ring)
   //       Otherwise returns false
   public boolean isGameOver() {
      return (killRingFront.next == null);
   }
   
   // post: Returns name of winner if game is over
   //       Otherwise returns null
   public String winner() {
      if (isGameOver()) {
         return killRingFront.name;
      } else {
         return null;
      }
   }
   
   // name: name of person to be assassinated
   // pre: name must be part of the kill ring
   //      (throws IllegalArgumentException if not).
   //      The game must not be over
   //      (throws IllegalStateException if game is over).
   // post: Moves the killed player from the kill ring to the 
   //       graveyard and records the killers' name
   public void kill(String name) {
      if (isGameOver()) {
         throw new IllegalStateException("The game is over");
      } else if (!killRingContains(name)) {
         throw new IllegalArgumentException(name + " is not part of the kill ring");
      }
      AssassinNode current = killRingFront;
      while (current.next != null && !current.next.name.equalsIgnoreCase(name)) {
         current = current.next;
      }
      AssassinNode temp = graveyardFront;
      if (killRingFront.name.equalsIgnoreCase(name)) {
         graveyardFront = killRingFront;
         killRingFront = killRingFront.next;
      } else {
         graveyardFront = current.next;
         current.next = current.next.next;
      }
      graveyardFront.next = temp;
      graveyardFront.killer = current.name;
   }
}