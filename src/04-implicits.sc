import java.util.UUID

import scala.language.implicitConversions

// Arguments

// 1. Only the last argument list, including the only list for a single-list method, can have implicit arguments.
// 2. The implicit keyword must appear first and only once in the argument list. The list can’t have “nonimplicit” arguments followed by implicit arguments.
// 3. All the arguments are implicit when the list starts with the implicit keyword.

def taxes(amount: Float)(implicit rate: Float) = {
  amount * rate
}

// value in scope
{

  implicit val fixedRate = .1F

  assert(taxes(10) == 1.1F)
}

// method in scope
{
  implicit val rates = Map("LTU" -> .2F)

  implicit def ltuRate(implicit rates: Map[String, Float]): Float = rates("LTU")

  assert(taxes(10) == 2)
}

// implicitly example
{
  implicit val fixedRate = .3F

  def taxesWithImplicitly(amount: Float) = amount * implicitly[Float]

  assert(taxesWithImplicitly(10) == 3)

}

// Conversions
val uuidString = "9b237255-acbe-43d6-b7b9-f8f7ae9778ad"

def versionOf(uuid: UUID) = uuid.version()

implicit def `string to uuid`(uuid: String): UUID = UUID.fromString(uuid)

assert(versionOf(uuidString) == 4)

// detect conversion from companion object

case class Foo(s: String)

object Foo {
  implicit def fromString(s: String): Foo = Foo(s)
}

val foo: Foo = "bar"

assert(foo == Foo("bar"))


// Extension methods
implicit class StringOps(string: String) {
  def yell: String = string.toUpperCase + "!!1"
}

assert("hello".yell == "HELLO!!1")

assert(1 -> "one" == (1, "one"))

// StringContext

implicit class MapStringContext(sc: StringContext) {
  def bla(values: Any*): Map[String, Any] = {
    sc.parts.map(_.trim).zip(values).toMap
  }
}

val a = 1
val b = 2

s"a $a b $b"

assert(bla"a $a b $b" == Map("a" -> 1, "b" -> 2))

// Type classes
trait Renderer[T] {
  def render(value: T): String
}

def render[T](value: T)(implicit renderer: Renderer[T]) = renderer.render(value)

//def render[T: Renderer](value: T) = implicitly[Renderer[T]].render(value)

implicit object IntRenderer extends Renderer[Int] {
  override def render(value: Int): String = s"Int $value"
}

render(42)
//render(42.0) // doesn't compile
