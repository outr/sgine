package org.sgine.task

import org.sgine.update.UpdateSupport

trait TaskSupport extends UpdateSupport {
  implicit def taskSupport: TaskSupport = this

  protected def createInstance(task: Task): TaskInstance = new TaskInstance(task, updates)

  def start(task: Task): TaskInstance  = {
    val instance = createInstance(task)
    instance.start()
    instance
  }

  def updateTasks(): Boolean = true
}
