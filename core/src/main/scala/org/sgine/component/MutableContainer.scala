package org.sgine.component

import reactify.{Channel, Var}

class MutableContainer[Child <: Component] extends TypedContainer[Child] {
  container =>
  def this(children: Child*) = {
    this()
    this.children.addAll(children: _*)
  }

  override object children extends Var[List[Child]](Nil) {
    val added: Channel[Child] = Channel[Child]
    val removed: Channel[Child] = Channel[Child]

    changes {
      case (oldValue, newValue) =>
        val rem = oldValue.diff(newValue)
        val add = newValue.diff(oldValue)
        rem.foreach(child => removed @= child)
        add.foreach(child => added @= child)
    }

    removed.attach { child =>
      child._parent @= None
    }
    added.attach { child =>
      child._parent @= Some(container)
    }

    def +=(child: Child): Unit = add(child)

    def -=(child: Child): Unit = remove(child)

    def add(child: Child): Child = {
      addAll(child)
      child
    }

    def addAll(children: Child*): List[Child] = {
      val list = children.toList
      this @= get ::: list
      list
    }

    def remove(child: Child): Child = {
      removeAll(child)
      child
    }

    def removeAll(children: Child*): List[Child] = {
      val list = children.toList
      this @= get.filterNot(list.contains)
      list
    }
  }
}