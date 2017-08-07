import scala.annotation.tailrec

//Class/Constructor
//Named parameters
//Default values
//Methods/Nested-methods/Argument lists
//Partially applied functions/Currying
//Pass by name/value
//Var-args
//Expressions
//Type inference
//Objects/static methods/Constants
//Package objects
//case classes
//Partially defined functions

// Call by value/name
def callByValue(i: Int) = s"$i $i $i"
def callByName(i: => Int) = s"$i $i $i"
def callByNameUnused(i: => Int) = {}

val counter = new java.util.concurrent.atomic.AtomicInteger
callByValue(counter.incrementAndGet())
callByName(counter.incrementAndGet())
val tmp = counter.get
callByNameUnused(counter.incrementAndGet())
tmp == counter.get

// Partially applied functions
def add(a: Int, b: Int) = a + b

val add2 = add(2, _:Int)

add2(6)

def wrap(prefix: String)(html: String)(suffix: String) = prefix + html + suffix

wrap("<div>")("Hi")("</div>")

val div = wrap("<div>")(_: String)("</div>")

div("Hello")

// Currying
val addFunction = add _ // (Int, Int) => Int
val addCurry: (Int) => (Int) => Int = addFunction.curried

addCurry(2)(3)

// Partially defined functions
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
