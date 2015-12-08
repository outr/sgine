package org.sgine.component

import org.sgine.component.prop.SizeProperties

trait SizedComponent extends Component {
  val size = new SizeProperties
}
