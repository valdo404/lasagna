package com.lapoule.fastparse.sql

import fastparse.*

object SqlColumns {
  import fastparse.NoWhitespace.*

  import SqlCommons.*
  import SqlValues.*

  sealed trait ColumnExpr

  case object Star extends ColumnExpr {
    override def toString: String = "*"
  }

  case class NamedColumnExp(columnExpr: SimpleColumn, identifier: SimpleIdentifier) extends ColumnExpr {
    override def toString: String = s"$columnExpr AS $identifier"
  }

  trait SimpleColumn extends ColumnExpr

  case class Column(identifier: Identifier) extends SimpleColumn {
    override def toString: String = s"$identifier"
  }

  case class ValueColumn(value: Value) extends SimpleColumn {
    override def toString: String = s"$value"
  }

  def starExpr[$: P]: P[Star.type] = P("*").map(_ => Star)

  def simpleColumn[$: P]: P[SimpleColumn] = P(identifier).map(id => Column(id))

  def namedColumnExp[$: P]: P[NamedColumnExp] = P(simpleColumn ~ ws ~ "AS" ~ ws ~ identifier).
    map { case (column: SimpleColumn, identifier: SimpleIdentifier) => NamedColumnExp(columnExpr = column, identifier = identifier) }

  def sqlComma[$: P]: P[Unit] = P("," ~ ws.rep(0).?)

  def columnExpr[$: P]: P[Seq[ColumnExpr]] = P((namedColumnExp | simpleColumn | starExpr).repX(1, sqlComma./))

}
