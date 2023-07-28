package model

data class Event(var point: Point, var segments: List<Segment>, var type: Int) {
    var value: Double = point.x;

    constructor(point: Point, segment: Segment, type: Int) : this(point, arrayListOf(segment), type);
}
