package org.sgine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cursor
import org.sgine.event.pointer.{PointerDraggedEvent, PointerEvent, PointerMovedEvent}
import reactify.{Channel, Val, Var}

object Pointer {
  private val displayX: Var[Int] = Var(0)
  private val displayY: Var[Int] = Var(0)

  private val screenX: Var[Double] = Var(0.0)
  private val screenY: Var[Double] = Var(0.0)

  val cursor: Var[PointerCursor] = {
    val v = Var[PointerCursor](PointerCursor.Arrow)
    v.attach {
      case PointerCursor.Custom(cursor) => Gdx.graphics.setCursor(cursor)
      case PointerCursor.Arrow => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow)
      case PointerCursor.IBeam => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Ibeam)
      case PointerCursor.Crosshair => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair)
      case PointerCursor.Hand => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand)
      case PointerCursor.HorizontalResize => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize)
      case PointerCursor.VerticalResize => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize)
      case PointerCursor.NWSEResize => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NWSEResize)
      case PointerCursor.NESWResize => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NESWResize)
      case PointerCursor.AllResize => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.AllResize)
      case PointerCursor.NotAllowed => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed)
      case PointerCursor.None => Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None)
    }
    v
  }

  object display {
    def x: Val[Int] = displayX
    def y: Val[Int] = displayY
  }

  object screen {
    def x: Val[Double] = screenX
    def y: Val[Double] = screenY
  }

  def update(displayX: Int, displayY: Int, screenX: Double, screenY: Double): Unit = {
    this.displayX @= displayX
    this.displayY @= displayY
    this.screenX @= screenX
    this.screenY @= screenY
  }
}