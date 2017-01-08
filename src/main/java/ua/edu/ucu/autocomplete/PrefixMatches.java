package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

/**
 *
 * @author andrii
 */
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
                    trie.add(new Tuple(word,word.length()));
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        throw new UnsupportedOperationException("Not supported yet.");        
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        throw new UnsupportedOperationException("Not supported yet.");        
    }

    public int size() {
        return trie.size();
    }
}
