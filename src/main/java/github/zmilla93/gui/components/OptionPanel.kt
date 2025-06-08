package github.zmilla93.gui.components

import github.zmilla93.core.utility.GUIReferences
import github.zmilla93.gui.options.HeaderPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.*
import javax.swing.*

/**
 * Used to create consistently formatted option panels.
 * Added components always appear at the top left of the panel.
 * // FIXME: Refactor add names?
 * Do not add components directly. Instead, use the addHeader() and addComponent() functions.
 */
open class OptionPanel @JvmOverloads constructor(addScrollPanel: Boolean = true, addInsets: Boolean = true) : JPanel() {

    var contentPanel: JPanel
    var scrollPane: CustomScrollPane? = null
        private set
    private val gc = GridBagConstraints()


    init {
        super.setLayout(BorderLayout())
        contentPanel = JPanel(GridBagLayout())
        val insetPanel = JPanel(BorderLayout())
        // FIXME: Debug borders?
        //        if (App.debugUIBorders >= 1) {
//            setBackground(new Color(96, 236, 122));
//            contentPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
//        }
        // FIXME: if insets are disable, should not add insets panel at all
        if (addInsets) {
            insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.NORTH)
            insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.SOUTH)
            insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.EAST)
            insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.WEST)
        }
        insetPanel.add(contentPanel, BorderLayout.CENTER)
        val bufferPanel = JPanel(BorderLayout())
        bufferPanel.add(insetPanel, BorderLayout.NORTH)
        if (addScrollPanel) {
            scrollPane = CustomScrollPane(bufferPanel)
            super.add(scrollPane, BorderLayout.CENTER)
        } else {
            super.add(bufferPanel, BorderLayout.CENTER)
        }
        gc.gridx = 0
        gc.gridy = 0
    }

    fun addHeader(title: String?): HeaderPanel {
        val headerPanel = HeaderPanel(title)
        val prevFill = gc.fill
        val prevWeightX = gc.weightx
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0
        contentPanel.add(headerPanel, gc)
        gc.fill = prevFill
        gc.weightx = prevWeightX
        gc.gridy++
        contentPanel.add(Box.createVerticalStrut(2), gc)
        gc.gridy++
        return headerPanel
    }

    fun addSeperator(): JSeparator {
        val prevFill = gc.fill
        val prevWeightX = gc.weightx
        gc.fill = GridBagConstraints.HORIZONTAL


        //        gc.weightx = 1;
//        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0
        val seperator = JSeparator(SwingConstants.HORIZONTAL)
        contentPanel.add(seperator, gc)
        gc.fill = prevFill
        gc.weightx = prevWeightX
        //        gc.gridy++;
//        contentPanel.add(Box.createVerticalStrut(2), gc);
        gc.gridy++
        return seperator
    }

    fun add(text: String?): JLabel {
        val label = JLabel(text)
        addComponent(label)
        return label
    }

    fun addLabel(text: String?): JLabel {
        val label = JLabel(text)
        addComponent(label)
        return label
    }

    fun addStyledLabel(text: String?): StyledLabel {
        val label = StyledLabel(text)
        addComponent(label)
        return label
    }

    // FIXME: Refactor to add()?
    fun addComponent(component: Component): Component {
        // FIXME : Debug borders?
//        if (App.debugUIBorders >= 1) {
//            if (component instanceof JPanel)
//                ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
//            else if (App.debugUIBorders >= 2 && component instanceof JComponent) {
//                JPanel debugPanel = new JPanel(new BorderLayout());
//                debugPanel.add(component);
//                debugPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
//                debugPanel.add(component, BorderLayout.CENTER);
//                component = debugPanel;
//            }
//        }
        val prevFill = gc.fill
        val prevWeight = gc.weightx
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0
        val outerPanel = JPanel(BorderLayout())
        val innerPanel = JPanel(BorderLayout())
        outerPanel.add(Box.createHorizontalStrut(CONTENT_INSET), BorderLayout.WEST)
        outerPanel.add(innerPanel, BorderLayout.CENTER)
        innerPanel.add(component, BorderLayout.WEST)
        contentPanel.add(outerPanel, gc)
        gc.fill = prevFill
        gc.weightx = prevWeight
        gc.gridy++
        return outerPanel
    }

    fun addHTMLLabel(text: String): HTMLLabel {
        val label = HTMLLabel(text)
        add(label)
        return label
    }

    fun addFullWidthComponent(component: Component): Component {
        val prevFill = gc.fill
        val prevWeightX = gc.weightx
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0
        contentPanel.add(component, gc)
        gc.fill = prevFill
        gc.weightx = prevWeightX
        gc.gridy++
        return component
    }

//    fun addVerticalStrutTiny(): Component {
//        val strut = Box.createVerticalStrut(GUIReferences.TINY_INSET)
//        addComponent(strut)
//        return strut
//    }

    fun addVerticalStrut(): Component {
        val strut = Box.createVerticalStrut(GUIReferences.INSET)
        addComponent(strut)
        return strut
    }

    fun addVerticalStrutSmall(): Component {
        val strut = Box.createVerticalStrut(GUIReferences.SMALL_INSET)
        addComponent(strut)
        return strut
    }

    fun scrollToTop() {
        if (scrollPane == null) return
        scrollPane!!.viewport.viewPosition = Point(0, 0)
    }

    // Components should only be added using provided functions.
    // Override all add functions to warn about improper use.
    override fun add(comp: Component): Component {
        return addComponent(comp)
    }

    override fun add(comp: Component, constraints: Any) {
        incorrectAddMethod()
        super.add(comp, constraints)
    }

    override fun add(name: String, comp: Component): Component {
        incorrectAddMethod()
        return super.add(name, comp)
    }

    override fun add(comp: Component, index: Int): Component {
        incorrectAddMethod()
        return super.add(comp, index)
    }

    override fun add(comp: Component, constraints: Any, index: Int) {
        incorrectAddMethod()
        super.add(comp, constraints, index)
    }

    private fun incorrectAddMethod() {
        logger.error("[OptionPanel] Components should not be added directly to an OptionPanel! Use addHeader() or addComponent() instead.")
    }

    override fun removeAll() {
        contentPanel.removeAll()
        gc.gridx = 0
        gc.gridy = 0
    }

    companion object {
        private const val CONTENT_PANEL_INSET = 10
        private val logger: Logger = LoggerFactory.getLogger(OptionPanel::class.java.simpleName)
        private const val CONTENT_INSET = 4
    }
}
