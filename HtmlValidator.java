// This program manages a collection of HTML tags and ensures
// that they are in a valid sequence.

import java.util.*;

public class HtmlValidator {
   private Queue<HtmlTag> allTags;
   
   // post: Initializes a new list
   public HtmlValidator() {
      allTags = new LinkedList<HtmlTag>();
   }
   
   // tags: List of HTML tags
   // pre: The list of tags cannot be null (throws IllegalArgumentException if null)
   // post: Creates a copy of the tag list
   public HtmlValidator(Queue<HtmlTag> tags) {
      nullException(tags, "Queue");
      allTags = new LinkedList<HtmlTag>();
      for (int i = 0; i < tags.size(); i++) {
         HtmlTag currentTag = tags.remove();
         allTags.add(currentTag);
         tags.add(currentTag);
      }
   }
   
   // tag: Individual HTML tag
   // pre: tag cannot be null (throws IllegalArgumentException if null)
   // post: Adds tag to the end of list of html tags
   public void addTag(HtmlTag tag) {
      nullException(tag, "Tag");
      allTags.add(tag);
   }
   
   // post: returns list of html tags
   public Queue<HtmlTag> getTags() {
      return allTags;
   }
   
   // element: HTML element to be removed
   // pre: element cannot be null (throws IllegalArgumentException if null)
   // post: Every tag matching element is removed from the list
   public void removeAll(String element) {
      nullException(element, "Element");
      int size = allTags.size();
      for (int i = 0; i < size; i++) {
         HtmlTag currentTag = allTags.remove();
         if (!currentTag.getElement().equals(element)) {
            allTags.add(currentTag);
         }
      }
   }
   
   // post: Prints a representation of the list's HTML tags
   // Error is printed if a closing tag does not match the most recently opened tag.
   // Error is printed if there are unclosed tags at the end of the HTML input.
   public void validate() {
      Stack<HtmlTag> notClosed = new Stack<HtmlTag>();
      int size = allTags.size();
      int indentCount = 0;
      for (int i = 0; i < size; i++) {
         HtmlTag currentTag = allTags.remove();
         if (currentTag.isOpenTag()) {
            if (!currentTag.isSelfClosing()) {
               notClosed.push(currentTag);
               indentCount ++;
            }
            indent(indentCount, notClosed.size(), currentTag);
         } else if (notClosed.size() > 0 && currentTag.matches(notClosed.peek())) {
            notClosed.pop();
            indent(indentCount, notClosed.size(), currentTag);
         } else {
            System.out.println("ERROR unexpected tag: " + currentTag.toString()); 
         }
         indentCount = 0;
         allTags.add(currentTag);
      }
      while (!notClosed.isEmpty()) {
         System.out.println("ERROR unclosed tag: " + notClosed.pop().toString());
      }
   }

      
   // count: The current number of indentations 
   // size: The number of unclosed tags
   // tag: The HTML tag to be printed
   // post: Prints the correct number of spaces based on the indentation count
   //       and the HTML tag on the same line
   private void indent(int count, int size, HtmlTag tag) {
      String indentation = "";
      for (int i = 0; i < (size - count); i++) {
         indentation += "    ";
      }
      System.out.println(indentation + tag.toString());
   }
   
   // check: Object to be checked
   // type: Object description for exception text
   // post: Throws IllegalArgumentException if check is null
   private void nullException(Object check, String type) {
      if (check == null) {
         throw new IllegalArgumentException(type + " is null");
      }
   }
}