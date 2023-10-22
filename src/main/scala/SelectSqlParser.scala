package com.lapoule.fastparse.sql
import fastparse.*
import NoWhitespace.*
import com.lapoule.fastparse.sql.SqlCommons.{Identifier, SimpleIdentifier, identifier, ws}

object SelectSqlParser {
  import SqlValues._
  import SqlColumns._
  import SqlCommons._
  import SqlExpressions._
  import SqlTables._

  def where[$: P]: P[Unit] = P(IgnoreCase("WHERE") ~ ws)

  def whereExp[$: P]: P[Expr] = P(where ~ combinedExpr ~ ws.?)

  case class Select(columns: Seq[ColumnExpr], tableSpec: Option[DataSelectionExpr], filteringExpr: Option[Expr]) {
    override def toString: String = s"SELECT ${columns.mkString(",")}${tableSpec.map(spec => s" FROM $spec").getOrElse("")}${filteringExpr.map(expr => s" WHERE ${expr}").getOrElse("")}"
  }

  private def select[$: P]: P[Unit] = P( IgnoreCase("SELECT") ~ ws )

  private def selectExpr[$: P]: P[Seq[ColumnExpr]] = P(select ~ columnExpr ~ ws.?)

  def selectDml[$: P]: P[Select] = P(selectExpr ~ fromExpr.? ~ whereExp.? ~ End)
    .map{ case (columnSpec, tableSpec: Option[DataSelectionExpr], filterings: Option[Expr]) => Select(columnSpec, tableSpec, filterings)}


  implicit class StringSelectContext(sc: StringContext) {
    def sql(args: Any*): Select =
      val str: String = sc.s(args: _*)
      parse(str, selectDml).
        get.
        fold(
          {  case (str, int, extra) => throw Exception("Cannot parse")},
          {case (res: Select, int: Int) =>  res })
    }
}

