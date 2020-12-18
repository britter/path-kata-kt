package path.kata.kt

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll

class PathSpecs: StringSpec({
    "String size" {
        forAll<String, String> { a, b ->
            (a + b).length == a.length + b.length
        }
    }
    "distance of a segment" {
        forAll<Int, Int, Int, Int> { x1, y1, x2, y2 ->
            
        }
    }
})
