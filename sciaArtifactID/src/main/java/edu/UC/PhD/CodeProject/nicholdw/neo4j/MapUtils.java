package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.HashMap;
import java.util.Map;

// Program to clone a Map in Java
class MapUtils
{
    public static<K,V> Map<K,V> clone(Map<K,V> original) {
        Map<K,V> copy = new HashMap<>();

        for (Map.Entry<K,V> entry: original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }

        return copy;
    }

    public static void main(String[] args) {
        Map<String, Integer> hashMap = new HashMap();
        hashMap.put("A", 1);
        hashMap.put("B", 2);
        hashMap.put("C", 3);

        Map<String, Integer> copy = clone(hashMap);
        System.out.println(copy);
    }
}
