package org.sgine.tools

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Pixmap

import scala.annotation.tailrec

object Scale9Trimmer {
  def trim(file: FileHandle): Unit = {
    val pixmap = new Pixmap(file)
    val horizontal = checkColumn(0, pixmap, new Array[Int](pixmap.getHeight), new Array[Int](pixmap.getHeight))
    val vertical = checkRow(0, pixmap, new Array[Int](pixmap.getWidth), new Array[Int](pixmap.getWidth))
    println(horizontal)
    println(vertical)
  }

  @tailrec
  private def checkColumn(x: Int, pixmap: Pixmap, previous: Array[Int], store: Array[Int], sameSince: Option[Int] = None): (Int, Int) = {
    column(x, pixmap, store)
    val same = previous.sameElements(store)
    val since = if (same && sameSince.nonEmpty) {
      sameSince
    } else if (same) {
      Some(x - 1)
    } else {
      None
    }
    if (x + 1 >= pixmap.getWidth) {
      if (sameSince.nonEmpty) {
        sameSince.get -> x
      } else {
        val center = math.round(pixmap.getWidth / 2.0).toInt
        (center - 1) -> (center + 1)
      }
    } else if (!same && sameSince.nonEmpty && x > (pixmap.getWidth / 2)) {
      sameSince.get -> (x - 1)
    } else {
      checkColumn(x + 1, pixmap, store, previous, since)
    }
  }

  private def column(x: Int, pixmap: Pixmap, store: Array[Int]): Unit = {
    store.indices.foreach { y =>
      store(y) = pixmap.getPixel(x, y)
    }
  }

  @tailrec
  private def checkRow(y: Int, pixmap: Pixmap, previous: Array[Int], store: Array[Int], sameSince: Option[Int] = None): (Int, Int) = {
    row(y, pixmap, store)
    val same = previous.sameElements(store)
    val since = if (same && sameSince.nonEmpty) {
      sameSince
    } else if (same) {
      Some(y - 1)
    } else {
      None
    }
    if (y + 1 >= pixmap.getHeight) {
      if (sameSince.nonEmpty) {
        sameSince.get -> y
      } else {
        val middle = math.round(pixmap.getHeight / 2.0).toInt
        (middle - 1) -> (middle + 1)
      }
    } else if (!same && sameSince.nonEmpty && y > (pixmap.getHeight / 2)) {
      sameSince.get -> (y - 1)
    } else {
      checkRow(y + 1, pixmap, store, previous, since)
    }
  }

  private def row(y: Int, pixmap: Pixmap, store: Array[Int]): Unit = {
    store.indices.foreach { x =>
      store(x) = pixmap.getPixel(x, y)
    }
  }
}