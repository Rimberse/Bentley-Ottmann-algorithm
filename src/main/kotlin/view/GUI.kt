package view

import model.Point
import model.Segment
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.KeyStroke
import kotlin.system.exitProcess

class GUI(private val inputData: List<Segment>, private val intersections: ArrayList<Point>): JFrame() {
    private var repaintFlag: Boolean

    init {
        repaintFlag = false

        val panel: JPanel = JPanel()
        contentPane.add(panel)

        setSize(1000, 1000)
        title = "Bentley-Ottmann algorithm"

        val dimension: Dimension = Toolkit.getDefaultToolkit().screenSize
        val x: Int = ((dimension.width - width) / 2).toInt()
        val y: Int = ((dimension.height - height) / 2).toInt()
        setLocation(x, y)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        getRootPane().registerKeyboardAction(ActionListener {
                _ : ActionEvent? -> exitProcess(0)
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)

        getRootPane().registerKeyboardAction(ActionListener {
                _ : ActionEvent? ->
            run {
                repaintFlag = !repaintFlag
                repaint()
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)

        isVisible = true
    }

    override fun paint(graphics: Graphics) {
        super.paint(graphics)
        val graphics2D: Graphics2D = graphics as Graphics2D
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        for (s: Segment in inputData) {
            val segment: Line2D.Double = Line2D.Double(s.first().x, s.first().y, s.second().x, s.second().y)
            graphics2D.draw(segment)
        }

        if (repaintFlag) {
            graphics2D.drawString("number of intersections: " + this.intersections.size, 40, 70)

            for (p: Point in this.intersections) {
                val newX: Double = p.x - 6.0 / 2.0
                val newY: Double = p.y - 6.0 / 2.0
                val point: Ellipse2D.Double = Ellipse2D.Double(newX, newY, 6.0, 6.0)
                graphics2D.paint = Color.RED;
                graphics2D.fill(point);
                graphics.draw(point);
            }
        }
    }
}
