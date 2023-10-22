package com.lapoule.fastparse.sql

import fastparse.*
import NoWhitespace.*
import scopt.OParser


object Main {
  import SelectSqlParser._
  case class Config(sql: String = "")

  val builder = OParser.builder[Config]

  val parser = {
    import builder._
    OParser.sequence(
      programName("sql-cli"),
      head("sql-cli", "1.0"),
      opt[String]("sql")
        .required()
        .action((x, c) => c.copy(sql = x))
        .text("SQL query to execute")
    )
  }


  /**
     * Note: an extensive SQL parser would check if request is valid:
     * - Semantical check on namespaces and referenced data
     * - Semantical cross-check on field selection part and field-filtering part
     * - Semantical checks on group by
     *
     * Also it would allow standard function calls
     * Also it would allow negating using NOT
     * Also it would allow limiting using LIMIT / FETCH FIRST n ROWS ONLY / OFFSET
     * Also it would allow (inner|left|right|outer) joins
     * Also it would allow group by using GROUP BY, SUM, AVG, MIN, MAX, COUNT
     * Also it would allow ORDER BY,

     * Also it would allow unions using UNION
     *
     *
     * Also it would allow conditional logic using CASE
     * CONCAT (string)
     * LIKE, REGEXP (pattern matching)
     * IS NULL, IS NOT NULL, IFNULL, COALESCE (null handling)
     * HAVING (for post aggregate selection)
     * DISTINCT (for selecting distinct elements
     *
     * Also it would allow (correlated) (named) subqueries
     *
     * INTERSET / EXCEPT / MINUS (sets)
     * PARTITION BY (for windowing)
     * Also it would allow window functions using OVER, LAG, ROW_NUMBER, RANK, DENSE_RANK, NTILE, LEAD, FIRST_VALUE, LAST_VALUE, etc
     * * Also it would allow CUBE, ROLLUP, GROUPING, GROUPING_IDS
     * WITH ... AS, WITH RECURSIVE (ctes)
     * Comments (-- blabla)
     *
     *
     * USE INDEX, FORCE INDEX, IGNORE INDEX (index handling)
     * JSON_EXTRACT / JSON_OBJECT (for json data types)
     * XMLQUERY / XMLCAST (for xml data types)
     * UNNEST (array operations)
     * COLLATE (character set spec)
     */
  def main(args: Array[String]): Unit = {
    OParser.parse(parser, args, Config()) match {
      case Some(config) =>
        val result = parseSQL(config.sql)
        println(result)
        println(result.columns)
        println(result.tableSpec)
        println(result.filteringExpr)
      case _ =>
    }
  }

  def parseSQL(sql: String): Select = {
    // Replace this with your actual SQL execution code
    sql"$sql"
  }
}
