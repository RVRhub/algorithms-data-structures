package rvr.data_structures

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class HashSetImplTest {

    @Test
    fun `Create new HashSet`() {

        val hashSet: HashSetImpl<Int> = HashSetImpl()
        hashSet.add(-5)
        hashSet.add(5)
        hashSet.add(10)
        hashSet.add(-10)
        hashSet.add(1000000000)
        hashSet.add(-1000000000)

        assertEquals(hashSet.size, 3)
    }


    @Test
    fun `Print new HashSet`() {

        val hashSet: HashSetImpl<Int> = HashSetImpl()
        hashSet.add(5)
        hashSet.add(10)
        hashSet.add(100000000)
        hashSet.add(1000000000)


        assertTrue(hashSet.iterator().hasNext())

        hashSet.forEach(System.out::print)
        assertEquals(hashSet.size, 4)
    }
}