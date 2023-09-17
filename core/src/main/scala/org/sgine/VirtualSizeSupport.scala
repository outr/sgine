package org.sgine

import reactify._

trait VirtualSizeSupport {
  def screen: Screen

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

class VirtualSize(vss: VirtualSizeSupport) {
  private val _xOffset = Var(0.0)
  private val _yOffset = Var(0.0)
  private val _wMulti = Var(0.0)
  private val _hMulti = Var(0.0)
  val xOffset: Val[Double] = _xOffset
  val yOffset: Val[Double] = _yOffset
  val wMulti: Val[Double] = _wMulti
  val hMulti: Val[Double] = _hMulti

  private val updateFunction = (d: Double) => if (vss.screen.width.get > 0.0 && vss.screen.height.get > 0.0) {
    vss.virtualMode.get match {
      case VirtualMode.Bars | VirtualMode.Clip => {
        val widthRatio = vss.screen.width.get / vss.virtualWidth.get
        val heightRatio = vss.screen.height.get / vss.virtualHeight.get
        val ratio = vss.virtualMode.get match {
          case VirtualMode.Bars => math.min(widthRatio, heightRatio)
          case VirtualMode.Clip => math.max(widthRatio, heightRatio)
          case _ => 0.0   // Not possible
        }
        val w = vss.virtualWidth * ratio
        val h = vss.virtualHeight * ratio
        _xOffset := (vss.screen.width.get - w) / 2.0
        _yOffset := (vss.screen.height.get - h) / 2.0
        _wMulti := ratio
        _hMulti := ratio
      }
      case VirtualMode.Stretch => {
        _xOffset := 0.0
        _yOffset := 0.0
        _wMulti := vss.screen.width / vss.virtualWidth
        _hMulti := vss.screen.height / vss.virtualHeight
      }
    }
  }

  vss.screen.width.attachAndFire(updateFunction)
  vss.screen.height.attachAndFire(updateFunction)
  vss.virtualWidth.attach(updateFunction)
  vss.virtualHeight.attach(updateFunction)
  vss.virtualMode.on {
    updateFunction(0.0)
  }
}