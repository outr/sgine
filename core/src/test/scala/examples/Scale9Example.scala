//package examples
//
//import org.sgine.Color
//import org.sgine.component.{Component, Container, InteractiveComponent, Scale9View}
//import org.sgine.texture.{Scale9, Texture}
//import reactify._
//
//object Scale9Example extends Example {
//  private lazy val scale9 = Scale9(Texture.internal("scale9test.png"), 50, 50, 50, 50)
//
//  override protected lazy val root: Component = Container(
//    new Scale9View(scale9) with InteractiveComponent {
//      center @= screen.center
//      middle @= screen.middle
//      width @= 2000.0
//      height @= 1200.0
//
//      color := (if (pointer.over) Color.Red else Color.White)
//
//      override def toString: String = "scale9.1"
//    },
//    new Scale9View(scale9) with InteractiveComponent {
//      center @= screen.center
//      middle @= screen.middle
//      width @= 1000.0
//      height @= 800.0
//
//      color := (if (pointer.over) Color.Red else Color.White)
//
//      override def toString: String = "scale9.2"
//    },
//    new Scale9View(scale9) with InteractiveComponent {
//      center @= screen.center
//      middle @= screen.middle
//      width @= 400.0
//      height @= 400.0
//
//      color := (if (pointer.over) Color.Red else Color.White)
//
//      override def toString: String = "scale9.3"
//    }
//  )
//}
