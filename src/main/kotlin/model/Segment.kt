package model

data class Segment(var start: Point, var end: Point) {
    var value: Double = 0.0;

    init {
        calculateValue(first().x);
    }

    fun first(): Point {
        return if (start.x <= end.x) start else end;
    }

    fun second(): Point {
        return if (start.x <= end.x) end else start;
    }

    fun calculateValue(value: Double) {
        val x1 = first().x;
        val x2 = second().x;
        val y1 = first().y;
        val y2 = second().y;
        this.value = y1 + (((y2 - y1) / (x2 - x1)) * (value - x1));
    }
}
