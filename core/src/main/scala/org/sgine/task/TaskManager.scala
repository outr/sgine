package org.sgine.task

import java.util.concurrent.atomic.AtomicInteger

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net.{HttpMethods, HttpResponse, HttpResponseListener}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureRegion}
import com.badlogic.gdx.net.HttpRequestBuilder
import com.badlogic.gdx.utils.async.{AsyncExecutor, AsyncTask}
import org.sgine._

class TaskManager(maxConcurrent: Int = 4,
                  autoStart: Boolean = true,
                  allowTasksAfterSTart: Boolean = true) {
  private var started = false
  private val executor = new AsyncExecutor(maxConcurrent)

  private var backlog = List.empty[Task]
  private[task] val _queued = new AtomicInteger(0)
  private[task] val _running = new AtomicInteger(0)

  def add(task: Task): Unit = synchronized {
    if (started) {
      if (allowTasksAfterSTart) {
        submit(task)
      } else {
        throw new RuntimeException("Tasks not allowed to be added after task manager started.")
      }
    } else {
      backlog = task :: backlog
    }
  }

  def start(): Unit = synchronized {
    if (!started) {
      started = true
      backlog.reverse.foreach(submit)
      backlog = Nil
    }
  }

  private def submit(task: Task): Unit = {
    _queued.incrementAndGet()
    executor.submit(task)
  }

  def dispose(): Unit = {
    executor.dispose()
  }

  def apply(f: => Unit) = {
    add(new FunctionalTask(this, () => f))
  }

  def futureTask[T](f: () => T, autoAdd: Boolean = true) = {
    val future = new FutureObject[T](this, f)
    if (autoAdd) add(future)
    future
  }

  def future[T](f: => T) = futureTask(() => f)

  def download(url: String, local: FileHandle, autoAdd: Boolean = true): FutureObject[FileHandle] = futureTask(() => {
    if (!local.exists()) {
      val request = new HttpRequestBuilder().newRequest().method(HttpMethods.GET).timeout(15000).url(url).build()
      var finished = false
      Gdx.net.sendHttpRequest(request, new HttpResponseListener {
        override def handleHttpResponse(httpResponse: HttpResponse) = {
          local.writeBytes(httpResponse.getResult, false)
          finished = true
        }

        override def cancelled() = {}

        override def failed(t: Throwable) = {
          t.printStackTrace()
        }
      })
      waitFor(Double.MaxValue) {
        finished
      }
    }
    local
  }, autoAdd)

  def downloadLocal(base: String, filename: String, local: String, autoAdd: Boolean = true): FutureObject[FileHandle] = {
    download(s"$base/$filename", Gdx.files.local(s"$local/$filename"), autoAdd)
  }

  def font(family: String, style: String, size: Int): FutureObject[BitmapFont] = future {
    val fnt = downloadLocal("http://bitmapfonts.outr.com/font", s"$family.$style.$size.fnt", "fonts", autoAdd = false)
    val png = downloadLocal("http://bitmapfonts.outr.com/font", s"$family.$style.$size.png", "fonts", autoAdd = false)
    fnt.invoke()
    png.invoke()
    if (!fnt().exists()) {
      throw new RuntimeException(s"Font file doesn't exist: ${fnt()}.")
    }
    if (!png().exists()) {
      throw new RuntimeException(s"Font texture doesn't exist: ${png()}.")
    }

    ui.render.request {
      val texture = new Texture(png(), true)
      texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear)
      new BitmapFont(fnt(), new TextureRegion(texture), false)
    }
  }
}

trait Task extends AsyncTask[Unit] {
  def manager: TaskManager

  override final def call() = {
    manager._running.incrementAndGet()
    try {
      invoke()
    } finally {
      manager._running.decrementAndGet()
      manager._queued.decrementAndGet()
    }
  }

  def invoke(): Unit
}

class FunctionalTask(val manager: TaskManager, f: () => Unit) extends Task {
  override def invoke() = f()
}

class FutureObject[T](val manager: TaskManager, f: () => T) extends Task {
  private var result: Option[T] = None
  private var next: Option[T => Unit] = None

  def apply(maxWait: Double = 60.0) = {
    waitFor(maxWait, errorOnTimeout = true) {
      result.nonEmpty
    }
    result.get
  }

  def get() = result

  override def invoke() = {
    val r = f()
    result = Some(r)
    next match {
      case Some(n) => n(r)
      case None => // Nothing to do next
    }
  }

  def andThen(f: T => Unit) = if (result.nonEmpty) {
    f(result.get)
  } else {
    next = Some(f)
  }
}