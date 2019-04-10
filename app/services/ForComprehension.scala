package services

import scala.util.{Failure, Success, Try}

object ForComprehension {
  val list = List(1, 2, 3)

  def square(num: Int): Int = {
    num * num
  }

  list.map(square)

  def listMap[A, B](list: List[A])(f: A => B): List[B] = {
    list match {
      case Nil => Nil
      case head :: tail =>
        f(head) :: listMap(tail)(f)
    }
  }

  def mapWithFold2[A, B](aaas: List[A])(f: A => B): List[B] = {
    foldRight[A, List[B]](Nil, aaas)(f(_) :: _)
  }

  listMap(list)(square)
  listMap(list) { i =>
    i + 1
  }

  def listFlatMap[A, B](list: List[A])(f: A => List[B]): List[B] = {
    list match {
      case Nil => Nil
      case a :: as =>
        f(a) ++ listFlatMap(as)(f)
    }
  }

  def optionMap[A, B](option: Option[A])(f: A => B): Option[B] = {
    option match {
      case Some(a) => Some(f(a))
      case None => None
    }
  }

  def optionFlatMap[A, B](option: Option[A])(f: A => Option[B]): Option[B] = {
    option match {
      case Some(a) => f(a) // None can come from f...
      case None => None // ...or None can come from option
    }
  }

  optionMap(Some(1)) { i =>
    i * 3
  }

  val tmp = optionMap(Some("hello")) { s =>
    s.length
  }

  def tryMap[A, B](attempt: Try[A])(f: A => B): Try[B] = {
    attempt match {
      case Success(a) => Success(f(a))
      case Failure(exception) => Failure(exception)
    }
  }

  // come back to futureMap!!

  case class User()

  def getUser(id: String): Option[User] = {
    ???
  }

  def getAvatar(user: User): Option[String] = {
    ???
  }

  def filterOlderThan(age: Int): List[User] = {
    ???
  }

  def getArticlesUserLikes(user: User): List[String] = {
    ???
  }

  def getUserAvatar(id: String): Option[String] = {
    optionFlatMap(getUser(id))(getAvatar) // like a nested if
  }

  def getUserAvatar2(id: String): Option[String] = {
    for {
      user <- getUser(id)
      avatar <- getAvatar(user)
    } yield avatar
  }

  def getArticlesLikedByUsersOverAge(age: Int): List[String] = {
    // like a nested for loop
    listFlatMap(filterOlderThan(age))(getArticlesUserLikes)
  }

  val strings = List("hello", "goodbye")
  val characters: List[Int] = listFlatMap(strings) { str =>
    listMap(str.toList) { char =>
      char.toInt
    }
  }

  val characters2 = for {
    str <- strings
    uppercaseStr = str.toUpperCase
    char <- uppercaseStr.toList
    int = char.toInt // char.toInt returns an int, so the = assigns the int to a value
  } yield int

  // traverse Swaps the two effects list <> try
  def tryTraverse[A, B](aaas: List[A])(f: A => Try[B]): Try[List[B]] = {
    foldRight[A, Try[List[B]]](Success(Nil), aaas) { (a, acc) =>
      for {
        bbbs <- acc // extracts from Try effect
        b <- f(a) // extracts from Try effect
      } yield b :: bbbs // yields the same effect in this case a Try
    }
  }

  def eitherTraverse[L, A, B](aaas: List[A])(f: A => Either[L, B]): Either[L, List[B]] = {
    foldRight[A, Either[L, List[B]]](Right(Nil), aaas) { (a, acc) =>
      for {
        bbbs <- acc // extracts from Try effect
        b <- f(a) // extracts from Try effect
      } yield b :: bbbs // yields the same effect in this case a Try
    }
  }

  def foldLeft[A, B](acc: B, aaas: List[A])(f: (A, B) => B): B = {
    aaas match {
      case Nil => acc
      case head :: tail => foldLeft(f(head, acc), tail)(f)
    }
  }

  def foldRight[A, B](acc: B, aaas: List[A])(f: (A, B) => B): B = {
    foldLeft(acc, aaas.reverse)(f)
  }

  //(n, acc) => acc + n ===> (_+_) !!!!!
  def sum(list: List[Int]): Int = {
    foldLeft(0, list)(_ + _)
  }

  def length[A](list: List[A]): Int = {
    foldLeft(0, list)((_, acc) => acc + 1)
  }

  def mapWithFold[A, B](aaas: List[A])(f: A => B): List[B] = {
    foldRight[A, List[B]](Nil, aaas) { (a, acc) =>
      // acc ++ List(f(a)) - terrible performance
      f(a) :: acc // excellent performance - list is optimized for ops on its head
    }
  }

  def flatMapWithFold[A, B](aaas: List[A])(f: A => List[B]): List[B] = {
    foldRight[A, List[B]](Nil, aaas) { (a, acc) =>
      f(a) ++ acc
    }
  }

  // look at optionFold

  def test() = {
    println(mapWithFold(List(1, 2, 3))(identity))
  }
}
