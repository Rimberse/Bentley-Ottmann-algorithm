package controller

import model.Event
import model.Point
import model.Segment
import java.util.NavigableSet
import java.util.PriorityQueue
import java.util.Queue
import java.util.TreeSet

class Algorithm(var inputData: List<Segment>) {
    private val queue: Queue<Event>;
    private val tree: NavigableSet<Segment>;
    private val coordinates: List<Point>

    init {
        queue = PriorityQueue(eventComparator());
        tree = TreeSet(segmentComparator());
        coordinates = ArrayList();

        for (segment: Segment in inputData) {
            queue.add(Event(segment.start, segment, 0));
            queue.add(Event(segment.end, segment, 1));
        }
    }

    private inner class eventComparator : Comparator<Event> {
        override fun compare(e1: Event, e2: Event): Int {
            if (e1.value > e2.value)
                return 1;
            else if (e1.value < e2.value)
                return -1;

            return 0;
        }
    }

    private inner class segmentComparator : Comparator<Segment> {
        override fun compare(s1: Segment, s2: Segment): Int {
            if (s1.value < s2.value)
                return 1;
            else if (s1.value > s2.value)
                return -1;

            return 0;
        }
    }
}
