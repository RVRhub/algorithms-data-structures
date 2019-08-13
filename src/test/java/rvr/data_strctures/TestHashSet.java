package rvr.data_strctures;

import org.openjdk.jmh.annotations.Benchmark;
import rvr.data_structures.HashSetImpl;

import java.util.HashSet;

public class TestHashSet {


    @Benchmark
    public void printNewHashSet() {

        HashSetImpl hashSet = new HashSetImpl();
        for(int i =0; i < 100000; i++){
            hashSet.add(i);
        }
    }

    @Benchmark
    public void printNewHashSetConstCapacity() {

        HashSetImpl hashSet = new HashSetImpl(133334);

        for(int i =0; i < 100000; i++){
            hashSet.add(i);
        }
    }

    @Benchmark
    public void printNewHashSetJava() {

        HashSet hashSet = new HashSet();

        for(int i =0; i < 100000; i++){
            hashSet.add(i );
        }

    }

    @Benchmark
    public void printNewHashSetJavaConstCapacity() {

        HashSet hashSet = new HashSet(133334);

        for(int i =0; i < 100000; i++){
            hashSet.add(i);
        }

    }
}
