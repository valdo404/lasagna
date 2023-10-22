package com.lapoule.lasagna.parser

import fastparse.*

object SqlColumns {
  import com.lapoule.lasagna.sql.Model._

  import SqlCommons.*
  import SqlValues.*
  import fastparse.NoWhitespace.*

  def starExpr[$: P]: P[Star.type] = P("*").map(_ => Star)

  def simpleColumn[$: P]: P[SimpleColumn] = P(identifier).map(id => Column(id))

  def namedColumnExp[$: P]: P[NamedColumnExp] = P(simpleColumn ~ ws ~ IgnoreCase("AS") ~ ws ~ identifier).
    map { case (column: SimpleColumn, identifier: SimpleIdentifier) => NamedColumnExp(columnExpr = column, identifier = identifier) }

  def sqlComma[$: P]: P[Unit] = P("," ~ ws.rep(0).?)

  def columnExpr[$: P]: P[Seq[ColumnExpr]] = P((namedColumnExp | simpleColumn | starExpr).repX(1, sqlComma./))

}
