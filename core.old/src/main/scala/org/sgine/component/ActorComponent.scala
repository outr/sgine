package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Actor

trait ActorComponent[A <: Actor] extends Component {
  def actor: A
}
