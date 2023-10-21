package example

import java.io.{File, PrintWriter}
import scala.io.Source

// Generated using https://en.wikipedia.org/wiki/List_of_colors:_A–F
object ColorsGenerator {
  private lazy val colors = {
    val source = Source.fromURL(getClass.getClassLoader.getResource("colors.txt"))
    try {
      source.getLines().toList
    } finally {
      source.close()
    }
  }

  private val Extractor = """.*hex=(.{6}).*name=\[\[(.+)]].*""".r

  def mainDisabled(args: Array[String]): Unit = {
    val filterOut = "'’&#"
    val file = new File("colors.txt")
    val writer = new PrintWriter(file)
    writer.println("  lazy val Clear: Color = fromLong(0x00000000)")
    colors.foreach {
      case Extractor(hex, names) =>
        val pipe = names.lastIndexOf('|')
        val name = (if (pipe == -1) {
          names
        } else {
          names.substring(pipe + 1)
        })
          .replace('é', 'e')
          .filterNot(c => filterOut.contains(c))
          .split("""[()\- /.]""")
          .map(_.capitalize.trim)
          .filterNot(_.isEmpty)
          .mkString
        writer.println(s"  lazy val $name: Color = fromLong(0x${hex}FF)")
    }
    writer.flush()
    writer.close()
  }
}
