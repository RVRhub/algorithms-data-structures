package rvr.data_structures;


// https://www.javaspecialists.eu/archive/Issue235.html
public class Hashing {


    private Hashing() {
        throw new Error("No instances");
    }


    // hash for key from java 8 binary tree
    // Thanks to Olaf Fricke for pointing this out
    //
    public static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    // jdk 7 something better then jdk 1.4
    // attacks going around where you would send lots of String objects with same hashCode
    public static final int hashOld(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    // jdk 1.4, there were quite a few collisions in the table buckets,
    // can be O(n)
    public static final int hashTooOld(Object key) {
        int h = key == null ? 0 : key.hashCode();
        h += ~(h << 9);
        h ^= (h >>> 14);
        h += (h << 4);
        h ^= (h >>> 10);
        return h;
    }


}
