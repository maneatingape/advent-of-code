package AdventOfCode2021

object Day01:
  def part1(input: Seq[Int]): Int = input.sliding(2).count { case Seq(x, y) => y > x }

  def part2(input: Seq[Int]): Int = input.sliding(4).count { case Seq(x, _, _, y) => y > x }

  def main(args: Array[String]): Unit =
    val data = io.Source.fromResource("AdventOfCode2021/Day01.txt").getLines().map(_.toInt).toSeq
    println(part1(data))
    println(part2(data))
