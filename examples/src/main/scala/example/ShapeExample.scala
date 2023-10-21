//package example
//
//import io.youi.Color
//import org.sgine._
//import org.sgine.lwjgl.BasicDesktopApp
//import org.sgine.widget.{Image, Shape}
//
//object ShapeExample extends BasicDesktopApp {
//  this += new Image("sgine.png") {
//    position.center := ui.center
//    position.middle := ui.middle
//  }
//  this += new Shape {
//    position.center := ui.center
//    position.middle := ui.middle
//    size.width := 200.0
//    size.height := 200.0
//    color := Color.Red
//
//    override def draw(): Unit = {
//      doLine {
//        setColor(Color.White)
//        circle(size.width.get / 2.0, size.height.get / 2.0, size.width.get / 2.0, 100)
//        setColor(Color.Red)
//        rect(0.0, 0.0, size.width.get, size.height.get)
//      }
//      doFilled {
//        rect(25.0, 25.0, size.width.get - 50.0, size.height.get - 50.0, Color.Red, Color.Green, Color.Blue, Color.White)
//      }
//    }
//
//    forever(
//      rotation transitionTo 360.0 in 5.seconds and(color.alpha transitionTo 0.0 in 5.seconds)
//      andThen function {
//        rotation := 0.0
//      }
//      andThen(rotation transitionTo 360.0 in 5.seconds and(color.alpha transitionTo 1.0 in 5.seconds))
//      andThen function {
//        rotation := 0.0
//      }
//    ).start(ui)
//  }
//}