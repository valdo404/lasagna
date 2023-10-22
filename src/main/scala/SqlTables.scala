package com.lapoule.fastparse.sql

import SqlCommons.{Identifier, SimpleIdentifier, identifier, ws}

object SqlTables {

  import fastparse.*
  import NoWhitespace.*

  trait DataSelectionExpr

  case class SimpleTable(identifier: Identifier) extends DataSelectionExpr {
    override def toString: String = identifier.toString
  }

  case class NamedTable(table: SimpleTable, asClause: SimpleIdentifier) extends DataSelectionExpr {
    override def toString: String = s"$table AS $asClause"
  }

  private def from[$: P]: P[Unit] = P(IgnoreCase("FROM") ~ ws)

  private def simpleTable[$: P]: P[SimpleTable] = P(identifier).map(id => SimpleTable(id))

  private def namedTableExp[$: P]: P[NamedTable] = P(simpleTable ~ ws ~ IgnoreCase("AS") ~ ws ~ identifier).
    map { case (table: SimpleTable, identifier: SimpleIdentifier) => NamedTable(table = table, asClause = identifier) }

  def fromExpr[$: P]: P[DataSelectionExpr] = P(from ~ (namedTableExp | simpleTable) ~ ws.?)
}
