package org.sgine.input

import com.badlogic.gdx.Gdx

class Key private(val code: Int, val lowerCase: Option[Char] = None, val upperCase: Option[Char] = None, val name: String, add: Boolean = true) {
  if (add) Key.add(this)

  def pressed: Boolean = Gdx.input.isKeyPressed(code)

  override def toString: String = s"Key($name)"
}

object Key {
  // TODO: better support shifted / caps lock keys

  private[input] def apply(code: Int,
                           lowerCase: Option[Char] = None,
                           upperCase: Option[Char] = None,
                           name: String,
                           add: Boolean = true): Key = {
    new Key(code, lowerCase, upperCase.orElse(lowerCase.map(_.toUpper)), name, add)
  }

  private var codes = Map.empty[Int, Key]
  private var chars = Map.empty[Char, Key]
  private def add(key: Key): Unit = {
    codes += key.code -> key
    key.lowerCase.foreach(chars += _ -> key)
    key.upperCase.foreach(chars += _ -> key)
  }
  
  import com.badlogic.gdx.Input.{Keys => k}

  val Zero = Key(k.NUM_0, Some('0'), Some(')'), name = "Zero")
  val One = Key(k.NUM_1, Some('1'), Some('!'), name = "One")
  val Two = Key(k.NUM_2, Some('2'), name = "Two")
  val Three = Key(k.NUM_3, Some('3'), name = "Three")
  val Four = Key(k.NUM_4, Some('4'), Some('$'), name = "Four")
  val Five = Key(k.NUM_5, Some('5'), Some('%'), name = "Five")
  val Six = Key(k.NUM_6, Some('6'), name = "Six")
  val Seven = Key(k.NUM_7, Some('7'), Some('&'), name = "Seven")
  val Eight = Key(k.NUM_8, Some('8'), name = "Eight")
  val Nine = Key(k.NUM_9, Some('9'), Some('('), name = "Nine")

  val Up = Key(k.UP, name = "Up")
  val Left = Key(k.LEFT, name = "Left")
  val Down = Key(k.DOWN, name = "Down")
  val Right = Key(k.RIGHT, name = "Right")
  val Center = Key(k.CENTER, name = "Center")

  val A = Key(k.A, Some('a'), name = "A")
  val B = Key(k.B, Some('b'), name = "B")
  val C = Key(k.C, Some('c'), name = "C")
  val D = Key(k.D, Some('d'), name = "D")
  val E = Key(k.E, Some('e'), name = "E")
  val F = Key(k.F, Some('f'), name = "F")
  val G = Key(k.G, Some('g'), name = "G")
  val H = Key(k.H, Some('h'), name = "H")
  val I = Key(k.I, Some('i'), name = "I")
  val J = Key(k.J, Some('j'), name = "J")
  val K = Key(k.K, Some('k'), name = "K")
  val L = Key(k.L, Some('l'), name = "L")
  val M = Key(k.M, Some('m'), name = "M")
  val N = Key(k.N, Some('n'), name = "N")
  val O = Key(k.O, Some('o'), name = "O")
  val P = Key(k.P, Some('p'), name = "P")
  val Q = Key(k.Q, Some('q'), name = "Q")
  val R = Key(k.R, Some('r'), name = "R")
  val S = Key(k.S, Some('s'), name = "S")
  val T = Key(k.T, Some('t'), name = "T")
  val U = Key(k.U, Some('u'), name = "U")
  val V = Key(k.V, Some('v'), name = "V")
  val W = Key(k.W, Some('w'), name = "W")
  val X = Key(k.X, Some('x'), name = "X")
  val Y = Key(k.Y, Some('y'), name = "Y")
  val Z = Key(k.Z, Some('z'), name = "Z")

  val AltLeft = Key(k.ALT_LEFT, name = "AltLeft")
  val AltRight = Key(k.ALT_RIGHT, name = "AltRight")
  val AngleLeft = Key(-1, None, Some('<'), name = "AngleLeft")
  val AngleRight = Key(-1, None, Some('>'), name = "AngleRight")
  val Apostrophe = Key(k.APOSTROPHE, Some('\''), name = "Apostrophe")
  val Asterisk = Key(k.STAR, Some('*'), name = "Asterisk")
  val At = Key(k.AT, Some('@'), name = "At")
  val Backslash = Key(k.BACKSLASH, Some('\\'), name = "Backslash")
  val BracketLeft = Key(k.LEFT_BRACKET, Some('['), Some('{'), name = "LeftBracket")
  val BracketRight = Key(k.RIGHT_BRACKET, Some(']'), Some('}'), name = "RightBracket")
  val Colon = Key(k.COLON, Some(':'), name = "Colon")
  val Comma = Key(k.COMMA, Some(','), name = "Comma")
  val Equals = Key(k.EQUALS, Some('='), name = "Equals")
  val ForwardSlash = Key(k.SLASH, Some('/'), name = "ForwardSlash")
  val Grave = Key(k.GRAVE, Some('`'), Some('~'), name = "Grave")
  val Minus = Key(k.MINUS, Some('-'), name = "Minus")
  val Period = Key(k.PERIOD, Some('.'), name = "Period")
  val Pipe = Key(-1, None, Some('|'), name = "Pipe")
  val Plus = Key(k.PLUS, Some('+'), name = "Plus")
  val Pound = Key(k.POUND, Some('#'), name = "Pound")
  val Power = Key(k.POWER, Some('^'), name = "Power")
  val Question = Key(-1, None, Some('?'), name = "Question")
  val Quote = Key(-1, None, Some('"'), name = "Quote")
  val SemiColon = Key(k.SEMICOLON, Some(';'), name = "SemiColon")
  val Space = Key(k.SPACE, Some(' '), name = "Space")
  val Underscore = Key(-1, None, Some('_'), name = "Underscore")

  val Back = Key(k.BACK, name = "Back")
  val Backspace = Key(k.BACKSPACE, Some(8.toChar), name = "Backspace")
  val Call = Key(k.CALL, name = "Call")
  val Camera = Key(k.CAMERA, name = "Camera")
  val Clear = Key(k.CLEAR, name = "Clear")
  val Dash = Key(-1, lowerCase = None, upperCase = Some('-'), name = "Dash")
  val EndCall = Key(k.ENDCALL, name = "EndCall")
  val Enter = Key(k.ENTER, Some('\r'), name = "Enter")
  val Envelope = Key(k.ENVELOPE, name = "Envelope")
  val Escape = Key(k.ESCAPE, name = "Escape")
  val Explorer = Key(k.EXPLORER, name = "Explorer")
  val Focus = Key(k.FOCUS, name = "Focus")
  val ForwardDel = Key(k.FORWARD_DEL, Some(127.toChar), name = "ForwardDel")
  val HeadsetHook = Key(k.HEADSETHOOK, name = "HeadsetHook")
  val Home = Key(k.HOME, name = "Home")
  val MediaFastForward = Key(k.MEDIA_FAST_FORWARD, name = "MediaFastForward")
  val MediaNext = Key(k.MEDIA_NEXT, name = "MediaNext")
  val MediaPlayPause = Key(k.MEDIA_PLAY_PAUSE, name = "MediaPlayPause")
  val MediaPrevious = Key(k.MEDIA_PREVIOUS, name = "MediaPrevious")
  val MediaRewind = Key(k.MEDIA_REWIND, name = "MediaRewind")
  val MediaStop = Key(k.MEDIA_STOP, name = "MediaStop")
  val Menu = Key(k.MENU, name = "Menu")
  val Mute = Key(k.MUTE, name = "Mute")
  val Notification = Key(k.NOTIFICATION, name = "Notification")
  val Num = Key(k.NUM, name = "Num")
  val PageUp = Key(k.PAGE_UP, name = "PageUp")
  val PageDown = Key(k.PAGE_DOWN, name = "PageDown")
  val Search = Key(k.SEARCH, name = "Search")
  val ShiftLeft = Key(k.SHIFT_LEFT, name = "ShiftLeft")
  val ShiftRight = Key(k.SHIFT_RIGHT, name = "ShiftRight")
  val SoftLeft = Key(k.SOFT_LEFT, name = "SoftLeft")
  val SoftRight = Key(k.SOFT_RIGHT, name = "SoftRight")
  val Sym = Key(k.SYM, name = "Sym")
  val Tab = Key(k.TAB, Some('\t'), name = "Tab")
  val VolumeDown = Key(k.VOLUME_DOWN, name = "VolumeDown")
  val VolumeUp = Key(k.VOLUME_UP, name = "VolumeUp")
  val ControlLeft = Key(k.CONTROL_LEFT, name = "ControlLeft")
  val ControlRight = Key(k.CONTROL_RIGHT, name = "ControlRight")
  val End = Key(k.END, name = "End")
  val Insert = Key(k.INSERT, name = "Insert")
  val PictSymbols = Key(k.PICTSYMBOLS, name = "PictSymbols")
  val SwitchCharset = Key(k.SWITCH_CHARSET, name = "SwitchCharset")
  val Button = new Buttons
  val NumPad = new NumPadKeys

  val F1 = Key(k.F1, name = "F1")
  val F2 = Key(k.F2, name = "F2")
  val F3 = Key(k.F3, name = "F3")
  val F4 = Key(k.F4, name = "F4")
  val F5 = Key(k.F5, name = "F5")
  val F6 = Key(k.F6, name = "F6")
  val F7 = Key(k.F7, name = "F7")
  val F8 = Key(k.F8, name = "F8")
  val F9 = Key(k.F9, name = "F9")
  val F10 = Key(k.F10, name = "F10")
  val F11 = Key(k.F11, name = "F11")
  val F12 = Key(k.F12, name = "F12")

  // Special keys
  val Play = Key(-1, add = false, name = "Play")
  val Pause = Key(-1, add = false, name = "Pause")
  val Next = Key(-1, add = false, name = "Next")
  val Previous = Key(-1, add = false, name = "Previous")
  val Stop = Key(-1, add = false, name = "Stop")

  val Unknown = Key(k.UNKNOWN, name = "Unknown")

  def byCode(code: Int) = codes.get(code)
  def byChar(char: Char) = chars.get(char)
}

class Buttons private[input]() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Circle = Key(k.BUTTON_CIRCLE, add = false, name = "Circle")
  val A = Key(k.BUTTON_A, name = "A")
  val B = Key(k.BUTTON_B, name = "B")
  val C = Key(k.BUTTON_C, name = "C")
  val X = Key(k.BUTTON_X, name = "X")
  val Y = Key(k.BUTTON_Y, name = "Y")
  val Z = Key(k.BUTTON_Z, name = "Z")
  val L1 = Key(k.BUTTON_L1, name = "L1")
  val R1 = Key(k.BUTTON_R1, name = "R1")
  val L2 = Key(k.BUTTON_L2, name = "L2")
  val R2 = Key(k.BUTTON_R2, name = "R2")
  val ThumbLeft = Key(k.BUTTON_THUMBL, name = "ThumbLeft")
  val ThumbRight = Key(k.BUTTON_THUMBR, name = "ThumbRight")
  val Start = Key(k.BUTTON_START, name = "Start")
  val Select = Key(k.BUTTON_SELECT, name = "Select")
  val Mode = Key(k.BUTTON_MODE, name = "Mode")
}

class NumPadKeys private[input]() {
  import com.badlogic.gdx.Input.{Keys => k}

  val Zero = Key(k.NUMPAD_0, name = "Zero")
  val One = Key(k.NUMPAD_1, name = "One")
  val Two = Key(k.NUMPAD_2, name = "Two")
  val Three = Key(k.NUMPAD_3, name = "Three")
  val Four = Key(k.NUMPAD_4, name = "Four")
  val Five = Key(k.NUMPAD_5, name = "Five")
  val Six = Key(k.NUMPAD_6, name = "Six")
  val Seven = Key(k.NUMPAD_7, name = "Seven")
  val Eight = Key(k.NUMPAD_8, name = "Eight")
  val Nine = Key(k.NUMPAD_9, name = "Nine")
}