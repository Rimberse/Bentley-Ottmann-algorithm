package model

data class Event(var point: Point, var segments: List<Segment>, var value: Double, var type: Int) {
    constructor(point: Point, segment: Segment, type: Int) : this(point, arrayListOf(segment), point.x, type);
}
