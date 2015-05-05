package edu.sjsu.cmpe.cache.client;

/**
 * Cache Service Interface
 * 
 */
public interface CacheServiceInterface {
    String get(long key);
    void put(long key, String value);
}
