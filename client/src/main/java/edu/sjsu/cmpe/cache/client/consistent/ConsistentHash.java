package edu.sjsu.cmpe.cache.client.consistent;

/**
 * Created by kaustubh on 03/05/15.
 */

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle =
            new TreeMap<Integer, T>();

    public ConsistentHash(//HashFunction hashFunction,
                          int numberOfReplicas, Collection<T> nodes) {

        this.hashFunction = Hashing.md5();
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }

        System.out.println("New Circle : " + circle);
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            //int hash=hashFunction.hash(node.toString() + i);
            int hash = hashFunction.newHasher().putString((node.toString() + i), Charset.defaultCharset()).hash().asInt();
            System.out.println("hash when adding : " + hash);
            circle.put(hash, node);

        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.newHasher().putString((node.toString() + i), Charset.defaultCharset()).hash().asInt());
        }
    }

    public T getCache(Object key) {
//        System.out.println("Circle : " + circle);

        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.newHasher().putString(key.toString(), Charset.defaultCharset()).hash().asInt();
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