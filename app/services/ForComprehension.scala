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

  listMap(list)(square)
  listMap(list){ i =>
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

  optionMap(Some(1)){ i =>
    i * 3
  }

  val tmp = optionMap(Some("hello")){ s =>
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
  val characters: List[Int] = listFlatMap(strings){ str =>
    listMap(str.toList){ char =>
      char.toInt
    }
  }

  val characters2 = for {
    str <- strings
    uppercaseStr = str.toUpperCase
    char <- uppercaseStr.toList
    int = char.toInt // char.toInt returns an int, so the = assigns the int to a value
  } yield int
}
