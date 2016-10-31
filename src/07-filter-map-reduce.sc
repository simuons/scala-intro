def fold[I, O](seq: Seq[I])(zero: O)(operation: (O, I) => O): O = seq match {
  case head +: tail => fold(tail)(operation(zero, head))(operation)
  case Nil => zero
}

def reduce[T](seq: Seq[T])(operation: (T, T) => T): T = seq match {
  case head +: tail => fold(tail)(head)(operation)
  case Nil => throw new IllegalAccessException("Reduce is not supported for empty sequences")
}

def filter[T](seq: Seq[T])(predicate: T => Boolean): Seq[T] =
  fold[T, Seq[T]](seq)(Seq.empty)((accumulator, value) =>
    if (predicate(value))
      accumulator :+ value
    else
      accumulator
  )

def map[I, O](seq: Seq[I])(operation: I => O): Seq[O] =
  fold[I, Seq[O]](seq)(Seq.empty)((accumulator: Seq[O], value: I) =>
    accumulator :+ operation.apply(value)
  )

assert(fold(1 to 5)(0)(_ + _) == 15)
assert(reduce(1 to 5)(_ + _) == 15)
assert(filter(1 to 5)(_ % 2 == 0) == Seq(2, 4))
assert(map(1 to 5)(_.toString) == Seq("1", "2", "3", "4", "5"))
