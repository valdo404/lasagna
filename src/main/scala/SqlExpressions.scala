package com.lapoule.fastparse.sql


import fastparse.*

object SqlExpressions {
  import SqlCommons._
  import SqlValues._
  import fastparse.NoWhitespace.*
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


  def operator[$: P]: P[Operator] = P("=".!).map(str => Equal)
  def and[$: P]: P[And.type] = P(IgnoreCase("AND")).map(_ => And)
  def or[$: P]: P[Or.type] = P(IgnoreCase("OR")).map(_ => Or)

  def atom[$: P]: P[Atom] = P(identifier | value)

  def filteringCombinator[$: P]: P[FilteringCombinator] = P(ws ~ (and | or) ~ ws)

  def simpleBinaryExpr[$: P]: P[Binary] = P(atom ~ ws.? ~ operator ~ ws.? ~ atom)
    .map { case (left, operator, right) => Binary(left = left, operator = operator, right = right) }

  def simpleUnaryExpr[$: P]: P[Unary] = P(atom)
    .map { case (atom) => Unary(atom = atom) }

  def simple[$: P]: P[Simple] = P(simpleBinaryExpr | simpleUnaryExpr)

  def chainedExpr[$: P]: P[ChainedExpr] = P((simple ~ filteringCombinator ~ combinedExpr)).
    map { case (left: Expr, combinator: FilteringCombinator, right: Expr) => ChainedExpr(left, combinator, right) }

  def groupedExpr[$: P]: P[GroupedExpr] = P("(" ~ combinedExpr ~ ")").map(filter => GroupedExpr(filter))

  def combinedExpr[$: P]: P[Expr] = P(groupedExpr | chainedExpr | simpleBinaryExpr | simpleUnaryExpr)
 
 }
