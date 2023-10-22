import com.lapoule.lasagna.parser.SelectSqlParser.StringSelectContext
import org.scalatest.funsuite.AnyFunSuite

class SimpleParseCheck extends AnyFunSuite {

  test("Test SQL Query 1") {
    val result = sql"""SELECT A.a AS truc, b, c, d FROM table AS A WHERE a AND "dude4"=b OR c=3"""
    // Perform your assertions here
    // assert(result == expectedOutput)
  }

  test("Test SQL Query 2") {
    val result2 = sql"""SELECT a, B.b, c, d FROM table as A WHERE a="a" AND (b=1 OR c=3)"""
    // Perform your assertions here
    // assert(result2 == expectedOutput)
  }

  test("Test SQL Query 3") {
    val result3 = sql"""SELECT a FROM table AS C WHERE (a="a" OR b=2)"""
    // Perform your assertions here
    // assert(result3 == expectedOutput)
  }

  test("Test SQL Query 4") {
    val result4 = sql"SELECT a FROM table WHERE a=+1E+1"
    // Perform your assertions here
    // assert(result4 == expectedOutput)
  }

  test("Test SQL Query 5") {
    val result5 = sql"SELECT a FROM table WHERE a= 10.12"
    // Perform your assertions here
    // assert(result5 == expectedOutput)
  }

  test("Test SQL Query 6") {
    val result6 = sql"SELECT a FROM table"
    // Perform your assertions here
    // assert(result6 == expectedOutput)
  }

  test("Test SQL Query 7") {
    val result7 = sql"SELECT a"
    // Perform your assertions here
    // assert(result7 == expectedOutput)
  }
}