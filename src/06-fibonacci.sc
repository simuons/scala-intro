import scala.annotation.tailrec

// Handling of negative n values
// Issues with simple recursion
// Memoization/caching prevents calculating of same value multiple times
// Tail call optimization/accumulation prevents stack from blowing up
// Iterative implementation: with collection

val examples = Seq(
  0 -> 0,
  1 -> 1,
  2 -> 1,
  3 -> 2,
  4 -> 3,
  5 -> 5,
  6 -> 8,
  10 -> 55,
  20 -> 6765,
  30 -> 832040,
  40 -> 102334155
  //  50 -> 12586269025L
)

def recursive(n: Int): Long = {
  if (n <= 1) n
  else recursive(n - 1) + recursive(n - 2)
}

def recursive_with_memoization(n: Int): Long = {
  val cache = collection.mutable.Map[Int, Long](
    0 -> 0,
    1 -> 1
  )

  def calculate(i: Int): Long = cache.getOrElseUpdate(
    i, calculate(i - 1) + calculate(i - 2)
  )

  calculate(n)
}

def recursive_with_accumulator(n: Int): Long = {
  @tailrec
  def calculate(i: Int, accumulator: Long, next: Long): Long =
    if (i == 0) accumulator
    else calculate(i - 1, next, accumulator + next)

  calculate(n, 0, 1)
}

def iterative_with_collection(n: Int): Long = {
  val seq = collection.mutable.ArrayBuffer[Long](0, 1)

  (2 to n).foreach { i =>
    seq += (seq(i - 1) + seq(i - 2))
  }

  seq(n)
}

def iterative_with_vars(n: Int): Long = {
  var (result, next) = (0, 1)

  (1 to n).foreach { _ =>
    val swap = result
    result = result + next
    next = swap
  }

  result
}

val fibonacci$: Stream[Long] = 0 #:: 1 #:: fibonacci$.zip(fibonacci$.tail).map { case (n0, n1) => n0 + n1 }

fibonacci$.take(10)

Seq(
  "recursive" -> recursive _,
  "recursive with mem" -> recursive_with_memoization _,
  "recursive with acc" -> recursive_with_accumulator _,
  "iterative with col" -> iterative_with_collection _,
  "iterative with var" -> iterative_with_vars _
).foreach { case (name, implementation) =>

  println(name)

  examples.foreach { case (n, expected) =>

    val t0 = System.nanoTime()
    val actual = implementation(n)
    val t1 = System.nanoTime()

    println(s"$n -> $actual in ${t1 - t0}ns")

    assert(actual == expected, s"$name: $actual not equal to $expected for $n")
  }

}
