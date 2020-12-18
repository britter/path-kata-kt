package path.kata.kt

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.checkAll
import io.kotest.property.forAll
import kotlin.math.abs

class PathSpecs : StringSpec({
    val points = arbitrary { rs ->
        val x = Arb.int().next(rs)
        val y = Arb.int().next(rs)
        Point(x, y)
    }

    "String size" {
        forAll<String, String> { a, b ->
            (a + b).length == a.length + b.length
        }
    }
    "distance of a segment" {
        forAll<Int, Int, Int> { x1, x2, y ->
            val p1 = Point(x1, y)
            val p2 = Point(x2, y)
            distance(Segment(p1, p2)) == abs(x1 - x2).toDouble()
        }

        checkAll(points, points) { p1, p2 ->
            if (p1 != p2) {
                
            }
        }
    }

})
