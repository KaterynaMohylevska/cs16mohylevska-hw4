package ua.edu.ucu.tries;

import java.util.ArrayList;
import java.util.List;

public class RWayTrie implements Trie {
    public static final int R = 256;
    private Node root = new Node(null);
    private int countWords = 0;

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
        private int counter = 0;
        private Node parent;

        public Node(Object value) {
            this.value = value;
        }

        public boolean containsInNext(Object letter) {
            for (int i = 0; i < this.counter; i++) {
                if (this.next[i].value == letter) {
                    return true;
                }
            }
            return false;
        }

        public Node findInNext(Object letter) {
            if (this.containsInNext(letter)) {
                for (int i = 0; i < this.counter; i++) {
                    if (this.next[i].value == letter) {
                        return this.next[i];
                    }
                }
            }
            throw new Error();
        }

        public void addNext(Node letter) {
            this.next[this.counter] = letter;
            this.next[this.counter].parent = this;
            counter++;

        }

    }

    @Override
    public void add(Tuple t) {
        Node letter = this.root;
        int len = t.weight;
        char[] word = t.term.toCharArray();
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (!letter.containsInNext(word[i])) {
                start = i;
                break;
            }
            letter = letter.findInNext(word[i]);
        }

        for (int i = start; i < len; i++) {
            Node l = new Node(word[i]);
            letter.addNext(l);
            letter = l;
        }
        letter.addNext(new Node("#"));
        this.countWords++;
    }

    @Override
    public boolean contains(String word) {
        char[] wordArray = word.toCharArray();
        Node letter = this.root;
        for (int i = 0; i < wordArray.length; i++) {
            if (!letter.containsInNext(wordArray[i])) {
                return false;
            }
            letter = letter.findInNext(wordArray[i]);
        }
        return true;
    }

    @Override
    public boolean delete(String word) {
        if (this.contains(word)) {
            return true;
        }
        return false;
    }

    public String iteration(Node[] next) {
        String allWords = "";
        for (int i = 0; i < next[0].parent.counter; i++) {
            if (next[i].value == "#") {
                String str = "";
                Node letter = next[i];
                while (letter.value != null) {
                    str += letter.value;
                    letter = letter.parent;
                }
                String word = new StringBuffer(str).reverse().toString();
                allWords += word;
            } else {
                allWords += iteration(next[i].next);
            }
        }
        return allWords;
    }

    @Override
    public Iterable<String> words() {
        List<String> dict = new ArrayList<>();
        String words = iteration(this.root.next);
        char[] wordArray = words.toCharArray();
        String word = "";
        for (int i = 0; i < wordArray.length; i++) {
            if (wordArray[i] == '#') {
                dict.add(word);
                word = "";
            } else {
                word += wordArray[i];
            }
        }
        return dict;
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        if (s.length() >= 2) {
            char[] sArray = s.toCharArray();
            Node letter = this.root;
            for (int i = 0; i < sArray.length; i++) {
                if (!letter.containsInNext(sArray[i])) {
                    return null;
                }
                letter = letter.findInNext(sArray[i]);
            }
            List<String> dict = new ArrayList<String>();
            String words = iteration(letter.next);
            char[] wordArray = words.toCharArray();
            String word = "";
            for (int i = 0; i < wordArray.length; i++) {
                if (wordArray[i] == '#') {
                    dict.add(word);
                    word = "";
                } else {
                    word += wordArray[i];
                }
            }

            return dict;
        }
        return null;
    }

    @Override
    public int size() {
        return countWords;
    }

}
