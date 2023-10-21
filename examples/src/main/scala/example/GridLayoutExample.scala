package example

import org.sgine.component._
import org.sgine.layout.{GridLayout, LayoutSupport}
import reactify._

object GridLayoutExample extends Example {
  private lazy val gridLayout = new GridLayout(3)

  override protected lazy val component: Component = new Container with LayoutSupport {
    width := screen.width
    height := screen.height

    layout @= gridLayout

    override lazy val children: Children[Component] = Children(this, (0 until 50).toVector.map { _ =>
      new Box
    })
  }

  class Box extends Image("crate.jpg") with DimensionedSupport with PointerSupport {
    width @= 300.0
    height @= 300.0
    center := screen.center
    middle := screen.middle
    pointer.down.on(gridLayout.columns @= gridLayout.columns + 1)
  }
}