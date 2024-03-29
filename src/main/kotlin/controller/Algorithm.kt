package controller

import model.Event
import model.Point
import model.Segment
import java.util.NavigableSet
import java.util.PriorityQueue
import java.util.Queue
import java.util.TreeSet

class Algorithm(var inputData: List<Segment>) {
    val queue: Queue<Event>
    val tree: NavigableSet<Segment>
    val intersections: ArrayList<Point>

    init {
        queue = PriorityQueue(EventComparator())
        tree = TreeSet(SegmentComparator())
        intersections = ArrayList()

        for (segment: Segment in inputData) {
            queue.add(Event(segment.first(), segment, 0))
            queue.add(Event(segment.second(), segment, 1))
        }
    }

    fun findIntersections() {
        while (!queue.isEmpty()) {
            val event: Event = queue.poll()
            val sweepLine: Double = event.value

            when(event.type) {
                0 -> {
                    for (segment: Segment in event.segments) {
                        recalculate(sweepLine)
                        tree.add(segment)

                        tree.lower(segment)?.let { r ->
                            reportIntersection(r, segment, sweepLine)
                        }

                        tree.higher(segment)?.let { t ->
                            reportIntersection(t, segment, sweepLine)
                        }

                        tree.lower(segment)?.let { r ->
                            tree.higher(segment)?.let { t ->
                                removeFuture(r, t)
                            }
                        }
                    }
                }

                1 -> {
                    for (segment: Segment in event.segments) {
                        tree.lower(segment)?.let { r ->
                            tree.higher(segment)?.let { t ->
                                reportIntersection(r, t, sweepLine)
                            }
                        }

                        tree.remove(segment)
                    }
                }

                2 -> {
                    val s1: Segment = event.segments[0]
                    val s2: Segment = event.segments[1]
                    swap(s1, s2)

                    if (s1.value < s2.value) {
                        tree.higher(s1)?.let { t ->
                            reportIntersection(t, s1, sweepLine)
                            removeFuture(t, s2)
                        }

                        tree.lower(s2)?.let { r ->
                            reportIntersection(r, s2, sweepLine)
                            removeFuture(r, s1)
                        }
                    } else {
                        tree.higher(s2)?.let { t ->
                            reportIntersection(t, s2, sweepLine)
                            removeFuture(t, s1)
                        }

                        tree.lower(s1)?.let { r ->
                            reportIntersection(r, s1, sweepLine)
                            removeFuture(r, s2)
                        }
                    }

                    intersections.add(event.point)
                }
            }
        }
    }

    private fun reportIntersection(s1: Segment, s2: Segment, sweepLine: Double): Boolean {
        val x1 = s1.first().x
        val y1 = s1.first().y
        val x2 = s1.second().x
        val y2 = s1.second().y
        val x3 = s2.first().x
        val y3 = s2.first().y
        val x4 = s2.second().x
        val y4 = s2.second().y
        val r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3)

        if (r != 0.0) {
            val t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r
            val u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r

            if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                val x_c = x1 + t * (x2 - x1)
                val y_c = y1 + t * (y2 - y1)

                if (x_c > sweepLine) {
                    queue.add(Event(Point(x_c, y_c), arrayListOf(s1, s2), 2))
                    return true
                }
            }
        }

        return false
    }

    private fun removeFuture(s1: Segment, s2: Segment): Boolean {
        queue.removeAll {
            it.type == 2 && ((it.segments[0] == s1 && it.segments[1] == s2) || (it.segments[0] == s2 && it.segments[1] == s1))
        }

        return true
    }

    private fun swap(s1: Segment, s2: Segment) {
        tree.remove(s1)
        tree.remove(s2)
        val value = s1.value
        s1.value = s2.value
        s2.value = value
        tree.add(s1)
        tree.add(s2)
    }

    private fun recalculate(line: Double) {
        tree.forEach {
            it.calculateValue(line)
        }
    }

    fun printIntersections() {
        intersections.forEach {
            println("(${it.x}, ${it.y})")
        }
    }

    private inner class EventComparator : Comparator<Event> {
        override fun compare(e1: Event, e2: Event): Int {
            if (e1.value > e2.value)
                return 1;
            else if (e1.value < e2.value)
                return -1;

            return 0;
        }
    }

    private inner class SegmentComparator : Comparator<Segment> {
        override fun compare(s1: Segment, s2: Segment): Int {
            if (s1.value < s2.value)
                return 1;
            else if (s1.value > s2.value)
                return -1;

            return 0;
        }
    }
}
