package examples

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.sgine.component.Component
import org.sgine.{Screen, UI}
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.texture.TextureReference

object HelloSgine extends Screen {
  private lazy val ref = TextureReference(new TextureRegion({
    val t = new Texture(Gdx.files.internal("sgine.png"), true)
    t.setFilter(TextureFilter.MipMap, TextureFilter.MipMap)
    t
  }))

  override protected def root: Component = new Component with Renderable {
    override def render(context: RenderContext): Unit = {
      context.draw(ref, 0.0, 0.0, 1.0, 1.0, 0.0, Color.WHITE, false, false)
    }
  }

  def main(args: Array[String]): Unit = {
    val ui = new UI(this)
    ui.run()
  }
}