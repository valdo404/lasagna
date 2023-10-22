package com.lapoule.lasagna.parser

import SqlCommons.{identifier, ws}

object SqlTables {
  import com.lapoule.lasagna.sql.Model._
  import fastparse.*
  import NoWhitespace.*

  private def from[$: P]: P[Unit] = P(IgnoreCase("FROM") ~ ws)

  private def simpleTable[$: P]: P[SimpleTable] = P(identifier).map(id => SimpleTable(id))

  private def namedTableExp[$: P]: P[NamedTable] = P(simpleTable ~ ws ~ IgnoreCase("AS") ~ ws ~ identifier).
    map { case (table: SimpleTable, identifier: SimpleIdentifier) => NamedTable(table = table, asClause = identifier) }

  def fromExpr[$: P]: P[DataSelectionExpr] = P(from ~ (namedTableExp | simpleTable) ~ ws.?)
}
