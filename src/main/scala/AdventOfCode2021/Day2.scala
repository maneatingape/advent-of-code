package AdventOfCode2021

object Day2 {
  val sample = Seq("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2")

  val data = io.Source.fromResource("AdventOfCode2021/Day2.txt").getLines.toSeq

  case class Command(prefix: String) {
    def unapply(s: String) = Option.when(s.startsWith(prefix))(s.drop(prefix.length + 1).toInt)
  }

  val forward = Command("forward")
  val down = Command("down")
  val up = Command("up")

  def part1(input: Seq[String]) = {
    val (distance, depth) = input.foldLeft((0, 0)) { case ((distance, depth), command) =>
      command match {
        case forward(value) => (distance + value, depth)
        case down(value) => (distance, depth + value)
        case up(value) => (distance, depth - value)
      }
    }

    distance * depth
  }

  def part2(input: Seq[String]) = {
    val (distance, depth, _) = input.foldLeft((0, 0, 0)) { case ((distance, depth, aim), command) =>
      command match {
        case forward(value) => (distance + value, depth + aim * value, aim)
        case down(value) => (distance, depth, aim + value)
        case up(value) => (distance, depth, aim - value)
      }
    }

    distance * depth
  }

  def main(args: Array[String]) = {
    println(part1(sample))
    println(part2(sample))
    println(part1(data))
    println(part2(data))
  }
}
