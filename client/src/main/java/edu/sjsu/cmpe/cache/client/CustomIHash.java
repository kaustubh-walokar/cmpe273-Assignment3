package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

public class CustomIHash implements IHashFunction {


    @Override
    public int hash(String s) {


        System.out.println(s.toString() + "   hash = " + Hashing.md5().hashString(s).asInt());

        return Hashing.md5().hashString(s).asInt();
    }
}
