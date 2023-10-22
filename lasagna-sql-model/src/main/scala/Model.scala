package com.lapoule.lasagna.sql

object Model {
  sealed trait Value extends Atom

  case class StringValue(str: String) extends Value {
    override def toString: String = s"\"$str\""
  }

  case class IntValue(int: Int) extends Value {
    override def toString: String = int.toString
  }

  case class FloatValue(float: Float) extends Value {
    override def toString: String = float.toString
  }

  trait Sign

  case object Plus extends Sign {
    override def toString: String = "+"
  }

  case object Minus extends Sign {
    override def toString: String = "-"
  }

  trait Atom

  sealed trait Identifier extends Atom

  case class SimpleIdentifier(str: String) extends Identifier {
    override def toString: String = str
  }

  case class NamespacedIdentifier(namespace: SimpleIdentifier, identifier: SimpleIdentifier) extends Identifier {
    override def toString: String = s"$namespace.$identifier"
  }

  sealed trait FilteringCombinator

  case object And extends FilteringCombinator {
    override def toString: String = "AND"
  }

  case object Or extends FilteringCombinator {
    override def toString: String = "OR"
  }

  sealed trait Operator

  case object Equal extends Operator {
    override def toString: String = "="
  }

  case object NotEqual extends Operator {
    override def toString: String = "!="
  }

  case object Greater extends Operator {
    override def toString: String = ">"
  }

  case object Lower extends Operator {
    override def toString: String = "<"
  }

  sealed trait Expr

  sealed trait Simple extends Expr

  case class Unary(atom: Atom) extends Simple {
    override def toString: String = s"$atom"
  }

  case class Binary(left: Atom, operator: Operator, right: Atom) extends Simple {
    override def toString: String = s"$left$operator$right"
  }

  case class ChainedExpr(left: Expr, filteringCombinator: FilteringCombinator, right: Expr) extends Expr {
    override def toString: String = s"$left $filteringCombinator $right"
  }

  case class GroupedExpr(contents: Expr) extends Expr {
    override def toString: String = s"($contents)"
  }


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


  trait DataSelectionExpr

  case class SimpleTable(identifier: Identifier) extends DataSelectionExpr {
    override def toString: String = identifier.toString
  }

  case class NamedTable(table: SimpleTable, asClause: SimpleIdentifier) extends DataSelectionExpr {
    override def toString: String = s"$table AS $asClause"
  }

  case class Select(columns: Seq[ColumnExpr], tableSpec: Option[DataSelectionExpr], filteringExpr: Option[Expr]) {
    override def toString: String = s"SELECT ${columns.mkString(",")}${tableSpec.map(spec => s" FROM $spec").getOrElse("")}${filteringExpr.map(expr => s" WHERE ${expr}").getOrElse("")}"
  }
}
