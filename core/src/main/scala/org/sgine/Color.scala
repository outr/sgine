package org.sgine

/**
  * Color provides a simple wrapper around RGBA color information.
  */
case class Color(red: Double = 0.0, green: Double = 0.0, blue: Double = 0.0, alpha: Double = 0.0) {
  override def toString: String = s"Color(red = $red, green = $green, blue = $blue, alpha = $alpha)"
}

object Color {
  lazy val Clear = fromLong(0x00000000)

  lazy val AliceBlue = fromLong(0xF0F8FFFF)
  lazy val AntiqueWhite = fromLong(0xFAEBD7FF)
  lazy val Aqua = fromLong(0x00FFFFFF)
  lazy val Aquamarine = fromLong(0x7FFFD4FF)
  lazy val Azure = fromLong(0xF0FFFFFF)
  lazy val Beige = fromLong(0xF5F5DCFF)
  lazy val Bisque = fromLong(0xFFE4C4FF)
  lazy val Black = fromLong(0x000000FF)
  lazy val BlanchedAlmond = fromLong(0xFFEBCDFF)
  lazy val Blue = fromLong(0x0000FFFF)
  lazy val BlueViolet = fromLong(0x8A2BE2FF)
  lazy val Brown = fromLong(0xA52A2AFF)
  lazy val BurlyWood = fromLong(0xDEB887FF)
  lazy val CadetBlue = fromLong(0x5F9EA0FF)
  lazy val Chartreuse = fromLong(0x7FFF00FF)
  lazy val Chocolate = fromLong(0xD2691EFF)
  lazy val Coral = fromLong(0xFF7F50FF)
  lazy val CornflowerBlue = fromLong(0x6495EDFF)
  lazy val Cornsilk = fromLong(0xFFF8DCFF)
  lazy val Crimson = fromLong(0xDC143CFF)
  lazy val Cyan = fromLong(0x00FFFFFF)
  lazy val DarkBlue = fromLong(0x00008BFF)
  lazy val DarkCyan = fromLong(0x008B8BFF)
  lazy val DarkGoldenRod = fromLong(0xB8860BFF)
  lazy val DarkGray = fromLong(0xA9A9A9FF)
  lazy val DarkGreen = fromLong(0x006400FF)
  lazy val DarkKhaki = fromLong(0xBDB76BFF)
  lazy val DarkMagenta = fromLong(0x8B008BFF)
  lazy val DarkOliveGreen = fromLong(0x556B2FFF)
  lazy val DarkOrange = fromLong(0xFF8C00FF)
  lazy val DarkOrchid = fromLong(0x9932CCFF)
  lazy val DarkRed = fromLong(0x8B0000FF)
  lazy val DarkSalmon = fromLong(0xE9967AFF)
  lazy val DarkSeaGreen = fromLong(0x8FBC8FFF)
  lazy val DarkSlateBlue = fromLong(0x483D8BFF)
  lazy val DarkSlateGray = fromLong(0x2F4F4FFF)
  lazy val DarkTurquoise = fromLong(0x00CED1FF)
  lazy val DarkViolet = fromLong(0x9400D3FF)
  lazy val DeepPink = fromLong(0xFF1493FF)
  lazy val DeepSkyBlue = fromLong(0x00BFFFFF)
  lazy val DimGray = fromLong(0x696969FF)
  lazy val DodgerBlue = fromLong(0x1E90FFFF)
  lazy val FireBrick = fromLong(0xB22222FF)
  lazy val FloralWhite = fromLong(0xFFFAF0FF)
  lazy val ForestGreen = fromLong(0x228B22FF)
  lazy val Fuchsia = fromLong(0xFF00FFFF)
  lazy val Gainsboro = fromLong(0xDCDCDCFF)
  lazy val GhostWhite = fromLong(0xF8F8FFFF)
  lazy val Gold = fromLong(0xFFD700FF)
  lazy val GoldenRod = fromLong(0xDAA520FF)
  lazy val Gray = fromLong(0x808080FF)
  lazy val Green = fromLong(0x008000FF)
  lazy val GreenYellow = fromLong(0xADFF2FFF)
  lazy val HoneyDew = fromLong(0xF0FFF0FF)
  lazy val HotPink = fromLong(0xFF69B4FF)
  lazy val IndianRed = fromLong(0xCD5C5CFF)
  lazy val Indigo = fromLong(0x4B0082FF)
  lazy val Ivory = fromLong(0xFFFFF0FF)
  lazy val Khaki = fromLong(0xF0E68CFF)
  lazy val Lavender = fromLong(0xE6E6FAFF)
  lazy val LavenderBlush = fromLong(0xFFF0F5FF)
  lazy val LawnGreen = fromLong(0x7CFC00FF)
  lazy val LemonChiffon = fromLong(0xFFFACDFF)
  lazy val LightBlue = fromLong(0xADD8E6FF)
  lazy val LightCoral = fromLong(0xF08080FF)
  lazy val LightCyan = fromLong(0xE0FFFFFF)
  lazy val LightGoldenRodYellow = fromLong(0xFAFAD2FF)
  lazy val LightGray = fromLong(0xD3D3D3FF)
  lazy val LightGreen = fromLong(0x90EE90FF)
  lazy val LightPink = fromLong(0xFFB6C1FF)
  lazy val LightSalmon = fromLong(0xFFA07AFF)
  lazy val LightSeaGreen = fromLong(0x20B2AAFF)
  lazy val LightSkyBlue = fromLong(0x87CEFAFF)
  lazy val LightSlateGray = fromLong(0x778899FF)
  lazy val LightSteelBlue = fromLong(0xB0C4DEFF)
  lazy val LightYellow = fromLong(0xFFFFE0FF)
  lazy val Lime = fromLong(0x00FF00FF)
  lazy val LimeGreen = fromLong(0x32CD32FF)
  lazy val Linen = fromLong(0xFAF0E6FF)
  lazy val Magenta = fromLong(0xFF00FFFF)
  lazy val Maroon = fromLong(0x800000FF)
  lazy val MediumAquaMarine = fromLong(0x66CDAAFF)
  lazy val MediumBlue = fromLong(0x0000CDFF)
  lazy val MediumOrchid = fromLong(0xBA55D3FF)
  lazy val MediumPurple = fromLong(0x9370DBFF)
  lazy val MediumSeaGreen = fromLong(0x3CB371FF)
  lazy val MediumSlateBlue = fromLong(0x7B68EEFF)
  lazy val MediumSpringGreen = fromLong(0x00FA9AFF)
  lazy val MediumTurquoise = fromLong(0x48D1CCFF)
  lazy val MediumVioletRed = fromLong(0xC71585FF)
  lazy val MidnightBlue = fromLong(0x191970FF)
  lazy val MintCream = fromLong(0xF5FFFAFF)
  lazy val MistyRose = fromLong(0xFFE4E1FF)
  lazy val Moccasin = fromLong(0xFFE4B5FF)
  lazy val NavajoWhite = fromLong(0xFFDEADFF)
  lazy val Navy = fromLong(0x000080FF)
  lazy val OldLace = fromLong(0xFDF5E6FF)
  lazy val Olive = fromLong(0x808000FF)
  lazy val OliveDrab = fromLong(0x6B8E23FF)
  lazy val Orange = fromLong(0xFFA500FF)
  lazy val OrangeRed = fromLong(0xFF4500FF)
  lazy val Orchid = fromLong(0xDA70D6FF)
  lazy val PaleGoldenRod = fromLong(0xEEE8AAFF)
  lazy val PaleGreen = fromLong(0x98FB98FF)
  lazy val PaleTurquoise = fromLong(0xAFEEEEFF)
  lazy val PaleVioletRed = fromLong(0xDB7093FF)
  lazy val PapayaWhip = fromLong(0xFFEFD5FF)
  lazy val PeachPuff = fromLong(0xFFDAB9FF)
  lazy val Peru = fromLong(0xCD853FFF)
  lazy val Pink = fromLong(0xFFC0CBFF)
  lazy val Plum = fromLong(0xDDA0DDFF)
  lazy val PowderBlue = fromLong(0xB0E0E6FF)
  lazy val Purple = fromLong(0x800080FF)
  lazy val RebeccaPurple = fromLong(0x663399FF)
  lazy val Red = fromLong(0xFF0000FF)
  lazy val RosyBrown = fromLong(0xBC8F8FFF)
  lazy val RoyalBlue = fromLong(0x4169E1FF)
  lazy val SaddleBrown = fromLong(0x8B4513FF)
  lazy val Salmon = fromLong(0xFA8072FF)
  lazy val SandyBrown = fromLong(0xF4A460FF)
  lazy val SeaGreen = fromLong(0x2E8B57FF)
  lazy val SeaShell = fromLong(0xFFF5EEFF)
  lazy val Sienna = fromLong(0xA0522DFF)
  lazy val Silver = fromLong(0xC0C0C0FF)
  lazy val SkyBlue = fromLong(0x87CEEBFF)
  lazy val SlateBlue = fromLong(0x6A5ACDFF)
  lazy val SlateGray = fromLong(0x708090FF)
  lazy val Snow = fromLong(0xFFFAFAFF)
  lazy val SpringGreen = fromLong(0x00FF7FFF)
  lazy val SteelBlue = fromLong(0x4682B4FF)
  lazy val Tan = fromLong(0xD2B48CFF)
  lazy val Teal = fromLong(0x008080FF)
  lazy val Thistle = fromLong(0xD8BFD8FF)
  lazy val Tomato = fromLong(0xFF6347FF)
  lazy val Turquoise = fromLong(0x40E0D0FF)
  lazy val Violet = fromLong(0xEE82EEFF)
  lazy val Wheat = fromLong(0xF5DEB3FF)
  lazy val White = fromLong(0xFFFFFFFF)
  lazy val WhiteSmoke = fromLong(0xF5F5F5FF)
  lazy val Yellow = fromLong(0xFFFF00FF)
  lazy val YellowGreen = fromLong(0x9ACD32FF)

  /**
    * Creates a new Color instance from a Long value 0xRRGGBBAA
    *
    * @param value the numeric RGBA value
    */
  def fromLong(value: Long): Color = Color(
    red = (value >> 24 & 0xff) / 255.0,
    green = (value >> 16 & 0xff) / 255.0,
    blue = (value >> 8 & 0xff) / 255.0,
    alpha = (value >> 0 & 0xff) / 255.0
  )

  /**
    * Creates a new Color instance from a hex value. It is flexible for 3-digit and 6-digit with or without a leading
    * hash.
    *
    * @param hex String representation of a hex String
    */
  def fromHex(hex: String): Color = {
    if (hex.startsWith("#")) {
      fromHex(hex.substring(1))
    } else if (hex.length == 6) {
      fromHex(s"${hex}ff")
    } else if (hex.length == 3) {
      val r = hex.charAt(0)
      val g = hex.charAt(1)
      val b = hex.charAt(2)
      fromHex(s"$r$r$g$g$b${b}ff")
    } else {
      fromLong(java.lang.Long.parseLong(hex, 16))
    }
  }
}