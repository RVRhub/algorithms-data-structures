package rvr.tck_test;

/**
 * @test
 * @bug 7173432
 * @summary If the key to be inserted into a HashMap is null and the table
 * needs to be resized as part of the insertion then addEntry will try to
 * recalculate the hash of a null key. This will fail with an NPE.
 */

/*
How to run:
/Users/romanrybak/Downloads/jtreg/bin/jtreg \
  -verbose:summary \
  /Users/romanrybak/repo/java_workspace/algorithms-data-structures/src/test/java/rvr/data_strctures/NullKeyAtResize.java
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullKeyAtResize {


    public static void main(String[] args) throws Exception {
        List<Object> old_order = new ArrayList<>();
        Map<Object,Object> m = new HashMap<>(16);
        int number = 0;
        while(number < 100000) {
            m.put(null,null); // try to put in null. This may cause resize.
            m.remove(null); // remove it.
            Integer adding = (number += 100);
            m.put(adding, null); // try to put in a number. This wont cause resize.
            List<Object> new_order = new ArrayList<>();
            new_order.addAll(m.keySet());
            new_order.remove(adding);
            if(!old_order.equals(new_order)) {
                // we resized and didn't crash.
                System.out.println("Encountered resize after " + (number / 100) + " iterations");
                break;
            }
            // remember this order for the next time around.
            old_order.clear();
            old_order.addAll(m.keySet());
        }
        if(number == 100000) {
            throw new Error("Resize never occurred");
        }
    }
}