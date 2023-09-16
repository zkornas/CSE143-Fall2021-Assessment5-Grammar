// Zacharia Kornas
// TA: Kent Zeng
// GrammarSolver keeps track of pre-determined grammar rules provided in BNF format
// and can generate phrases based off of these rules with randomly selected terminals
// based on the non-terminals.

import java.util.*;

public class GrammarSolver {
   private SortedMap<String, List<String>> grammarRules;

   // Constructs a new GrammarSolver
   // Throws:
   //    IllegalArgumentException - if the list contains duplicate grammar rules
   //                             - if the list contains no grammar rules
   // Parameters:
   //    rules - the list of grammar rules in BNF format
   public GrammarSolver(List<String> rules){
      if(rules.size() == 0){
         throw new IllegalArgumentException();
      }
      grammarRules = new TreeMap<>();
      for(int i = 0; i < rules.size(); i++){
         String[] pieces = (rules.get(i)).split("::=");
         if(grammarContains(pieces[0])){
            throw new IllegalArgumentException();
         }
         String[] allTerminals = pieces[1].split("\\|");
         List<String> terminals = new ArrayList();
         for(int j = 0; j < allTerminals.length; j++){
            if(!grammarRules.containsKey(pieces[0])){
               grammarRules.put(pieces[0], terminals);
            }
            (grammarRules.get(pieces[0])).add(allTerminals[j].trim());
         }
      }
      
   }

   // Returns true if the indicated non-terminal is known by GrammarSolver,
   // otherwise retuns false
   // Parameters:
   //    symbol - the non-terminal that the user wishes to check GrammarSolver for
   public boolean grammarContains(String symbol){
      return grammarRules.containsKey(symbol);
   }

   // Returns the list of non-terminals known by GrammarSolver
   public String getSymbols(){
      return (grammarRules.keySet()).toString();
   }
   
   // Generates times number of phrases using the terminals of the non-terminal indicated
   // by the user. The terminals of the non-terminal will be randomly selected.
   // Parameters:
   //    symbol - the non-terminal that the user wants to generate a phrase of
   //    times - the number of phrases that the user wants to generate
   // Throws:
   //    IllegalArgumentException - if the non-terminal passed in is not a non-terminal
   //                               known by GrammarSolver
   //                             - if times is negative
   // Returns:
   //    Times number of phrases using the random terminals of
   //    the indicated non-terminal 
   public String[] generate(String symbol, int times){
      if(times < 0 || !grammarRules.containsKey(symbol)){
         throw new IllegalArgumentException();
      }
      String[] sentences = new String[times];
      for(int i = 0; i < times; i++){
         sentences[i] = generateSentence(symbol).trim();
      }
      return sentences;
   }
   
   // Helper method for the public generate method
   // Generates a single phrase following the indicated grammar rule
   // Parameters:
   //    symbol - the grammar rule that the user wants to generate a phrase of
   // Returns:
   //    a single phrase following symbol grammar rule made of randomly generated terminals
   private String generateSentence(String symbol){
      String sentence = "";
      Random r = new Random();
      int random = r.nextInt((grammarRules.get(symbol)).size());
      String[] pieces = ((grammarRules.get(symbol)).get(random)).split("\\s+");
      for(int i = 0; i < pieces.length; i++){
         if(!grammarRules.containsKey(pieces[i])){
            sentence += pieces[i] + " ";
         } else {
            sentence += generateSentence(pieces[i]);
         }
      }
      return sentence;
   }
   
}
