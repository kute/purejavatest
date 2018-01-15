package com.kute.test;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by kute on 2017/5/28.
 */
public class TrieNode {

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
