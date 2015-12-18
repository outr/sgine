package org.sgine.widget

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.sgine._
import org.sgine.component.{DimensionedComponent, EmptyComponent}
import pl.metastack.metarx._

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

  def slice(texture: Texture, x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    val topLeftImage = new Image(new TextureRegion(texture, 0, 0, x1, y1))
    val topRightImage = new Image(new TextureRegion(texture, x2, 0, x1, y1))
    val bottomLeftImage = new Image(new TextureRegion(texture, 0, y2, x1, y1))
    val bottomRightImage = new Image(new TextureRegion(texture, x2, y2, x1, y1))
    val topImage = new Image(new TextureRegion(texture, x1, 0, x2 - x1, y1))
    val bottomImage = new Image(new TextureRegion(texture, x1, y2, x2 - x1, y1))
    val leftImage = new Image(new TextureRegion(texture, 0, y1, x1, y2 - y1))
    val rightImage = new Image(new TextureRegion(texture, x2, y1, x1, y2 - y1))
    val centerImage = new Image(new TextureRegion(texture, x1, y1, x2 - x1, y2 - y1))

    topLeft := topLeftImage
    topRight := topRightImage
    bottomLeft := bottomLeftImage
    bottomRight := bottomRightImage
    top := topImage
    bottom := bottomImage
    left := leftImage
    right := rightImage
    center := centerImage
  }

  protected def initialize(): Unit = {
    var tl: Option[DimensionedComponent] = None
    var tr: Option[DimensionedComponent] = None
    var bl: Option[DimensionedComponent] = None
    var br: Option[DimensionedComponent] = None
    var t: Option[DimensionedComponent] = None
    var b: Option[DimensionedComponent] = None
    var l: Option[DimensionedComponent] = None
    var r: Option[DimensionedComponent] = None
    var cntr: Option[DimensionedComponent] = None

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
    bottom.attach { c =>
      b.foreach(remove)
      c.position.left := bottomLeft.flatMap(dc => dc.size.width)
      c.position.bottom := 0.0
      c.size.width := size.width - (bottomLeft.flatMap(dc => dc.size.width) + bottomRight.flatMap(dc => dc.size.width))
      c.size.height := bottomLeft.flatMap(dc => dc.size.height).zipWith(bottomRight.flatMap(dc => dc.size.height))(math.max)
      add(c)
      b = Some(c)
    }
    left.attach { c =>
      l.foreach(remove)
      c.position.left := 0.0
      c.position.bottom := bottomLeft.flatMap(dc => dc.size.height)
      c.size.width := bottomLeft.flatMap(dc => dc.size.width).zipWith(topLeft.flatMap(dc => dc.size.width))(math.max)
      c.size.height := size.height - (bottomLeft.flatMap(dc => dc.size.height) + topLeft.flatMap(dc => dc.size.height))
      add(c)
      l = Some(c)
    }
    right.attach { c =>
      r.foreach(remove)
      c.position.right := size.width
      c.position.bottom := bottomLeft.flatMap(dc => dc.size.height)
      c.size.width := bottomRight.flatMap(dc => dc.size.width).zipWith(topRight.flatMap(dc => dc.size.width))(math.max)
      c.size.height := size.height - (bottomRight.flatMap(dc => dc.size.height) + topRight.flatMap(dc => dc.size.height))
      add(c)
      r = Some(c)
    }
    center.attach { c =>
      cntr.foreach(remove)
      c.position.left := bottomLeft.flatMap(dc => dc.size.width).zipWith(topLeft.flatMap(dc => dc.size.width))(math.max)
      c.position.bottom := bottomLeft.flatMap(dc => dc.size.height).zipWith(bottomRight.flatMap(dc => dc.size.height))(math.max)
      val maxLeft = bottomLeft.flatMap(_.size.width).zipWith(topLeft.flatMap(_.size.width))(math.max)
      val maxRight = bottomRight.flatMap(_.size.width).zipWith(topRight.flatMap(_.size.width))(math.max)
      c.size.width := size.width - (maxLeft + maxRight)
      val maxTop = topLeft.flatMap(_.size.height).zipWith(topRight.flatMap(_.size.height))(math.max)
      val maxBottom = bottomLeft.flatMap(_.size.height).zipWith(bottomRight.flatMap(_.size.height))(math.max)
      c.size.height := size.height - (maxTop + maxBottom)
      add(c)
      cntr = Some(c)
    }
  }
}
