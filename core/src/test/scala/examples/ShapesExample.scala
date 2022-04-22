//package examples
//
//import org.sgine.Color
//import org.sgine.component.{Component, DimensionedComponent}
//import org.sgine.render.{RenderContext, Renderable}
//import space.earlygrey.shapedrawer.JoinType
//import reactify._
//
//object ShapesExample extends Example {
//  override protected def root: Component = new DimensionedComponent with Renderable {
//    center @= screen.center
//    middle @= screen.middle
//    width @= 1200.0
//    height @= 600.0
//
//    override def render(context: RenderContext): Unit = {
//      val m = matrix4(context)
//      context.filledRectangle(0.0, 0.0, width, height, m, Color.Red)
//      context.rectangle(100.0, 100.0, width - 200.0, height - 200.0, m, Color.White, 20.0)
//      context.circle(width / 2.0, height / 2.0, 100.0, m, Color.DarkMagenta, 20.0, JoinType.SMOOTH)
//      context.line(150.0, 150.0, width - 150.0, 150.0, m, Color.DarkBlue, 20.0)
//    }
//  }
//}
