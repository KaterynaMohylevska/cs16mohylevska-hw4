package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.List;

public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int count = 0;
        for (String s : strings) {
            String[] str = s.split(" ");
            for (String word : str) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word, word.length()));
                    count++;
                }
            }
        }
        return count;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() >= 2) {
            List<String> lst = new ArrayList<>();
            Iterable<String> wordsWithPref = wordsWithPrefix(pref);
            for (String s : wordsWithPref) {
                if (s.length() < pref.length() + k) {
                    lst.add(s);
                }
            }
            return lst;
        }
        return null;
    }

    public int size() {
        return trie.size();
    }
}
