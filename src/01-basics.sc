import scala.annotation.tailrec

//Class/Constructor
//Named parameters
//Default values
//Methods/Nested-methods/Argument lists
//Pass by name
//Var-args
//Expressions
//Type inference
//Objects/static methods/Constants
//Package objects
//case classes
//Partial functions

def factorial(i: Int): Long = {
  @tailrec
  def fact(i: Int, accumulator: Int): Long = {
    if (i <= 1) accumulator
    else fact(i - 1, i * accumulator)
  }

  fact(i, 1)
}

factorial(5)

(1 to 5) foreach (i => println(factorial(i)))

def fib(n: Int): Int = {
  if (n <= 1) 1
  else fib(n - 2) + fib(n - 1)
}

// Partial functions
val f1: PartialFunction[Any, String] = {
  case s: String => s"string: $s"
}

val f2: PartialFunction[Any, String] = {
  case d: Double => s"double $d"
}

val f = f1 orElse f2

println(f("test"))

f.isDefinedAt("test")
f.isDefinedAt(10)

Seq(1, 2, 3, 4)

Seq(1, Nil, "a string").collect {
  case s: String => s.toUpperCase
}

// For
for (i <- 1 to 5) println(i)

for (i <- 1 to 5 if i % 2 == 0) yield i
for (i <- 1 to 15
     if i % 2 != 0
     if i % 5 == 0) yield i


def callByValue(i: Int) = {
  println("by value unused")
}
def callByNameUnused(i: => Int) = {
  println("by name unused")
}
def callByName(i: => Int) = {
  println(s"by name $i")
}

callByValue({
  println("calling")
  42
})
callByNameUnused({
  println("calling")
  42
})
callByName({
  println("calling")
  42
})
