package examples

import org.sgine.component.{Children, Component, Container, DimensionedSupport, Image, PointerSupport}
import org.sgine.layout.{BoxLayout, LayoutSupport}
import org.sgine.{Color, Compass, PointerCursor}
import reactify._

object BoxLayoutExample extends Example {
  override protected lazy val component: Component = new Container with LayoutSupport {
    width := screen.width
    height := screen.height

    layout @= new BoxLayout(
      direction = Compass.West,
      spacing = 25.0,
      offset = 50.0
    )

    override lazy val children: Children[Component] = Children(this, Vector(
      new Box,
      new Box,
      new Box,
      new Box,
      new Box
    ))
  }

  class Box extends Image("crate.jpg") with DimensionedSupport {
    width @= 300.0
    height @= 300.0
    center := screen.center
    middle := screen.middle
  }
}