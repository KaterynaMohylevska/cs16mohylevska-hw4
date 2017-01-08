package ua.edu.ucu.tries;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RWayTrie implements Trie {
    public static final int R = 256;
    private Node root = new Node();
    private int countWords = 0;

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
        private int counter = 0;

        public Node(){
        }

        public Node(Object value){
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

        public void addNext(Node letter){
            this.next[this.counter] = letter;
            counter++;
        }

    }

    @Override
    public void add(Tuple t) {
        Node letter = this.root;
        int len = t.weight;
        char[] word = t.term.toCharArray();
        int start = 0;
        for (int i = 0; i < len; i++){
            if (!letter.containsInNext(word[i])){
                start = i;
                break;
            }
            letter = letter.findInNext(word[i]);
        }

        for (int i = start; i < len; i++){
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
        for (int i = 0; i < wordArray.length; i++){
            if (!letter.containsInNext(wordArray[i])){
                return false;
            }
            letter = letter.findInNext(wordArray[i]);
        }
        return true;
    }

    @Override
    public boolean delete(String word) {
        if (this.contains(word)){
            return true;
        }
        return false;
    }

    public void iteration(Node[] next){
        for (int i = 0; i < next.length; i++){
            if (next[i].counter != 0){
                if (next[i].value == "#"){

                }
            }
        }
    }

    @Override
    public Iterable<String> words() {
        List<String> dict = new ArrayList<>();
        Node letter = this.root;

        return dict;
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        return countWords;
    }

}
