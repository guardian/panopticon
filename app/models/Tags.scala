package models

import enumeratum.{Enum, EnumEntry, PlayJsonEnum}
import scala.util.Random

sealed trait Tag extends EnumEntry
object Tag extends Enum[Tag] with PlayJsonEnum[Tag] {
  val values = findValues
  case object Navigation extends Tag
  case object Podcast extends Tag
  case object GDPR extends Tag
  case object Atoms extends Tag
  case object Accessibility extends Tag
  case object Payment extends Tag
  case object Checkout extends Tag
  case object Contributions extends Tag
  case object Europe extends Tag
  case object Testtag extends Tag

  // this function will be removed once we're able to add tags to Drive data
  def selectRandomTags(): List[Tag] = {
    val tagList = Tag.values.toList
    Random.shuffle(tagList).take(2)
  }

}
