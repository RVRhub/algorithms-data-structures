package rvr.data_strctures;

import java.lang.reflect.*;
import java.util.*;

public class MapInspector {

    private interface MapProcessor {
        void beginBucket();

        void process(Map.Entry<?, ?> node);

        void endBucket(Map<Integer, Integer> count);
    }

    public static Map<Integer, Integer> getHashClashDistribution(
            Map<?, ?> map)
            throws NoSuchFieldException, IllegalAccessException {
        return getBucketDistribution(map, new MapProcessor() {
            private final Map<Integer, Integer> numberOfClashes =
                    new HashMap<Integer, Integer>();

            public void beginBucket() {
                numberOfClashes.clear();
            }

            public void process(Map.Entry<?, ?> node) {
                increment(numberOfClashes, node.getKey().hashCode());
            }

            public void endBucket(Map<Integer, Integer> count) {
                for (Integer val : numberOfClashes.values()) {
                    increment(count, val);
                }
            }
        });
    }

    public static Map<Integer, Integer> getBucketClashDistribution(
            Map<?, ?> map)
            throws NoSuchFieldException, IllegalAccessException {
        return getBucketDistribution(map, new MapProcessor() {
            private int size;

            public void beginBucket() {
                size = 0;
            }

            public void process(Map.Entry<?, ?> node) {
                size++;
            }

            public void endBucket(Map<Integer, Integer> count) {
                increment(count, size);
            }
        });
    }

    private static void increment(
            Map<Integer, Integer> map, int size) {
        Integer counter = map.get(size);
        if (counter == null) {
            map.put(size, 1);
        } else {
            map.put(size, counter + 1);
        }
    }

    private static Map<Integer, Integer> getBucketDistribution(
            Map<?, ?> map, MapProcessor processor)
        // Since Java 1.7, we can throw ReflectiveOperationException
            throws NoSuchFieldException, IllegalAccessException {

        Map.Entry<?, ?>[] table = getTable(map);
        Field nextNodeField = getNextField(table);
        Map<Integer, Integer> numberPerBucket =
                new TreeMap<Integer, Integer>();
        for (Map.Entry<?, ?> node : table) {
            processor.beginBucket();
            while (node != null) {
                processor.process(node);
                node = (Map.Entry<?, ?>) nextNodeField.get(node);
            }
            processor.endBucket(numberPerBucket);
        }
        return numberPerBucket;
    }

    private static Map.Entry<?, ?>[] getTable(Map<?, ?> map)
            throws NoSuchFieldException, IllegalAccessException {
        Field tableField = map.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        return (Map.Entry<?, ?>[]) tableField.get(map);
    }

    private static Field getNextField(Object table)
            throws NoSuchFieldException {
        Class<?> nodeType = table.getClass().getComponentType();
        Field nextNodeField = nodeType.getDeclaredField("next");
        nextNodeField.setAccessible(true);
        return nextNodeField;
    }
}