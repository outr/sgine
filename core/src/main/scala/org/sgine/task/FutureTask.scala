package org.sgine.task

import scala.concurrent.Future

class FutureTask[R](futureFunction: () => Future[R]) extends Task {
  private var future: Future[R] = _

  override def update(delta: Double, reset: Boolean): Conclusion = {
    scribe.info(s"FutureTask update!")
    if (reset) {
      scribe.info(s"Resetting!")
      future = futureFunction()
    }
    scribe.info(s"Completed? ${future.isCompleted}")
    if (future.isCompleted) {
      Conclusion.Finished
    } else {
      Conclusion.Continue
    }
  }
}

object FutureTask {
  def apply[R](f: => Future[R]): FutureTask[R] = new FutureTask[R](() => f)
}