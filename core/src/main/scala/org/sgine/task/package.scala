package org.sgine

import reactify._

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.language.implicitConversions

package object task {
  implicit def future2Task[R](future: Future[R]): Task = FutureTask[R](future)
  implicit def f2Task(f: => Unit): Task = Action(f)

  implicit object DoubleAnimatable extends Animatable[Double] {
    override def valueAt(start: Double, end: Double, position: Double): Double = {
      start + ((end - start) * position)
    }
  }

  implicit object ColorAnimate extends Animatable[Color] {
    override def valueAt(start: Color, end: Color, position: Double): Color = {
      def v(start: Double, end: Double): Double =
        math.max(0.0, math.min(1.0, DoubleAnimatable.valueAt(start, end, position)))

      val red = v(start.red, end.red)
      val green = v(start.green, end.green)
      val blue = v(start.blue, end.blue)
      val alpha = v(start.alpha, end.alpha)
      Color.fromRGBA(red, green, blue, alpha)
    }
  }

  implicit class StateChannelWorkflowAnimatable[T](state: Stateful[T] with Mutable[T]) {
    def to(destination: => T, adjustableDestination: Boolean = false)
          (implicit animatable: Animatable[T]): PartialAnimate[T] = PartialAnimate[T](
      get = () => state(),
      apply = (t: T) => state := t,
      destination = () => destination,
      adjustableDestination,
      animatable
    )
  }

  def parallel(tasks: Task*): Parallel = new Parallel(tasks.toList)
  def sequential(tasks: Task*): Sequential = new Sequential(tasks.toList)
  def sleep(duration: FiniteDuration): Sleep = new Sleep(duration)
  def asynchronous(f: => Future[Unit]): Action = Action(f)
  def futureTask(f: => Future[Unit]): FutureTask[Unit] = FutureTask(f)
  def synchronous(f: => Unit): Action = Action {
    f
    Future.successful(())
  }
  def repeat(task: Task, times: Int = 1): Repeat = new Repeat(task, times)
  def forever(task: Task): Repeat = new Repeat(task, Int.MaxValue)
}