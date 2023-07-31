package view

import model.Point
import model.Segment
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
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
}