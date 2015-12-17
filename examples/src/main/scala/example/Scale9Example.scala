package example

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.sgine._
import org.sgine.component.DimensionedComponent
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.{ComponentGroup, Image}
import pl.metastack.metarx._

object Scale9Example extends BasicDesktopApp {
  lazy val texture: Texture = "scale9test.png"
  lazy val topLeftImage = new Image(new TextureRegion(texture, 0, 0, 50, 50))
  lazy val topRightImage = new Image(new TextureRegion(texture, 450, 0, 50, 50))
  lazy val bottomLeftImage = new Image(new TextureRegion(texture, 0, 450, 50, 50))
  lazy val bottomRightImage = new Image(new TextureRegion(texture, 450, 450, 50, 50))
  lazy val topImage = new Image(new TextureRegion(texture, 50, 0, 400, 50))

  this += new Scale9 {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
    size.width := 80.pctw
    size.height := 80.pcth

    topLeft := topLeftImage
    topRight := topRightImage
    bottomLeft := bottomLeftImage
    bottomRight := bottomRightImage
    top := topImage
  }
}

class Scale9(implicit scrn: Screen) extends ComponentGroup()(scrn) {
  val topLeft: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val topRight: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val bottomLeft: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val bottomRight: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val top: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val bottom: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val left: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val right: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)
  val center: Sub[DimensionedComponent] = Sub[DimensionedComponent](new EmptyComponent)

  initialize()

  protected def initialize(): Unit = {
    var tl: Option[DimensionedComponent] = None
    var tr: Option[DimensionedComponent] = None
    var bl: Option[DimensionedComponent] = None
    var br: Option[DimensionedComponent] = None
    var t: Option[DimensionedComponent] = None

    topLeft.attach { c =>
      tl.foreach(remove)
      c.position.left := 0.0
      c.position.top := size.height
      add(c)
      tl = Some(c)
    }
    topRight.attach { c =>
      tr.foreach(remove)
      c.position.right := size.width
      c.position.top := size.height
      add(c)
      tr = Some(c)
    }
    bottomLeft.attach { c =>
      bl.foreach(remove)
      c.position.left := 0.0
      c.position.bottom := 0.0
      add(c)
      bl = Some(c)
    }
    bottomRight.attach { c =>
      br.foreach(remove)
      c.position.right := size.width
      c.position.bottom := 0.0
      add(c)
      br = Some(c)
    }
    top.attach { c =>
      t.foreach(remove)
      c.position.left := topLeft.flatMap(dc => dc.size.width)
      c.position.top := size.height
      c.size.width := size.width - (topLeft.flatMap(dc => dc.size.width) + topRight.flatMap(dc => dc.size.width))
      c.size.height := topLeft.flatMap(dc => dc.size.height).zipWith(topRight.flatMap(dc => dc.size.height))(math.max)
      add(c)
      t = Some(c)
    }
  }
}

class EmptyComponent(implicit val screen: Screen) extends DimensionedComponent() {
  size.width := 0.0
  size.height := 0.0
}