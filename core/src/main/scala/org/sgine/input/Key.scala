package org.sgine.input

class Key(val code: Int, val lowerCase: Option[Char] = None, add: Boolean = true) {
  lazy val upperCase: Option[Char] = lowerCase.map(_.toUpper)

  if (add) Key.add(this)
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

  val Zero = new Key(k.NUM_0, Some('0'))
  val One = new Key(k.NUM_1, Some('1'))
  val Two = new Key(k.NUM_2, Some('2'))
  val Three = new Key(k.NUM_3, Some('3'))
  val Four = new Key(k.NUM_4, Some('4'))
  val Five = new Key(k.NUM_5, Some('5'))
  val Six = new Key(k.NUM_6, Some('6'))
  val Seven = new Key(k.NUM_7, Some('7'))
  val Eight = new Key(k.NUM_8, Some('8'))
  val Nine = new Key(k.NUM_9, Some('9'))

  val Up = new Key(k.UP)
  val Left = new Key(k.LEFT)
  val Down = new Key(k.DOWN)
  val Right = new Key(k.RIGHT)
  val Center = new Key(k.CENTER)

  val A = new Key(k.A, Some('a'))
  val B = new Key(k.B, Some('b'))
  val C = new Key(k.C, Some('c'))
  val D = new Key(k.D, Some('d'))
  val E = new Key(k.E, Some('e'))
  val F = new Key(k.F, Some('f'))
  val G = new Key(k.G, Some('g'))
  val H = new Key(k.H, Some('h'))
  val I = new Key(k.I, Some('i'))
  val J = new Key(k.J, Some('j'))
  val K = new Key(k.K, Some('k'))
  val L = new Key(k.L, Some('l'))
  val M = new Key(k.M, Some('m'))
  val N = new Key(k.N, Some('n'))
  val O = new Key(k.O, Some('o'))
  val P = new Key(k.P, Some('p'))
  val Q = new Key(k.Q, Some('q'))
  val R = new Key(k.R, Some('r'))
  val S = new Key(k.S, Some('s'))
  val T = new Key(k.T, Some('t'))
  val U = new Key(k.U, Some('u'))
  val V = new Key(k.V, Some('v'))
  val W = new Key(k.W, Some('w'))
  val X = new Key(k.X, Some('x'))
  val Y = new Key(k.Y, Some('y'))
  val Z = new Key(k.Z, Some('z'))

  val Apostrophe = new Key(k.APOSTROPHE, Some('\''))
  val Asterisk = new Key(k.STAR, Some('*'))
  val At = new Key(k.AT, Some('@'))
  val Backslash = new Key(k.BACKSLASH, Some('\\'))
  val Colon = new Key(k.COLON, Some(':'))
  val Comma = new Key(k.COMMA, Some(','))
  val Enter = new Key(k.ENTER, Some('\n'))
  val Equals = new Key(k.EQUALS, Some('='))
  val ForwardSlash = new Key(k.SLASH, Some('/'))
  val Grave = new Key(k.GRAVE, Some('`'))
  val LeftBracket = new Key(k.LEFT_BRACKET, Some('['))
  val Minus = new Key(k.MINUS, Some('-'))
  val Period = new Key(k.PERIOD, Some('.'))
  val Plus = new Key(k.PLUS, Some('+'))
  val Pound = new Key(k.POUND, Some('#'))
  val Power = new Key(k.POWER, Some('^'))
  val RightBracket = new Key(k.RIGHT_BRACKET, Some(']'))
  val SemiColon = new Key(k.SEMICOLON, Some(';'))
  val Space = new Key(k.SPACE, Some(' '))
  val Tab = new Key(k.TAB, Some('\t'))

  val AltLeft = new Key(k.ALT_LEFT)
  val AltRight = new Key(k.ALT_RIGHT)
  val Back = new Key(k.BACK)
  val Backspace = new Key(k.BACKSPACE)
  val Call = new Key(k.CALL)
  val Camera = new Key(k.CAMERA)
  val Clear = new Key(k.CLEAR)
  val Del = new Key(k.DEL)
  val EndCall = new Key(k.ENDCALL)
  val Envelope = new Key(k.ENVELOPE)
  val Escape = new Key(k.ESCAPE)
  val Explorer = new Key(k.EXPLORER)
  val Focus = new Key(k.FOCUS)
  val ForwardDel = new Key(k.FORWARD_DEL)
  val HeadsetHook = new Key(k.HEADSETHOOK)
  val Home = new Key(k.HOME)
  val MediaFastForward = new Key(k.MEDIA_FAST_FORWARD)
  val MediaNext = new Key(k.MEDIA_NEXT)
  val MediaPlayPause = new Key(k.MEDIA_PLAY_PAUSE)
  val MediaPrevious = new Key(k.MEDIA_PREVIOUS)
  val MediaRewind = new Key(k.MEDIA_REWIND)
  val MediaStop = new Key(k.MEDIA_STOP)
  val Menu = new Key(k.MENU)
  val Mute = new Key(k.MUTE)
  val Notification = new Key(k.NOTIFICATION)
  val Num = new Key(k.NUM)
  val PageUp = new Key(k.PAGE_UP)
  val PageDown = new Key(k.PAGE_DOWN)
  val Search = new Key(k.SEARCH)
  val ShiftLeft = new Key(k.SHIFT_LEFT)
  val ShiftRight = new Key(k.SHIFT_RIGHT)
  val SoftLeft = new Key(k.SOFT_LEFT)
  val SoftRight = new Key(k.SOFT_RIGHT)
  val Sym = new Key(k.SYM)
  val Unknown = new Key(k.UNKNOWN)
  val VolumeDown = new Key(k.VOLUME_DOWN)
  val VolumeUp = new Key(k.VOLUME_UP)
  val ControlLeft = new Key(k.CONTROL_LEFT)
  val ControlRight = new Key(k.CONTROL_RIGHT)
  val End = new Key(k.END)
  val Insert = new Key(k.INSERT)
  val PictSymbols = new Key(k.PICTSYMBOLS)
  val SwitchCharset = new Key(k.SWITCH_CHARSET)
  val Button = new Buttons
  val NumPad = new NumPadKeys

  val F1 = new Key(k.F1)
  val F2 = new Key(k.F2)
  val F3 = new Key(k.F3)
  val F4 = new Key(k.F4)
  val F5 = new Key(k.F5)
  val F6 = new Key(k.F6)
  val F7 = new Key(k.F7)
  val F8 = new Key(k.F8)
  val F9 = new Key(k.F9)
  val F10 = new Key(k.F10)
  val F11 = new Key(k.F11)
  val F12 = new Key(k.F12)

  // Special keys
  val Play = new Key(-1)
  val Pause = new Key(-1)
  val Next = new Key(-1)
  val Previous = new Key(-1)
  val Stop = new Key(-1)

  def byCode(code: Int) = codes.get(code)
  def byChar(char: Char) = chars.get(char)
}

class Buttons private() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Circle = new Key(k.BUTTON_CIRCLE)
  val A = new Key(k.BUTTON_A)
  val B = new Key(k.BUTTON_B)
  val C = new Key(k.BUTTON_C)
  val X = new Key(k.BUTTON_X)
  val Y = new Key(k.BUTTON_Y)
  val Z = new Key(k.BUTTON_Z)
  val L1 = new Key(k.BUTTON_L1)
  val R1 = new Key(k.BUTTON_R1)
  val L2 = new Key(k.BUTTON_L2)
  val R2 = new Key(k.BUTTON_R2)
  val ThumbLeft = new Key(k.BUTTON_THUMBL)
  val ThumbRight = new Key(k.BUTTON_THUMBR)
  val Start = new Key(k.BUTTON_START)
  val Select = new Key(k.BUTTON_SELECT)
  val Mode = new Key(k.BUTTON_MODE)
}

class NumPadKeys private() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Zero = new Key(k.NUM_0)
  val One = new Key(k.NUM_1)
  val Two = new Key(k.NUM_2)
  val Three = new Key(k.NUM_3)
  val Four = new Key(k.NUM_4)
  val Five = new Key(k.NUM_5)
  val Six = new Key(k.NUM_6)
  val Seven = new Key(k.NUM_7)
  val Eight = new Key(k.NUM_8)
  val Nine = new Key(k.NUM_9)
}