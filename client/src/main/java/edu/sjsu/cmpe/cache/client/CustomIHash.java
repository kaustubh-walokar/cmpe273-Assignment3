package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.Objects;

public class CustomIHash implements IHashFunction {


    @Override
    public int hash(Object s) {
        HashFunction hf = Hashing.md5();

        System.out.println(s.toString() + "   hash = " + ((Objects.hashCode(s)) % 100));
        return ((Objects.hashCode(s)) % 100);
        //return s.hashCode();
    }
}
