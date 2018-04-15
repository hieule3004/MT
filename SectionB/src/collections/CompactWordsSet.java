package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    // TO BE IMPLEMENTED
    if (word == null) {
      throw new InvalidWordException("Null word");
    }
    if (word.isEmpty()) {
      throw new InvalidWordException("Empty word");
    }
    for (int i = 0; i < word.length(); i++) {
       if (word.charAt(i) < 'a' || word.charAt(i) > 'z') {
         throw new InvalidWordException("Non-alphabetic char at " + i);
       }
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
