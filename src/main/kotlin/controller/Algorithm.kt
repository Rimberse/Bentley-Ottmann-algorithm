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
        queue = PriorityQueue();
        tree = TreeSet();
        coordinates = ArrayList();
    }
}
