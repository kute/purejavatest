package com.kute.arithmetic;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by kute on 2017/5/28.
 */
public class TrieTest {

    public static void main(String[] args) {

        List<String > wordList = Lists.newArrayList("abcdef", "rasdf", "rasuh", "abrd", "abr");

        TrieNode root = new TrieNode(false);
        addToTrieTree(wordList, root);

        System.out.println(search("abr", root));

    }

    public static void addToTrieTree(List<String> wordList, TrieNode root) {

        for(String word : wordList) {
            char[] ca = word.toCharArray();
            TrieNode currentNode = root;
            for(char c : ca) {
                Character cha = new Character(c);
                if(!currentNode.getNextNodeMap().containsKey(cha)) {
                    TrieNode temp = new TrieNode(false);
                    currentNode.getNextNodeMap().put(cha, temp);
                    currentNode = temp;
                }
                currentNode.setEnd(true);
            }
        }
    }

    public static boolean search(String str, TrieNode root) {
        boolean found = true;
        if(!Strings.isNullOrEmpty(str)) {
            char[] ca = str.toCharArray();
            for(char c : ca) {
                Character cha = new Character(c);
                if(!root.getNextNodeMap().containsKey(cha)) {
                    return false;
                }
                root = root.getNextNodeMap().get(cha);
            }
        }
        return found && root.isEnd();
    }

    public static void println(char c) {
        System.out.println(c);
    }

    public static void println(char[] ca) {
        for (char c : ca) {
            System.out.println(c);
        }
    }

    public static class TrieNode {

        private boolean end;
        private Map<Character, TrieNode> nextNodeMap;

        public TrieNode() {
        }

        public TrieNode(boolean end) {
            this.end = end;
            this.nextNodeMap = Maps.newHashMap();
        }

        public boolean isEnd() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }

        public Map<Character, TrieNode> getNextNodeMap() {
            return nextNodeMap;
        }

        public void setNextNodeMap(Map<Character, TrieNode> nextNodeMap) {
            this.nextNodeMap = nextNodeMap;
        }
    }
}
