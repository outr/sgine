package org.sgine

import org.sgine.task._
import reactify._

class ScreenManager extends Val[Vector[Screen]](Vector.empty) with TaskSupport { sm =>
  val adding: Channel[Screen] = Channel[Screen]
  val added: Channel[Screen] = Channel[Screen]
  val removing: Channel[Screen] = Channel[Screen]
  val removed: Channel[Screen] = Channel[Screen]

  def :=(screen: => Screen): Unit = replace(screen)
  def @=(screen: Screen): Unit = replace(screen)

  /**
   * Adds the supplied screen to the existing screens currently active.
   */
  def add(screen: Screen, transition: Boolean = true): Unit = start(addTask(screen, transition))

  /**
   * The task the applies adding a screen.
   */
  def addTask(screen: Screen, transition: Boolean = true): Task = {
    val transitionTask = if (transition) {
      screen.activate()
    } else {
      Task.None
    }
    sequential(
      adding @= screen,
      Action {
        val vector = get :+ screen
        set(vector)
      },
      transitionTask,
      added @= screen,
    )
  }

  /**
   * Removes the supplied screen from the current list of active screens.
   */
  def remove(screen: Screen, transition: Boolean = true): Boolean = {
    val task = removeTask(screen, transition)
    start(task)
    task != Task.None
  }

  /**
   * The remove screen task.
   */
  def removeTask(screen: Screen, transition: Boolean = true): Task = if (screen.locked || !get.contains(screen)) {
    Task.None
  } else {
    val transitionTask = if (transition) {
      screen.deactivate()
    } else {
      Task.None
    }
    sequential(
      removing @= screen,
      transitionTask,
      Action {
        val vector = get.filterNot(_ == screen)
        set(vector)
      },
      removed @= screen
    )
  }

  /**
   * Replaces all of the currently active screens with the supplied screen optionally disabling transitions.
   */
  def replace(screen: Screen,
              transitionRemove: Boolean = true,
              transitionAdd: Boolean = true): Unit = {
    val task = addTask(screen, transitionAdd) :: clearTasks(Set(screen), transitionRemove)
    start(parallel(task: _*))
  }

  /**
   * Convenience method to immediately set a screen active and hide currently set screens without transitions. Useful
   * for initially setting up.
   */
  def set(screen: Screen): Unit = replace(screen, transitionRemove = false, transitionAdd = false)

  /**
   * Clears all active screens.
   */
  def clear(exclude: Set[Screen] = Set.empty,
            transition: Boolean = true): Unit = start(parallel(clearTasks(exclude, transition): _*))

  /**
   * The clear tasks to remove the currently active screens.
   */
  def clearTasks(exclude: Set[Screen] = Set.empty,
                 transition: Boolean = true): List[Task] = get.toList.filterNot(exclude.contains).map { screen =>
    removeTask(screen, transition)
  }

  override def update(delta: Double): Unit = {
    super.update(delta)
    get.foreach(_.update(delta))
  }
}
