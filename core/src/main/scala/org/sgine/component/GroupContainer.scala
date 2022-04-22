package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Group

trait GroupContainer extends ActorComponent[Group] with Container {
  override lazy val actor: Group = new Group
}
