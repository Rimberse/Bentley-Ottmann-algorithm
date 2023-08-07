import controller.Algorithm
import model.Point
import model.Segment
import view.GUI
import java.util.ArrayList
import java.util.Random

fun main(args: Array<String>) {
    val rangeMin: Double = 100.0
    val rangeMax: Double = 900.0
    val data: ArrayList<Segment> = ArrayList()

    for(i in 0 until 100) {
        val p1: Point = Point(rand(rangeMin, rangeMax), rand(rangeMin, rangeMax))
        val p2: Point = Point(rand(rangeMin, rangeMax), rand(rangeMin, rangeMax))
        data.add(Segment(p1, p2))
    }

    val test: Algorithm = Algorithm(data)

    val time1: Long = System.currentTimeMillis()
    test.findIntersections()
    val time2: Long = System.currentTimeMillis()

    test.printIntersections()
    val intersections: ArrayList<Point>  = test.intersections;
    GUI(data, intersections);

    println("Number of intersections: " + intersections.size);
    println("Runtime: " + (time2 - time1) + " ms");
}

private fun rand(rangeMin: Double, rangeMax: Double): Double {
    val random: Random = Random()
    return rangeMin + (random.nextDouble() * (rangeMax - rangeMin));
}
