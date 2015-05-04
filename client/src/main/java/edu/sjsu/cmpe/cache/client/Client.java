package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        //  CacheServiceInterface cache1 = new DistributedCacheService("http://localhost:3000");
        //  CacheServiceInterface cache2 = new DistributedCacheService("http://localhost:3001");
        //  CacheServiceInterface cache3 = new DistributedCacheService("http://localhost:3002");

        String cache1 = "http://localhost:3000";
        String cache2 = "http://localhost:3001";
        String cache3 = "http://localhost:3002";
        IHashFunction hashFunction = new CustomIHash();
        ArrayList collection = new ArrayList();
        collection.add(cache1);
        collection.add(cache2);
        collection.add(cache3);


        System.out.println("---------------------------------------------------------------");
        System.out.println("------------------Add to the distributed caches----------------");
        System.out.println("---------------------------------------------------------------");


        ConsistentHash consistentHash = new ConsistentHash(hashFunction, 1, collection);

        for (int i = 1; i <= 10; i++) {

            addToCache(i, String.valueOf((char) (i + 96)), consistentHash);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("-----------Retrieve from the distributed caches----------------");
        System.out.println("---------------------------------------------------------------");
        for (int i = 1; i <= 10; i++) {
            String value = (String) getFromCache(i, consistentHash);
            //System.out.println("got (" + i + ") => " + value + " from " + cache.toString());
        }




        System.out.println("Exiting Cache Client...");
    }

    public static void addToCache(int toAddKey, String toAddValue, ConsistentHash consistentHash) {

        String cacheUrl = (String) consistentHash.getCache(toAddKey);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        cache.put(toAddKey, toAddValue);
        System.out.println("added (" + toAddKey + " => " + toAddValue + ")" + " On " + cache.getCacheServerUrl());

    }

    public static Object getFromCache(int key, ConsistentHash consistentHash) {
        String cacheUrl = (String) consistentHash.getCache(key);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);

        System.out.println("got (" + key + ") => " + cache.get(key) + " from " + cache.getCacheServerUrl());
        return cache.get(key);
    }
}
