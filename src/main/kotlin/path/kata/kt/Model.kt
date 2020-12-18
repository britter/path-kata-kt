package path.kata.kt

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int)

data class Segment(val origin: Point, val destination: Point)

data class Path(val segments: List<Segment>) {
    constructor(vararg segments: Segment) : this(segments = segments.toList())

    operator fun plus(segment: Segment) = Path(segments + listOf(segment))
}

object InconsecutivePath

fun path(vararg segments: Segment): Either<InconsecutivePath, Path> = segments.fold(Right(Path()) as Either<InconsecutivePath, Path>) { acc, segment ->
    when (acc) {
        is Either.Left -> acc
        is Either.Right -> when (val path = acc.b) {
            Path() -> Right(Path(segment))
            else -> when (path.segments.last().destination) {
                segment.origin -> Right(path + segment)
                else -> Left(InconsecutivePath)
            }
        }
    }
}

fun allPaths(origin: Point, destination: Point, segments: Set<Segment>, acc: Path = Path()): Set<Path> {
    if (origin == destination) {
        return setOf(acc)
    }
    return segments
        .filter { it.origin == origin }
        .flatMap { segment -> allPaths(segment.destination, destination, segments - segment, acc + segment) }
        .toSet()
}

fun shortestPath(vararg paths: Path, stops: Set<Point> = emptySet()) = paths
    .filter { path -> stops.isEmpty() || containsAllStops(path, stops) }
    .minByOrNull(::distance)

private fun containsAllStops(path: Path, stops: Set<Point>) = pointsContained(path).intersect(stops).isNotEmpty()

private fun pointsContained(path: Path) = path.segments.flatMap { s -> setOf(s.origin, s.destination) }

fun distance(path: Path) = path.segments.map { distance(it) }.sum()

fun distance(segment: Segment) = sqrt(
    (distanceSquared(segment.destination.x, segment.origin.x) +
            distanceSquared(segment.destination.y, segment.origin.y))
)

private fun distanceSquared(a: Int, b: Int) = (a - b).absoluteValue.toDouble().pow(2)
