package com.lapoule.lasagna.parser

import fastparse.*

object SqlExpressions {
  import SqlCommons.*
  import SqlValues.*
  import fastparse.NoWhitespace.*
  import com.lapoule.lasagna.sql.Model._

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
