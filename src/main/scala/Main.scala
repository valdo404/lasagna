package com.lapoule.fastparse.sql

import fastparse.*
import NoWhitespace.*
object Main {

  import SelectSqlParser._

  def main(args: Array[String]): Unit = {
    val Parsed.Success(result, _: Int) = parse("SELECT A.a AS truc, b,c,  d FROM table AS A WHERE a AND \"dude4\"=b OR c=3", selectDml)
    println(result)

    val Parsed.Success(result2, _: Int) = parse("SELECT a, b,c,  d FROM table WHERE a=\"a\" AND (b=1 OR c=3)", selectDml)
    println(result2)

    val Parsed.Success(result3, _: Int) = parse("SELECT a FROM table WHERE (a=\"a\" OR b=2)", selectDml)
    println(result3)

    val Parsed.Success(result4, _: Int) = parse("SELECT a FROM table WHERE a=+1E+1", selectDml)
    println(result4)

    val Parsed.Success(result5, _: Int) = parse("SELECT a FROM table WHERE a=10.12", selectDml)
    println(result5)

    val Parsed.Success(result6, _: Int) = parse("SELECT a FROM table", selectDml)
    println(result6)

    val Parsed.Success(result7, _: Int) = parse("SELECT a", selectDml)
    println(result7)
  }
}
