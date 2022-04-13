package org.sgine.widget

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.sgine._
import org.sgine.component.{DimensionedComponent, EmptyComponent}
import reactify._

class Scale9(implicit scrn: Screen) extends ComponentGroup()(scrn) {
  val topLeft: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val topRight: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val bottomLeft: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val bottomRight: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val top: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val bottom: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val left: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val right: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)
  val center: Var[DimensionedComponent] = Var[DimensionedComponent](new EmptyComponent)

  initialize()

  def slice(texture: Texture, offset: Int): Unit = {
    slice(texture, offset, offset, texture.getWidth - offset, texture.getHeight - offset)
  }

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
      c.position.left := topLeft.size.width
      c.position.top := size.height
      c.size.width := size.width - (topLeft.size.width + topRight.size.width)
      c.size.height := math.max(topLeft.size.height, topRight.size.height)
      add(c)
      t = Some(c)
    }
    bottom.attach { c =>
      b.foreach(remove)
      c.position.left := bottomLeft.size.width
      c.position.bottom := 0.0
      c.size.width := size.width - (bottomLeft.size.width) + bottomRight.size.width
      c.size.height := math.max(bottomLeft.size.height, bottomRight.size.height)
      add(c)
      b = Some(c)
    }
    left.attach { c =>
      l.foreach(remove)
      c.position.left := 0.0
      c.position.bottom := bottomLeft.size.height
      c.size.width := math.max(bottomLeft.size.width, topLeft.size.width)
      c.size.height := size.height - (bottomLeft.size.height) + topLeft.size.height
      add(c)
      l = Some(c)
    }
    right.attach { c =>
      r.foreach(remove)
      c.position.right := size.width
      c.position.bottom := bottomLeft.size.height
      c.size.width := math.max(bottomRight.size.width, topRight.size.width)
      c.size.height := size.height - (bottomRight.size.height) + topRight.size.height
      add(c)
      r = Some(c)
    }
    center.attach { c =>
      cntr.foreach(remove)
      c.position.left := math.max(bottomLeft.size.width, topLeft.size.width)
      c.position.bottom := math.max(bottomLeft.size.height, bottomRight.size.height)
      val maxLeft = math.max(bottomLeft.size.width, topLeft.size.width)
      val maxRight = math.max(bottomRight.size.width, topRight.size.width)
      c.size.width := size.width - (maxLeft + maxRight)
      val maxTop = math.max(topLeft.size.height, topRight.size.height)
      val maxBottom = math.max(bottomLeft.size.height, bottomRight.size.height)
      c.size.height := size.height - (maxTop + maxBottom)
      add(c)
      cntr = Some(c)
    }
  }
}
