package org.sgine.input

class Key(val code: Int, val lowerCase: Option[Char] = None, val name: String, add: Boolean = true) {
  lazy val upperCase: Option[Char] = lowerCase.map(_.toUpper)

  if (add) Key.add(this)

  override def toString: String = s"Key($name)"
}

object Key {
  private var codes = Map.empty[Int, Key]
  private var chars = Map.empty[Char, Key]
  private def add(key: Key): Unit = {
    codes += key.code -> key
    key.lowerCase match {
      case Some(c) => {
        chars += c -> key
        chars += c.toUpper -> key
      }
      case None => // No character
    }
  }
  
  import com.badlogic.gdx.Input.{Keys => k}

  val Zero = new Key(k.NUM_0, Some('0'), name = "Zero")
  val One = new Key(k.NUM_1, Some('1'), name = "One")
  val Two = new Key(k.NUM_2, Some('2'), name = "Two")
  val Three = new Key(k.NUM_3, Some('3'), name = "Three")
  val Four = new Key(k.NUM_4, Some('4'), name = "Four")
  val Five = new Key(k.NUM_5, Some('5'), name = "Five")
  val Six = new Key(k.NUM_6, Some('6'), name = "Six")
  val Seven = new Key(k.NUM_7, Some('7'), name = "Seven")
  val Eight = new Key(k.NUM_8, Some('8'), name = "Eight")
  val Nine = new Key(k.NUM_9, Some('9'), name = "Nine")

  val Up = new Key(k.UP, name = "Up")
  val Left = new Key(k.LEFT, name = "Left")
  val Down = new Key(k.DOWN, name = "Down")
  val Right = new Key(k.RIGHT, name = "Right")
  val Center = new Key(k.CENTER, name = "Center")

  val A = new Key(k.A, Some('a'), name = "A")
  val B = new Key(k.B, Some('b'), name = "B")
  val C = new Key(k.C, Some('c'), name = "C")
  val D = new Key(k.D, Some('d'), name = "D")
  val E = new Key(k.E, Some('e'), name = "E")
  val F = new Key(k.F, Some('f'), name = "F")
  val G = new Key(k.G, Some('g'), name = "G")
  val H = new Key(k.H, Some('h'), name = "H")
  val I = new Key(k.I, Some('i'), name = "I")
  val J = new Key(k.J, Some('j'), name = "J")
  val K = new Key(k.K, Some('k'), name = "K")
  val L = new Key(k.L, Some('l'), name = "L")
  val M = new Key(k.M, Some('m'), name = "M")
  val N = new Key(k.N, Some('n'), name = "N")
  val O = new Key(k.O, Some('o'), name = "O")
  val P = new Key(k.P, Some('p'), name = "P")
  val Q = new Key(k.Q, Some('q'), name = "Q")
  val R = new Key(k.R, Some('r'), name = "R")
  val S = new Key(k.S, Some('s'), name = "S")
  val T = new Key(k.T, Some('t'), name = "T")
  val U = new Key(k.U, Some('u'), name = "U")
  val V = new Key(k.V, Some('v'), name = "V")
  val W = new Key(k.W, Some('w'), name = "W")
  val X = new Key(k.X, Some('x'), name = "X")
  val Y = new Key(k.Y, Some('y'), name = "Y")
  val Z = new Key(k.Z, Some('z'), name = "Z")

  val Apostrophe = new Key(k.APOSTROPHE, Some('\''), name = "Apostrophe")
  val Asterisk = new Key(k.STAR, Some('*'), name = "Asterisk")
  val At = new Key(k.AT, Some('@'), name = "At")
  val Backslash = new Key(k.BACKSLASH, Some('\\'), name = "Backslash")
  val Colon = new Key(k.COLON, Some(':'), name = "Colon")
  val Comma = new Key(k.COMMA, Some(','), name = "Comma")
  val Enter = new Key(k.ENTER, Some('\n'), name = "Enter")
  val Equals = new Key(k.EQUALS, Some('='), name = "Equals")
  val ForwardSlash = new Key(k.SLASH, Some('/'), name = "ForwardSlash")
  val Grave = new Key(k.GRAVE, Some('`'), name = "Grave")
  val LeftBracket = new Key(k.LEFT_BRACKET, Some('['), name = "LeftBracket")
  val Minus = new Key(k.MINUS, Some('-'), name = "Minus")
  val Period = new Key(k.PERIOD, Some('.'), name = "Period")
  val Plus = new Key(k.PLUS, Some('+'), name = "Plus")
  val Pound = new Key(k.POUND, Some('#'), name = "Pound")
  val Power = new Key(k.POWER, Some('^'), name = "Power")
  val RightBracket = new Key(k.RIGHT_BRACKET, Some(']'), name = "RightBracket")
  val SemiColon = new Key(k.SEMICOLON, Some(';'), name = "SemiColon")
  val Space = new Key(k.SPACE, Some(' '), name = "Space")
  val Tab = new Key(k.TAB, Some('\t'), name = "Tab")

  val AltLeft = new Key(k.ALT_LEFT, name = "AltLeft")
  val AltRight = new Key(k.ALT_RIGHT, name = "AltRight")
  val Back = new Key(k.BACK, name = "Back")
  val Backspace = new Key(k.BACKSPACE, name = "Backspace")
  val Call = new Key(k.CALL, name = "Call")
  val Camera = new Key(k.CAMERA, name = "Camera")
  val Clear = new Key(k.CLEAR, name = "Clear")
  val EndCall = new Key(k.ENDCALL, name = "EndCall")
  val Envelope = new Key(k.ENVELOPE, name = "Envelope")
  val Escape = new Key(k.ESCAPE, name = "Escape")
  val Explorer = new Key(k.EXPLORER, name = "Explorer")
  val Focus = new Key(k.FOCUS, name = "Focus")
  val ForwardDel = new Key(k.FORWARD_DEL, name = "ForwardDel")
  val HeadsetHook = new Key(k.HEADSETHOOK, name = "HeadsetHook")
  val Home = new Key(k.HOME, name = "Home")
  val MediaFastForward = new Key(k.MEDIA_FAST_FORWARD, name = "MediaFastForward")
  val MediaNext = new Key(k.MEDIA_NEXT, name = "MediaNext")
  val MediaPlayPause = new Key(k.MEDIA_PLAY_PAUSE, name = "MediaPlayPause")
  val MediaPrevious = new Key(k.MEDIA_PREVIOUS, name = "MediaPrevious")
  val MediaRewind = new Key(k.MEDIA_REWIND, name = "MediaRewind")
  val MediaStop = new Key(k.MEDIA_STOP, name = "MediaStop")
  val Menu = new Key(k.MENU, name = "Menu")
  val Mute = new Key(k.MUTE, name = "Mute")
  val Notification = new Key(k.NOTIFICATION, name = "Notification")
  val Num = new Key(k.NUM, name = "Num")
  val PageUp = new Key(k.PAGE_UP, name = "PageUp")
  val PageDown = new Key(k.PAGE_DOWN, name = "PageDown")
  val Search = new Key(k.SEARCH, name = "Search")
  val ShiftLeft = new Key(k.SHIFT_LEFT, name = "ShiftLeft")
  val ShiftRight = new Key(k.SHIFT_RIGHT, name = "ShiftRight")
  val SoftLeft = new Key(k.SOFT_LEFT, name = "SoftLeft")
  val SoftRight = new Key(k.SOFT_RIGHT, name = "SoftRight")
  val Sym = new Key(k.SYM, name = "Sym")
  val Unknown = new Key(k.UNKNOWN, name = "Unknown")
  val VolumeDown = new Key(k.VOLUME_DOWN, name = "VolumeDown")
  val VolumeUp = new Key(k.VOLUME_UP, name = "VolumeUp")
  val ControlLeft = new Key(k.CONTROL_LEFT, name = "ControlLeft")
  val ControlRight = new Key(k.CONTROL_RIGHT, name = "ControlRight")
  val End = new Key(k.END, name = "End")
  val Insert = new Key(k.INSERT, name = "Insert")
  val PictSymbols = new Key(k.PICTSYMBOLS, name = "PictSymbols")
  val SwitchCharset = new Key(k.SWITCH_CHARSET, name = "SwitchCharset")
  val Button = new Buttons
  val NumPad = new NumPadKeys

  val F1 = new Key(k.F1, name = "F1")
  val F2 = new Key(k.F2, name = "F2")
  val F3 = new Key(k.F3, name = "F3")
  val F4 = new Key(k.F4, name = "F4")
  val F5 = new Key(k.F5, name = "F5")
  val F6 = new Key(k.F6, name = "F6")
  val F7 = new Key(k.F7, name = "F7")
  val F8 = new Key(k.F8, name = "F8")
  val F9 = new Key(k.F9, name = "F9")
  val F10 = new Key(k.F10, name = "F10")
  val F11 = new Key(k.F11, name = "F11")
  val F12 = new Key(k.F12, name = "F12")

  // Special keys
  val Play = new Key(-1, add = false, name = "Play")
  val Pause = new Key(-1, add = false, name = "Pause")
  val Next = new Key(-1, add = false, name = "Next")
  val Previous = new Key(-1, add = false, name = "Previous")
  val Stop = new Key(-1, add = false, name = "Stop")

  def byCode(code: Int) = codes.get(code)
  def byChar(char: Char) = chars.get(char)
}

class Buttons private[input]() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Circle = new Key(k.BUTTON_CIRCLE, add = false, name = "Circle")
  val A = new Key(k.BUTTON_A, name = "A")
  val B = new Key(k.BUTTON_B, name = "B")
  val C = new Key(k.BUTTON_C, name = "C")
  val X = new Key(k.BUTTON_X, name = "X")
  val Y = new Key(k.BUTTON_Y, name = "Y")
  val Z = new Key(k.BUTTON_Z, name = "Z")
  val L1 = new Key(k.BUTTON_L1, name = "L1")
  val R1 = new Key(k.BUTTON_R1, name = "R1")
  val L2 = new Key(k.BUTTON_L2, name = "L2")
  val R2 = new Key(k.BUTTON_R2, name = "R2")
  val ThumbLeft = new Key(k.BUTTON_THUMBL, name = "ThumbLeft")
  val ThumbRight = new Key(k.BUTTON_THUMBR, name = "ThumbRight")
  val Start = new Key(k.BUTTON_START, name = "Start")
  val Select = new Key(k.BUTTON_SELECT, name = "Select")
  val Mode = new Key(k.BUTTON_MODE, name = "Mode")
}

class NumPadKeys private[input]() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Zero = new Key(k.NUMPAD_0, name = "Zero")
  val One = new Key(k.NUMPAD_1, name = "One")
  val Two = new Key(k.NUMPAD_2, name = "Two")
  val Three = new Key(k.NUMPAD_3, name = "Three")
  val Four = new Key(k.NUMPAD_4, name = "Four")
  val Five = new Key(k.NUMPAD_5, name = "Five")
  val Six = new Key(k.NUMPAD_6, name = "Six")
  val Seven = new Key(k.NUMPAD_7, name = "Seven")
  val Eight = new Key(k.NUMPAD_8, name = "Eight")
  val Nine = new Key(k.NUMPAD_9, name = "Nine")
}