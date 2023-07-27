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
                        }

                        if (tree.lower(segment) != null) {
                            val t: Segment = tree.lower(segment);

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
