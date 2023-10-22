package com.lapoule.lasagna.parser

import fastparse.*
import fastparse.NoWhitespace.*

object SqlValues {
  import com.lapoule.lasagna.sql.Model._
 

  def minus[$: P]: P[Minus.type] = {
    P("-".!).map(str => Minus)
  }

  def plus[$: P]: P[Plus.type] = {
    P("+".!).map(str => Plus)
  }

  def sign[$: P]: P[Sign] = P(minus | plus).map(sign => sign: Sign)

  def letter[$: P]: P[String] = P(CharIn("a-zA-Z").!)

  def number[$: P]: P[String] = P(CharIn("0-9").!)

  def escapedQuote[$: P]: P[String] = P("\\\"".!)

  def notAlphanumericOrQuote[$: P]: P[Unit] = P(CharPred(!"\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".contains(_)))

  def stringValue[$: P]: P[StringValue] = P("\"" ~ (letter | number | escapedQuote | notAlphanumericOrQuote).rep(0).! ~ "\"").map((str: String) => StringValue(str))

  def intValue[$: P]: P[IntValue] = P(CharIn("0-9").rep(1).!).map(str => IntValue(str.toInt))

  def exponent[$: P] = P("E")

  def exponential[$: P] = {
    P(sign ~ intValue ~ exponent ~ sign ~ intValue).
      map { case (mantisSign: Sign, mantisInt, sign, int) => FloatValue(s"$mantisSign${mantisInt}E$sign$int".toFloat) }
  }

  def standard[$: P] = {
    P(intValue ~ "." ~ intValue).
      map { case (fullPart, fractionalPart) => FloatValue(s"$fullPart.${fractionalPart}".toFloat) }
  }

  def floatValue[$: P]: P[FloatValue] = P(exponential | standard)

  def value[$: P]: P[Value] = P(floatValue | intValue | stringValue)
}
