import edu.princeton.cs.algs4.Alphabet
import edu.princeton.cs.algs4.BinaryStdIn
import edu.princeton.cs.algs4.BinaryStdOut

class GenomeArchiver {

    fun compress() {
        val DNA = Alphabet.DNA
        val s = BinaryStdIn.readString()
        val n = s.length
        BinaryStdOut.write(n)

        // Write two-bit code for char.
        for (i in 0 until n) {
            val d = DNA.toIndex(s.get(i))
            BinaryStdOut.write(d, 2)
        }
        BinaryStdOut.close()
    }

    fun expand() {
        val DNA = Alphabet.DNA
        val n = BinaryStdIn.readInt()
        // Read two bits; write char.
        for (i in 0 until n) {
            val c = BinaryStdIn.readChar(2)
            BinaryStdOut.write(DNA.toChar(c.toInt()), 8)
        }
        BinaryStdOut.close()
    }

}



fun main(args: Array<String>) {
    if (args[0] == "-")
        GenomeArchiver().compress()
    else if (args[0] == "+")
        GenomeArchiver().expand()
    else
        throw IllegalArgumentException("Illegal command line argument")
}