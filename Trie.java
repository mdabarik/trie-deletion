// Java implementation of delete
// operations on Trie

import java.util.*;

public class Trie {
    static int ALPHABET_SIZE = 26;
    
    // Trie node
    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE]; 
        boolean isEndOfWord;
        
        // Returns new trie node (initialized to NULLs)
        public TrieNode() {
            isEndOfWord = false;
            Arrays.fill(children, null);
        }
    }
    
    static TrieNode root;
    
    // If not present, inserts key into trie
    // If the key is prefix of trie node, just
    // marks leaf node
    static void insert(String key) {
        TrieNode pCrawl = root;
        for (int level = 0; level < key.length(); level++) {
            int index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = new TrieNode();
            }
            pCrawl = pCrawl.children[index];
        }
        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }
    
    // Returns true if key is present in trie, else false
    static boolean search(String key) {
        TrieNode pCrawl = root;
        for (int level = 0; level < key.length(); level++) {
            int index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null) {
                return false;
            }
            pCrawl = pCrawl.children[index];
        }
        return pCrawl != null && pCrawl.isEndOfWord;
    }
  
    static boolean prefixSearch(String key) {
        TrieNode pCrawl = root;
        for (int level = 0; level < key.length(); level++) {
            int index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null) {
                return false;
            }
            pCrawl = pCrawl.children[index];
        }
        return true;
    }
    
    // Returns true if currentNode has no children
    static boolean hasNoChild(TrieNode currentNode) {
        for (TrieNode child : currentNode.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
    
    // Recursive function to delete a key from the given Trie
    static boolean removeUtil(TrieNode currentNode, String key, int level, int length) {
        // If tree is empty
        if (currentNode == null) {
            return false;
        }
        
        // If last character of key is being processed
        if (level == length) {
            // This node is no more end of word after removal of given key
            if (currentNode.isEndOfWord) {
                currentNode.isEndOfWord = false;
                
                // If currentNode has no children, it can be deleted
                return hasNoChild(currentNode);
            }
            return false;
        }
        
        // If not last character, recur for the child
        int index = key.charAt(level) - 'a';
        TrieNode childNode = currentNode.children[index];
        boolean childDeleted = removeUtil(childNode, key, level + 1, length);
        
        // If child node is deleted, remove the link from the current node
        if (childDeleted) {
            currentNode.children[index] = null;
            
            // If currentNode is not an end of another word and has no other children
            return !currentNode.isEndOfWord && hasNoChild(currentNode);
        }
        
        return false;
    }
    
    // Function to delete a key from the given Trie
    static void remove(String key) {
        if (key != null && !key.isEmpty()) {
            removeUtil(root, key, 0, key.length());
        }
    }

    // Driver Code
    public static void main(String[] args) {
        root = new TrieNode();
    
        String[] keys = {"bad", "bat", "cat", "cut", "geek", "geeks", "zoo"};
                        
        // Construct trie
        for (String key : keys) {
            insert(key);
        }
        
        remove("bad");
        System.out.println("search:bad->" + search("bad"));
        System.out.println("prefixSearch:bad->" + prefixSearch("bad"));
          
        // Other test cases
        System.out.println("search:bat->" + search("bat"));
        System.out.println("search:cat->" + search("cat"));
        System.out.println("search:geek->" + search("geek"));
        System.out.println("search:zoo->" + search("zoo"));
        
        remove("bat");
        System.out.println("search:bat->" + search("bat"));
        System.out.println("prefixSearch:bat->" + prefixSearch("bat"));
    }
}
