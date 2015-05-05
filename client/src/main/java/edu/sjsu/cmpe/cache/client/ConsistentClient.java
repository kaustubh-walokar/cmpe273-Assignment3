package edu.sjsu.cmpe.cache.client;

import edu.sjsu.cmpe.cache.client.consistent.ConsistentHashSimpler;

import java.util.ArrayList;

public class ConsistentClient {

    public static void main(String[] args) throws Exception {
        System.out.println("---------------------------------------------------------------");
        System.out.println("-------------Starting Consistent Cache Client------------------");


        String cache1 = "http://localhost:3000";
        String cache2 = "http://localhost:3001";
        String cache3 = "http://localhost:3002";


        ArrayList collection = new ArrayList();
        collection.add(cache1);
        collection.add(cache2);
        collection.add(cache3);


        System.out.println("---------------------------------------------------------------");
        System.out.println("------------------Add to the distributed caches----------------");
        System.out.println("---------------------------------------------------------------");


        ConsistentHashSimpler consistentHash = new ConsistentHashSimpler(collection);

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

    public static void addToCache(int toAddKey, String toAddValue, ConsistentHashSimpler consistentHash) {

        String cacheUrl = (String) consistentHash.getCache(toAddKey);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        cache.put(toAddKey, toAddValue);
        System.out.println("put(" + toAddKey + " => " + toAddValue + ")");
        // System.out.println("added (" + toAddKey + " => " + toAddValue + ")" + " On " + cacheUrl);

    }

    public static Object getFromCache(int key, ConsistentHashSimpler consistentHash) {
        String cacheUrl = (String) consistentHash.getCache(key);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        String value = cache.get(key);
        System.out.println("get(" + key + ") => " + value);
        //System.out.println("got (" + key + ") => " + cache.get(key) + " from " + cacheUrl);
        return value;
    }

}
