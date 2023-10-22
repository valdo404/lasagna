package com.lapoule.lasagna.parser

import fastparse.NoWhitespace.*
import fastparse.*

object SqlCommons {
  import com.lapoule.lasagna.sql.Model.*

  def ws[$: P]: P[Unit] = P(" ".rep(1))


  def simpleIdentifier[$: P] = P((CharIn("a-zA-Z_") ~ CharIn("a-zA-Z0-9_").rep).!).
    map((str: String) => SimpleIdentifier(str))

  def namespacedIdentifier[$: P]: P[NamespacedIdentifier] = P(simpleIdentifier ~ "." ~ simpleIdentifier).
    map { case (namespace: SimpleIdentifier, identifier: SimpleIdentifier) => NamespacedIdentifier(namespace, identifier) }

  def identifier[$: P]: P[Identifier] = P(namespacedIdentifier | simpleIdentifier)

}
