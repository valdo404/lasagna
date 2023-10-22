package com.lapoule.fastparse.sql

import fastparse.*
import NoWhitespace.*
object Main {
  import SelectSqlParser._


  /**
   * Note: an extensive SQL parser would check if request is valid:
   * - Semantical check on namespaces and referenced data
   * - Semantical cross-check on field selection part and field-filtering part
   *
   * Also it would allow standard function calls
   * Also it would allow (inner|left|right|outer) joins
   * Also it would allow (correlated) (named) subqueries
   * Also it would allow unions
   */
  def main(args: Array[String]): Unit = {
    val result = sql"""SELECT   A.a AS truc, b,c,  d FROM table AS A WHERE a     AND     "dude4"=b    OR c=3"""
    println(result)

    val result2 = sql"""SELECT a, B.b,c, d FROM table as A WHERE a="a" AND (b=1 OR c=3)"""
    println(result2)

    val result3 = sql"""SELECT a FROM table AS C WHERE (a="a" OR   b=2)"""
    println(result3)

    val result4 = sql"SELECT a FROM table WHERE a=+1E+1"
    println(result4)

    val result5 = sql"SELECT a FROM table WHERE a= 10.12"
    println(result5)

    val result6 = sql"SELECT a   FROM table"
    println(result6)

    val result7 = sql"SELECT a   "
    println(result7)
  }
}
