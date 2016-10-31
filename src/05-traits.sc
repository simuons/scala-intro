trait PureProtocol {
  def operation(): Unit
}

trait ProtocolWithStateAndPartialImplementation {
  private var calls = 0

  def call(count: Int): Unit

  def operation(): Unit = {
    calls += 1
    call(calls)
  }
}

// Stackable pattern

abstract class Printer {
  def print(message: String): Unit
}

class SystemOutPrinter extends Printer {
  override def print(message: String): Unit = System.out.print(message)
}

trait Caps extends Printer { // This declaration means that the trait can only be mixed into a class that also extends Printer
  abstract override def print(message: String): Unit = super.print(s"caps($message)") // abstract override means that super is resolved dynamically and allowed only in traits
}

trait NewLine extends Printer {
  abstract override def print(message: String): Unit = super.print(s"new-line($message)")
}

trait Bold extends Printer {
  abstract override def print(message: String): Unit = super.print(s"bold($message)")
}

class Printer1 extends SystemOutPrinter with Caps with Bold with NewLine

class Printer2 extends SystemOutPrinter with Bold with Caps with NewLine

class Printer3 extends SystemOutPrinter with Bold with NewLine with Caps

class Printer4 extends SystemOutPrinter with NewLine with Caps with Bold

new Printer1().print("message")
new Printer2().print("message")
new Printer3().print("message")
new Printer4().print("message")

val printer5 = new SystemOutPrinter with NewLine with Bold with Caps

printer5.print("message")
