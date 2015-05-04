package edu.sjsu.cmpe.cache.client;

/**
 * Cache Service Interface
 * 
 */
public interface CacheServiceInterface {
    String get(long key);

    String getCacheServerUrl();

    void put(long key, String value);
}
