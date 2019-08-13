package rvr.data_structures

import org.junit.jupiter.api.Test

class HashMapTest {

    @Test
    fun `Create new HashMap`() {
        println(Integer.toBinaryString(1));
        println(Integer.toBinaryString(Integer.MAX_VALUE));
        println(Integer.toBinaryString(Integer.MAX_VALUE/2+1));
        println(Integer.toBinaryString((1 ushr 16)));
        println(Integer.toBinaryString(Integer.MAX_VALUE ushr 16));
        println(Integer.toBinaryString(Integer.MAX_VALUE xor ( Integer.MAX_VALUE ushr 16)));


        //println(Double.MAX_VALUE.doubleToRawLongBits);
        println(Double.MAX_VALUE.hashCode() ushr 16);

    }


    fun toBinary(decimalNumber: Float, binaryString: String = "") : String {
        while (decimalNumber > 0) {
            var modTwo = "%.0f".format(decimalNumber % 2)
            val temp = "${binaryString}${modTwo}"
            return toBinary(decimalNumber/2, temp)
        }
        return binaryString.reversed()
    }

}