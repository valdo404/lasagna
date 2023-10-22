package com.lapoule.fastparse.sql

import fastparse.{CharIn, P}
import fastparse.*
import NoWhitespace.*

object SqlCommons {
  def ws[$: P]: P[Unit] = P(" ".rep(1))

  trait Atom

  sealed trait Identifier extends Atom

  case class SimpleIdentifier(str: String) extends Identifier {
    override def toString: String = str
  }

  case class NamespacedIdentifier(namespace: SimpleIdentifier, identifier: SimpleIdentifier) extends Identifier {
    override def toString: String = s"$namespace.$identifier"
  }

  def simpleIdentifier[$: P] = P((CharIn("a-zA-Z_") ~ CharIn("a-zA-Z0-9_").rep).!).
    map((str: String) => SimpleIdentifier(str))

  def namespacedIdentifier[$: P]: P[NamespacedIdentifier] = P(simpleIdentifier ~ "." ~ simpleIdentifier).
    map { case (namespace: SimpleIdentifier, identifier: SimpleIdentifier) => NamespacedIdentifier(namespace, identifier) }

  def identifier[$: P]: P[Identifier] = P(namespacedIdentifier | simpleIdentifier)
}
