package org.sgine.task

sealed trait Conclusion

object Conclusion {
  case object Continue extends Conclusion
  case object Finished extends Conclusion
}