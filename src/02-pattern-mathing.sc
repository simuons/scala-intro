// Destructuring on assignment

val (question, answer) = ("Meaning of life", 42)
val x1 +: x2 +: xs = Seq(1, 2, 3, 4)
val List(a, b) = xs
val Some(value) = Some("really really important value like 42")

// Basic pattners
val numbers = Seq(1, 2L, 3.0, 4F, '5', 'six)

for (n <- numbers) {
  println(
    n match {
      case 1 => "int: 1" // constant
      case _: Long => s"long: $n" // typed
      case _: Double | _: Float => s"floating point: $n" // typed with or
      case c: Char if c.isDigit => s"char: $c" // variable with guard
      case _ => s"unknown: $n" // wildcard
    }
  )
}

// Tuples

val langs = Seq(
  ("Scala", ("Martin", "Odersky")),
  ("Lisp", ("John", "McCarthy")),
  ("Clojure", ("Rich", "Hickey")),
  ("Ruby", ("Yukihiro", "Matsumoto"))
)

for (tuple <- langs) {
  println(
    tuple match {
      case ("Scala", _) => "Found scala"
      case (langauge@("Clojure" | "Lisp"), author) => s"A $langauge by $author"
      case ("Ruby", (_, surname)) => s"Ruby was created by $surname"
    }
  )
}

// Classes

class Person(val name: String, val surname: String, age: Int)

object Person {
  def unapply(person: Person): Option[(String, String)] =
    Some((person.name, person.surname))
}

val person = new Person("John", "Smith", 42)

person match {
  case Person(firstName, _) => s"Hello $firstName from"
}

// Extractors: fixed size

class Email(user: String, domain: String)

object Email {
  def unapply(string: String): Option[(String, String)] = {
    val parts = string split '@'
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}

for (string <- Seq("not-an-email", "someone@somewhere")) {
  println(
    string match {
      case Email(user, domain) => s"$user AT $domain"
      case _ => s"$string doesn't pattern-match"
    }
  )
}

// Extractors: variable length

object Name {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}

val names = Seq("Damme", "Van Damme", "Claude Van Damme", "Jean Claude Van Damme", "")

for (name <- names) {
  println(
    name match {
      case Name(first) => first
      case Name(first, last) => s"$first-$last"
      case Name(first, middle, rest@_*) => s"$first-$middle,-$rest"
      case Name(x@_*) => s"Doh, to long: ${x.mkString("-")}"
      case _ => "No name"
    }
  )
}

// Sealed classes: exhaustive match

sealed abstract class Fruit

class Apple extends Fruit

class Orange extends Fruit

val fruits: Seq[Fruit] = Seq(new Apple, new Orange)

for (fruit <- fruits) {
  println(
    fruit match {
      case _: Apple => "apple"
      case _: Orange => "orange"
    }
  )
}

// Sequences: example recursion with pattern match

def toString[T](s: Seq[T]): String = s match {
  case head +: tail => s"$head +: ${toString(tail)}"
  case Nil => "Nil"
}

toString(numbers)
