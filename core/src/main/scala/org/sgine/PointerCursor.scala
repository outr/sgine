package org.sgine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Cursor, Pixmap}

sealed trait PointerCursor

object PointerCursor {
  case class Custom(cursor: Cursor) extends PointerCursor
  case object Arrow extends PointerCursor
  case object IBeam extends PointerCursor
  case object Crosshair extends PointerCursor
  case object Hand extends PointerCursor
  case object HorizontalResize extends PointerCursor
  case object VerticalResize extends PointerCursor
  case object NWSEResize extends PointerCursor
  case object NESWResize extends PointerCursor
  case object AllResize extends PointerCursor
  case object NotAllowed extends PointerCursor
  case object None extends PointerCursor

  def fromPixmap(pixmap: Pixmap,
                 xHotspot: Int,
                 yHotspot: Int): PointerCursor = Custom(Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot))
}