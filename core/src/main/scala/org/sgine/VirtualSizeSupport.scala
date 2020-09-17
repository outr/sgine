package org.sgine

import reactify._

trait VirtualSizeSupport {
  val virtualWidth: Var[Double] = Var[Double](1024.0)
  val virtualHeight: Var[Double] = Var[Double](768.0)
  val virtualMode: Var[VirtualMode] = Var[VirtualMode](VirtualMode.Bars)

  lazy val virtual = new VirtualSize(this)

  implicit class IntVirtualPixels(i: Int) {
    def vx: Val[Double] = Val(vw + virtual.xOffset)
    def vy: Val[Double] = Val(vh + virtual.yOffset)
    def vw: Val[Double] = Val(i.toDouble * virtual.wMulti)
    def vh: Val[Double] = Val(i.toDouble * virtual.hMulti)
    def vf: Val[Int] = Val(math.round(i.toDouble * virtual.wMulti).toInt)
  }
  implicit class DoubleVirtualPixels(d: Double) {
    def vx: Val[Double] = Val(vw + virtual.xOffset)
    def vy: Val[Double] = Val(vh + virtual.yOffset)
    def vw: Val[Double] = Val(d * virtual.wMulti)
    def vh: Val[Double] = Val(d * virtual.hMulti)
    def vf: Val[Int] = Val(math.round(d * virtual.wMulti).toInt)
  }
}

sealed trait VirtualMode

object VirtualMode {
  case object Bars extends VirtualMode
  case object Clip extends VirtualMode
  case object Stretch extends VirtualMode
}

class VirtualSize(screen: VirtualSizeSupport) {
  private val _xOffset = Var(0.0)
  private val _yOffset = Var(0.0)
  private val _wMulti = Var(0.0)
  private val _hMulti = Var(0.0)
  val xOffset: Val[Double] = _xOffset
  val yOffset: Val[Double] = _yOffset
  val wMulti: Val[Double] = _wMulti
  val hMulti: Val[Double] = _hMulti

  private val updateFunction = (d: Double) => if (ui.width.get > 0.0 && ui.height.get > 0.0) {
    screen.virtualMode.get match {
      case VirtualMode.Bars | VirtualMode.Clip => {
        val widthRatio = ui.width.get / screen.virtualWidth.get
        val heightRatio = ui.height.get / screen.virtualHeight.get
        val ratio = screen.virtualMode.get match {
          case VirtualMode.Bars => math.min(widthRatio, heightRatio)
          case VirtualMode.Clip => math.max(widthRatio, heightRatio)
          case _ => 0.0   // Not possible
        }
        val w = screen.virtualWidth * ratio
        val h = screen.virtualHeight * ratio
        _xOffset := (ui.width.get - w) / 2.0
        _yOffset := (ui.height.get - h) / 2.0
        _wMulti := ratio
        _hMulti := ratio
      }
      case VirtualMode.Stretch => {
        _xOffset := 0.0
        _yOffset := 0.0
        _wMulti := ui.width / screen.virtualWidth
        _hMulti := ui.height / screen.virtualHeight
      }
    }
  }

  ui.width.attach(updateFunction)
  ui.height.attach(updateFunction)
  screen.virtualWidth.attach(updateFunction)
  screen.virtualHeight.attach(updateFunction)
  screen.virtualMode.attach { mode =>
    updateFunction(0.0)
  }
}