package se.roland
import java.io.PrintWriter
class Krand {
    companion object {
        val Delta = 300
        val Step = 6000
        val Iterations = 64*8640000// %%%224*8640000,%%%8640000;
        val Blocks = 1048576
        val Delta_mult = 10000
        val NumberBlocks = (Iterations / Step / Blocks + 1)
        @JvmStatic
        fun main(args: Array<String>) {
            val startTime = System.nanoTime()
            var Outs = 75.0
            val Numbers= FloatArray(Iterations)
            for (i in 0 until Iterations) {
                if (Math.random() < 0.5) {
                    if (Math.random() < 0.5) Outs = (Outs - Math.random() / Delta)
                    if (Math.random() > 0.5) Outs = (Outs + Math.random() / Delta) //adding
                }
                if (Math.random() > 0.5) {
                    if (Math.random() < 0.5) Outs = (Outs * (1 + Math.random() / Delta_mult))
                    if (Math.random() > 0.5) Outs = (Outs * (1 - Math.random() / Delta_mult))  //multyple
                }
                Numbers[i] = Outs.toFloat()
            }
            println("Loop success")
            for (i in 1..NumberBlocks) {
                proceedBlocks(Numbers, Blocks, i)
            }
            val endTime = System.nanoTime()
            val duration = endTime - startTime
            println("time execution:: " + duration / 1000000000)
        }
        fun proceedBlocks(Arr:FloatArray, ConstantBlock: Int, Block: Int) {
            val StartingFrom: Int = (Block - 1) * ConstantBlock * Step
            var FinishedBlock: Int = StartingFrom + ConstantBlock * Step
            println("Proceed Block  #$Block")
            if (FinishedBlock >= Iterations) FinishedBlock = Iterations - 1
            if (FinishedBlock < StartingFrom) return
            val open_ = PrintWriter("open$Block")
            val high_ = PrintWriter("high$Block")
            val low_ = PrintWriter("low$Block")
            val close_ = PrintWriter("close$Block")
            var counter = StartingFrom
            while (counter + Step < FinishedBlock){
                proceedRange(Arr, counter, open_, low_, high_, close_)
                counter += Step
            }
            open_.close()
            close_.close()
            high_.close()
            low_.close()
        }
        fun proceedRange(Arr: FloatArray,  StartIndex: Int, open_: PrintWriter,low_: PrintWriter, high_: PrintWriter, close_ : PrintWriter){
            val subarray = Arr.copyOfRange(StartIndex, StartIndex+ Step)
            if (subarray.size==0) return
            open_.println(subarray.get(0))
            low_.println(subarray.min())
            high_.println(subarray.max())
            close_.println(subarray.get(Step -1))
        }
    }
}
