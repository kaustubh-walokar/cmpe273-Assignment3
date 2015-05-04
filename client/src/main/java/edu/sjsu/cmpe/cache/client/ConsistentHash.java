package edu.sjsu.cmpe.cache.client;

/**
 * Created by kaustubh on 03/05/15.
 */

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

    private final IHashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle =
            new TreeMap<Integer, T>();

    public ConsistentHash(IHashFunction hashFunction,
                          int numberOfReplicas, Collection<T> nodes) {

        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }

        System.out.println("New Circle : " + circle);
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            System.out.println("hash when adding : " + hashFunction.hash(node.toString() + i));
            circle.put(hashFunction.hash(node.toString() + i),
                    node);

        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T get(Object key) {
//        System.out.println("Circle : " + circle);

        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap =
                    circle.tailMap(hash);
            hash = tailMap.isEmpty() ?
                    circle.firstKey() : tailMap.firstKey();
        }
        System.out.println("\nLocation of " + key + " found at " + hash);
        return circle.get(hash);
    }
}