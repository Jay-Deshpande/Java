// This program reads an input file containing a grammar in Backus-Naur Form.
// It allows the user to randomly generate elements of the grammar.

import java.util.*;

public class GrammarSolver {
   private Map<String, String[]> grammarRules;
   
   // rules: Given collection of grammar rules in BNF format
   // pre: rules must exist and contain at least one value
   //      (throws IllegalArgumentException if not)
   //      Each non-terminal value can only occur once in the grammar rules
   //      (throws IllegalArgumentException if not)
   // post: A new grammar solver is created with all grammar rules corresponding
   //       to their given non-terminal
   public GrammarSolver(List<String> rules) {
      if (rules == null || rules.size() < 1) {
         throw new IllegalArgumentException("rules must contain at least one value");
      }
      grammarRules = new TreeMap<String, String[]>();
      for (String s : rules) {
         String[] parts = s.split("::=");
         String nonTerminal = parts[0];
         if (grammarRules.containsKey(nonTerminal)) {
            throw new IllegalArgumentException("no duplicate non-terminals allowed");
         }
         String[] parts2 = parts[1].split("[|]");
         grammarRules.put(nonTerminal, parts2);
      }
   }
   
   // symbol: Given grammar symbol
   // pre: symbol must exist and have length greater than zero
   //      (throws IllegalArgumentException if not)
   // post: Returns true if symbol is a non-terminal of the grammar 
   //       Otherwise returns false
   public boolean contains(String symbol) {
      if (symbol == null || symbol.length() < 1) {
         throw new IllegalArgumentException("symbol must have a length greater than zero");
      }
      if (grammarRules.containsKey(symbol)) {
         return true;
      }
      return false;  
   }
   
   // post: Returns the sorted non-terminals of the grammar
   public Set<String> getSymbols() {
      return grammarRules.keySet();
   }
   
   // symbol: Given grammar symbol
   // pre: symbol must exist and have a length greater than zero
   //      (throws IllegalArgumentException if not)
   // post: Generates a random occurence of the given symbol according to the
   //       grammar rules
   public String generate(String symbol) {
      if (symbol == null || symbol.length() < 1) {
         throw new IllegalArgumentException("symbol must have a length greater than zero");
      }
      if (!grammarRules.containsKey(symbol)) {
         return symbol;
      }
      String result = "";
      String[] rules = grammarRules.get(symbol);
      Random r = new Random();
      int randomNumber = r.nextInt(rules.length);
      String randomRule = rules[randomNumber];
      randomRule = randomRule.trim();
      String[] chosenRule = randomRule.split("[ \t]+");
      for (String rule : chosenRule) {
         result += generate(rule) + " ";
      }      
      return result.trim();
   }
}