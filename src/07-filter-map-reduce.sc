def fold[I, O](seq: Seq[I])(zero: O)(aggregate: (O, I) => O): O = seq match {
  case Nil => zero
  case head +: tail => fold(tail)(aggregate(zero, head))(aggregate)
}

def reduce[T](seq: Seq[T])(aggregate: (T, T) => T): T = seq match {
  case Nil => throw new IllegalArgumentException("Reduce is not supported for empty sequences")
  case head +: tail => fold(tail)(head)(aggregate)
}

def filter[T](seq: Seq[T])(predicate: T => Boolean): Seq[T] =
  fold[T, Seq[T]](seq)(Seq.empty)((accumulator, value) =>
    if (predicate(value)) accumulator :+ value
    else accumulator
  )

def map[I, O](seq: Seq[I])(transform: I => O): Seq[O] =
  fold[I, Seq[O]](seq)(Seq.empty)((accumulator, value) =>
    accumulator :+ transform(value)
  )

assert(fold(1 to 5)(0)(_ + _) == 15)
assert(reduce(1 to 5)(_ + _) == 15)
assert(filter(1 to 5)(_ % 2 == 0) == Seq(2, 4))
assert(map(1 to 5)(_.toString) == Seq("1", "2", "3", "4", "5"))
