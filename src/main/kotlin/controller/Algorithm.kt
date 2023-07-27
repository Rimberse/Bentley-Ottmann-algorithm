package controller

import model.Event
import model.Point
import model.Segment
import java.util.NavigableSet
import java.util.PriorityQueue
import java.util.Queue
import java.util.TreeSet
import java.util.concurrent.Semaphore

class Algorithm(var inputData: List<Segment>) {
    private val queue: Queue<Event>;
    private val tree: NavigableSet<Segment>;
    private val coordinates: List<Point>

    init {
        queue = PriorityQueue(EventComparator());
        tree = TreeSet(SegmentComparator());
        coordinates = ArrayList();

        for (segment: Segment in inputData) {
            queue.add(Event(segment.start, segment, 0));
            queue.add(Event(segment.end, segment, 1));
        }
    }

    fun findIntersections() {
        while (!queue.isEmpty()) {
            val event: Event = queue.poll();
            val sweepLine: Double = event.value;

            when(event.type) {
                0 -> {
                    for (segment: Segment in event.segments) {
                        recalculate(sweepLine);
                        tree.add(segment);

                        if (tree.higher(segment) != null) {
                            val r: Segment = tree.higher(segment);
                            reportIntersection(r, segment, sweepLine);
                        }

                        if (tree.lower(segment) != null) {
                            val t: Segment = tree.lower(segment);
                            reportIntersection(t, segment, sweepLine);
                        }

                        if (tree.higher(segment) != null && tree.lower(segment) != null) {
                            val r: Segment = tree.higher(segment);
                            val t: Segment = tree.lower(segment);
                        }
                    }
                }

                1 -> {

                }

                2 -> {

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

    private fun recalculate(line: Double) {
        tree.forEach {
            it.calculateValue(line)
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
