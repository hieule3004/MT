package collections;

import collections.exceptions.InvalidWordException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCompactWordTree implements CompactWordsSet {

  private final Node root = new Node(Character.MIN_VALUE);
  private AtomicInteger size = new AtomicInteger(0);

  private Pair<Node, Integer> find(String word) {
    Node next, curr = root;
    curr.lock();
    int i = 0;
    char c;
    do {
      c = word.charAt(i);
      next = curr.getChildAt(c - 'a');
      if (next == null || next.getItem() != c) {
        break;
      }
      next.lock();
      curr.unlock();
      curr = next;
      i++;
    } while (i < word.length());
    return new Pair<>(curr, i);
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    // TO BE IMPLEMENTED
    Pair<Node, Integer> pair = find(word);
    Node node = pair.getX();
    int start = pair.getY();
    try {
      if (wordCheck(pair, word)) {
        return false;
      }
      Node next;
      for (int i = start; i < word.length(); i++) {
        next = node.setChild(word.charAt(i));
        next.lock();
        node.unlock();
        node = next;
      }
      node.setWord(true);
      size.incrementAndGet();
      return true;
    } finally {
      node.unlock();
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    // TO BE IMPLEMENTED
    Pair<Node, Integer> pair = find(word);
    Node node = pair.getX();
    try {
      if (!wordCheck(pair, word)) {
        return false;
      }
      node.setWord(false);
      size.decrementAndGet();
      return true;
    } finally {
      node.unlock();
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    // TO BE IMPLEMENTED
    Pair<Node, Integer> pair = find(word);
    Node node = pair.getX();
    try {
      return wordCheck(pair, word);
    } finally {
      node.unlock();
    }
  }

  private static boolean wordCheck(Pair<Node, Integer> pair, String word) {
    return pair.getX().isWord() && pair.getY() == word.length();
  }

  @Override
  public int size() {
    // TO BE IMPLEMENTED
    return size.get();
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    // TO BE IMPLEMENTED
    List<String> list = new ArrayList<>();
    traversal(root, "", list);
    return list;
  }

  private void traversal(Node node, String prefix, List<String> list) {
    for (Node child : node.getChildren()) {
      if (child != null) {
        String s = prefix + child.getItem();
        if (child.isWord()) {
          list.add(s);
        }
        traversal(child, s, list);
      }
    }
  }
}

class Node {

  public static final int AB = 26;

  private char item;
  private Node[] children = new Node[AB];
  private boolean isWord = false;
  private Lock lock = new ReentrantLock();

  Node(char item) {
    this.item = item;
  }

  char getItem() {
    return item;
  }

  Node[] getChildren() {
    return children;
  }

  Node getChildAt(int pos) {
    return children[pos];
  }

  Node setChild(char c) {
    Node child = new Node(c);
    this.children[c - 'a'] = child;
    return child;
  }

  boolean isWord() {
    return isWord;
  }

  void setWord(boolean word) {
    isWord = word;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }
}

class Pair<X, Y> {

  private final X x;
  private final Y y;

  Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }

  X getX() {
    return x;
  }

  Y getY() {
    return y;
  }
}


