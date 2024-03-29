package org.sgine.component

import reactify.Var

trait MutableContainer[Child <: Component] extends TypedContainer[Child] { self =>
  override lazy val children: MutableChildren = new MutableChildren

  class MutableChildren extends Var[Vector[Child]](Vector.empty) with Children[Child] {
    override protected def container: Component = self

    def +=(child: Child): Unit = add(child)

    def -=(child: Child): Unit = remove(child)

    def add(child: Child): Child = {
      addAll(child)
      child
    }

    def addAll(children: Child*): Vector[Child] = {
      val vector = children.toVector
      this @= (get ++ vector)
      vector
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

    def clear(): Unit = this @= Vector.empty
  }
}