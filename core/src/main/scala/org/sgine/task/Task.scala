package org.sgine.task

trait Task {
  def update(delta: Double, reset: Boolean): Conclusion

  def stepSize: Double = 0.0

  def andThen(that: Task): Task = new Sequential(List(this, that))

  def start(implicit taskSupport: TaskSupport): TaskInstance = taskSupport.start(this)
}

object Task {
  lazy val None: Task = (_: Double, _: Boolean) => Conclusion.Finished
}