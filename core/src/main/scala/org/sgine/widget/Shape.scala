package org.sgine.widget

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.ui.{Widget => GDXWidget}
import org.sgine.Screen
import org.sgine.component.ActorWidget

class Shape(implicit val screen: Screen) extends ActorWidget[ShapeActor] {
  val actor: ShapeActor = new ShapeActor


}

class ShapeActor extends GDXWidget {
  lazy val renderer = new ShapeRenderer

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    super.draw(batch, parentAlpha)

    batch.end()

    renderer.setProjectionMatrix(batch.getProjectionMatrix)
    renderer.setTransformMatrix(batch.getTransformMatrix)
    renderer.translate(getX, getY, 0.0f)

    renderer.begin(ShapeType.Line)
    val color = getColor
    renderer.box(0.0f, 0.0f, 0.0f, getWidth, getHeight, 0.0f)
    renderer.setColor(color.r, color.g, color.b, color.a * parentAlpha)
    renderer.circle(getWidth / 2.0f, getHeight / 2.0f, getWidth / 2.0f, 100)
    renderer.end()

    batch.begin()
  }
}