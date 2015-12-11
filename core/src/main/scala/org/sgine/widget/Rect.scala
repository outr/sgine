package org.sgine.widget

import org.sgine._

class Rect(implicit screen: Screen) extends Image()(screen) {
  drawable := Some(textureRegion2Drawable(pixel))
}
