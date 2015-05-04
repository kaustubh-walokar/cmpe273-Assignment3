package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService("http://localhost:3002");

        IHashFunction hashFunction = new CustomIHash();
        ArrayList collection = new ArrayList();
        collection.add(cache1);
        collection.add(cache2);
        collection.add(cache3);

        ConsistentHash consistentHash = new ConsistentHash(hashFunction, 1, collection);

//        String toAddValue= "foo";
//        int toAddKey = 1;
//        addToCache(toAddKey,toAddValue,consistentHash);
//
//        toAddValue= "bar";
//        toAddKey = 2;
//        addToCache(toAddKey,toAddValue,consistentHash);
        //int value = 'a';
        for (int i = 1; i <= 10; i++) {

            addToCache(i, String.valueOf((char) (i + 96)), consistentHash);
        }
        System.out.println("---------------------------");
        for (int i = 1; i <= 10; i++) {
            String value = (String) getFromCache(i, consistentHash);
            //System.out.println("got (" + i + ") => " + value + " from " + cache.toString());
        }


        //   String toAddValue= "foo";
        //  int toAddKey = 1;
        // addToCache(toAddKey,toAddValue,consistentHash);

        //ArrayList values = new ArrayList();


//        cache.put(toAddKey, toAddValue);
//        System.out.println("put("+toAddKey+" => "+toAddValue+")");
//        String value = cache.get(toAddKey);
//        System.out.println("get("+toAddKey+") => " + value);


        System.out.println("Exiting Cache Client...");
    }

    public static void addToCache(int toAddKey, String toAddValue, ConsistentHash consistentHash) {

        DistributedCacheService cache = (DistributedCacheService) consistentHash.get(toAddValue);

        cache.put(toAddKey, toAddValue);
        System.out.println("added (" + toAddKey + " => " + toAddValue + ")" + " On " + cache.toString());
        //  String value = cache.get(toAddKey);
        // System.out.println("get(" + toAddKey + ") => " + value);

    }

    public static Object getFromCache(int key, ConsistentHash consistentHash) {
        DistributedCacheService cache = (DistributedCacheService) consistentHash.get(key);
        System.out.println(cache);
        System.out.println("got (" + key + ") => " + cache.get(key) + " from " + cache.toString());
        return cache.get(key);
    }
}
