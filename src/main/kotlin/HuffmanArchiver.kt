import edu.princeton.cs.algs4.BinaryDump
import edu.princeton.cs.algs4.BinaryStdIn
import edu.princeton.cs.algs4.MinPQ
import rvr.algo.BinaryStdOutExtended


object Huffman {

    // alphabet size of extended ASCII
    private val R = 256

    // Huffman trie node
    private class Node internal constructor(
        internal val ch: Char,
        internal val freq: Int,
        internal val left: Node?,
        internal val right: Node?
    ) : Comparable<Node> {

        // is the node a leaf node?
        internal val isLeaf: Boolean
            get() {
                assert(left == null && right == null || left != null && right != null)
                return left == null && right == null
            }

        // compare, based on frequency
        override fun compareTo(that: Node): Int {
            return this.freq - that.freq
        }
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to standard output.
     */
    fun compress() {
        val s = "ABCDEEEE"
        val input = s.toCharArray()

        // tabulate frequency counts
        val freq = IntArray(R)
        for (i in input.indices)
            freq[input[i].toInt()]++

        // build Huffman trie
        val root = buildTrie(freq)

        // build code table
        val st = arrayOfNulls<String>(R)
        buildCode(st, root, "")

        // print trie for decoder
        writeTrie(root)

        // print number of bytes in original uncompressed message
        BinaryStdOutExtended.write(input.size)

        // use Huffman code to encode input
        for (i in input.indices) {
            val code:String = st[input[i].toInt()].toString()
            for (j in 0 .. code.length-1) {
                if (code.get(j) == '0') {
                    BinaryStdOutExtended.write(false)
                } else if (code.get(j) == '1') {
                    BinaryStdOutExtended.write(true)
                } else
                    throw IllegalStateException("Illegal state")
            }
        }

        // close output stream
        BinaryStdOutExtended.close()
    }

    // build the Huffman trie given frequencies
    private fun buildTrie(freq: IntArray): Node {

        // initialze priority queue with singleton trees
        val pq = MinPQ<Node>()
        for (i in 0..R-1)
            if (freq[i] > 0)
                pq.insert(Node(i.toChar(), freq[i], null, null))

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
            if (freq[0] == 0)
                pq.insert(Node(0.toChar(), 0, null, null))
            else
                pq.insert(Node(1.toChar(), 0, null, null))
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            val left = pq.delMin()
            val right = pq.delMin()
            val parent = Node(0.toChar(), left.freq + right.freq, left, right)
            pq.insert(parent)
        }
        return pq.delMin()
    }


    // write bitstring-encoded trie to standard output
    private fun writeTrie(x: Node) {
        if (x.isLeaf) {
            BinaryStdOutExtended.write(true)
            BinaryStdOutExtended.write(x.ch, 8)
            return
        }
        BinaryStdOutExtended.write(false)
        writeTrie(x.left!!)
        writeTrie(x.right!!)
    }

    // make a lookup table from symbols and their encodings
    private fun buildCode(st: Array<String?>, x: Node, s: String) {
        if (!x.isLeaf) {
            buildCode(st, x.left!!, s + '0')
            buildCode(st, x.right!!, s + '1')
        } else {
            st[x.ch.toInt()] = s
        }
    }

    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     */
    fun expand() {

        // read in Huffman trie from input stream
        val root = readTrie()

        // number of bytes to write
        val length = BinaryStdIn.readInt()

        // decode using the Huffman trie
        for (i in 0 until length) {
            var x: Node? = root
            while (!x!!.isLeaf) {
                val bit = BinaryStdIn.readBoolean()
                if (bit)
                    x = x.right
                else
                    x = x.left
            }
            BinaryStdOutExtended.write(x.ch, 8)
        }
        BinaryStdOutExtended.close()
    }


    private fun readTrie(): Node {
        val isLeaf = BinaryStdIn.readBoolean()
        return if (isLeaf) {
            Node(BinaryStdIn.readChar(), -1, null, null)
        } else {
            Node('0', -1, readTrie(), readTrie())
        }
    }

    /**
     * Sample client that calls `compress()` if the command-line
     * argument is "-" an `expand()` if it is "+".
     *
     * @param args the command-line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
       // if (args[0] == "-")
            compress()
       // else if (args[0] == "+")
       //     expand()
       // else
        ///    throw IllegalArgumentException("Illegal command line argument")
    }

}