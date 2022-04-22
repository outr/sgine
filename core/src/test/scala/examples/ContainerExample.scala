//package examples
//
//import org.sgine.Color
//import org.sgine.component.{Component, DimensionedComponent, Image, InteractiveComponent, MutableContainer}
//import org.sgine.task._
//
//import scala.concurrent.duration.DurationInt
//
//object ContainerExample extends Example with TaskSupport {
//  private lazy val crate = new Image("crate.jpg") {
//    override def toString: String = "crate"
//  }
//  private lazy val container = new MutableContainer[Component] with DimensionedComponent with InteractiveComponent {
//    private var index = 0
//    private val actions = Vector(
//      () => {
//        center := screen.center
//        middle := screen.middle
////        rotation @= 0.0
//      },
//      () => {
//        right := screen.width
//        bottom := screen.height
//      },
//      () => {
//        center := screen.center
//        middle := screen.middle
////        rotation @= 45.0
//      }
//    )
//
//    children.add(crate)
//
//    center := screen.center
//    middle := screen.middle
////    (rotation to 360.0 in 5.seconds).start
//
//    pointer.over.attach {
//      case true => crate.color @= Color.Red
//      case false => crate.color @= Color.White
//    }
//    pointer.down.on {
//      index += 1
//      if (index >= actions.length) {
//        index = 0
//      }
//      actions(index)()
//    }
//
//    override def toString: String = "container"
//  }
//
//  override protected lazy val root: Component = container
//}