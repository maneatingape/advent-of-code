package AdventOfCode2021

import scala.annotation.tailrec

object Day11:
  val directions = Seq((-1, -1), (0, -1), (1, -1), (-1, 0), (1, 0), (-1, 1), (0, 1), (1, 1))

  case class Point(x: Int, y: Int):
    def updated(dx: Int, dy: Int): Point = Point(x + dx, y + dy)

  case class Grid(energy: Map[Point, Int]):
    def points: Seq[Point] = energy.keys.toSeq
    def neighbours(point: Point): Seq[Point] = directions.map(point.updated).filter(energy.contains)
    def incremented(point: Point): Grid = copy(energy = energy + (point -> (energy(point) + 1)))
    def zeroed(point: Point): Grid = copy(energy = energy + (point -> 0))

  def parseGrid(input: Seq[String]): Grid =
    val energy = input.map(_.map(_.asDigit))
    val points = Seq.tabulate(energy.head.size, energy.size)((x, y) => Point(x, y) -> energy(y)(x))
    Grid(points.flatten.toMap)

  @tailrec
  def step(grid: Grid, todo: Seq[Point], flashed: Set[Point]): (Grid, Int) = todo match
    case Nil => (grid, flashed.size)
    case (head :: tail) =>
      if flashed.contains(head) then step(grid, tail, flashed)
      else if grid.energy(head) < 9 then step(grid.incremented(head), tail, flashed)
      else step(grid.zeroed(head), tail ++ grid.neighbours(head), flashed + head)

  def part1(input: Seq[String]): Int =
    @tailrec
    def helper(grid: Grid, iteration: Int = 1, total: Int = 0): Int =
      val (nextGrid, flashes) = step(grid, grid.points, Set())
      if iteration > 100 then total else helper(nextGrid, iteration + 1, total + flashes)
    helper(parseGrid(input))

  def part2(input: Seq[String]): Int =
    @tailrec
    def helper(grid: Grid, iteration: Int = 1): Int =
      val (nextGrid, flashes) = step(grid, grid.points, Set())
      if flashes == grid.energy.size then iteration else helper(nextGrid, iteration + 1)
    helper(parseGrid(input))

  def main(args: Array[String]): Unit =
    val data = io.Source.fromResource("AdventOfCode2021/Day11.txt").getLines().toSeq
    println(part1(data))
    println(part2(data))
