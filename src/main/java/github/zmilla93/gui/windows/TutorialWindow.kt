package github.zmilla93.gui.windows

import github.zmilla93.App
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ZUtil.addStrutsToBorderPanel
import github.zmilla93.gui.components.ImageLabel
import github.zmilla93.gui.components.TutorialPanel
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class TutorialWindow : CustomDialog("Tutorial"), IDefaultSizeAndLocation {
    private val cardLayout = CardLayout()
    private val cardPanel = JPanel(cardLayout)

    private val previousButton = JButton("Previous")
    private val nextButton = JButton("Next")
    private val pageLabel = JLabel("Page 1/1")

    private var activePanel = 1

    init {
        pinButton.setVisible(false)

        contentPanel.setLayout(BorderLayout())
        val borderPanel = JPanel(BorderLayout())
        addStrutsToBorderPanel(borderPanel, 10)

        // Tutorial Panels
        cardPanel.add(createFeatureOverviewPanel(), index())
        cardPanel.add(createTradeMessagePanel(), index())
        cardPanel.add(createStashHelperPanel(), index())
        cardPanel.add(createTradeHistoryPanel(), index())
        cardPanel.add(createChatScannerPanel(), index())
        cardPanel.add(createSearchPanel(), index())
        cardPanel.add(createGettingStartedPanel(), index())
        if (App.debugUIBorders >= 1) cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW))

        // Button Panel
        val pageLabelWrapper = JPanel(GridBagLayout())
        pageLabelWrapper.add(pageLabel)
        val buttonPanel = JPanel(BorderLayout())
        buttonPanel.add(previousButton, BorderLayout.WEST)
        buttonPanel.add(nextButton, BorderLayout.EAST)
        buttonPanel.add(pageLabelWrapper, BorderLayout.CENTER)

        // Content Panel
        borderPanel.add(cardPanel, BorderLayout.CENTER)
        contentPanel.add(borderPanel, BorderLayout.CENTER)
        contentPanel.add(buttonPanel, BorderLayout.SOUTH)

        pack()
        addListeners()
        updatePageLabel()
        updateButtons()
    }

    private fun addListeners() {
        previousButton.addActionListener(ActionListener { e: ActionEvent? ->
            if (activePanel <= 1) return@ActionListener
            activePanel--
            cardLayout.show(cardPanel, activePanel.toString())
            updatePageLabel()
            updateButtons()
        })
        nextButton.addActionListener(ActionListener { e: ActionEvent? ->
            if (activePanel >= cardPanel.componentCount) return@ActionListener
            activePanel++
            cardLayout.show(cardPanel, activePanel.toString())
            updatePageLabel()
            updateButtons()
        })
        // FIXME : Tutorial should probably reopen options, but only if called from the options
//        closeButton.addActionListener(ActionListener { e: ActionEvent? ->
//            FrameManager.optionsWindow.setVisible(true)
//            FrameManager.optionsWindow.toFront()
//        })
    }

    private fun index(): String {
        return (cardPanel.getComponentCount() + 1).toString()
    }

    private fun updatePageLabel() {
        pageLabel.setText("Page " + activePanel + "/" + cardPanel.getComponentCount())
    }

    private fun updateButtons() {
        previousButton.setEnabled(activePanel > 1)
        nextButton.setEnabled(activePanel < cardPanel.getComponentCount())
    }

    private fun showFirstPanel() {
        activePanel = 1
        cardLayout.show(cardPanel, "1")
        updatePageLabel()
        updateButtons()
    }

    /**//////////////////// */ //  Tutorial Panels  //
    /** //////////////////// */
    private fun createFeatureOverviewPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Feature Overview")
        panel.addLabel("- Streamlined trade UI")
        panel.addLabel("- Trade history")
        panel.addLabel("- Chat scanner")
        panel.addLabel("- Custom cheat sheet overlays")
        panel.addLabel("- In game searching (stash, vendors, skill tree, etc)")
        panel.addLabel("- Bulk trading support (Bulk Item Exchange > Filters > Group by Seller)")
        panel.addLabel("- Snap windows to monitor edge (SHIFT while moving a window)")
        panel.addLabel("- 20+ color themes")
        panel.addLabel("- Automated updates")
        panel.addLabel("- Incredibly customizable!").bold()
        return panel
    }

    private fun createTradeMessagePanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Trade Messages")
        panel.addLabel("Popups are created when a trade message is sent or received.")
        panel.addLabel("Response buttons are fully customizable. Color blind setting for alternate colors.")
        panel.addVerticalStrut()
        panel.addComponent(ImageLabel("/images/trade-messages.png", IMAGE_BORDER_SIZE, true), true)
        panel.addVerticalStrut()
        panel.addLabel("Quickly close many trades by right clicking the close button.")
        panel.addLabel("Works differently for incoming and outgoing, see macro customizers for details.")
        return panel
    }

    private fun createStashHelperPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Stash Helper")
        panel.addLabel("Incoming trades create an info window above your stash.")
        panel.addLabel("Hover to outline an item, left click to search the name, right click to close.")
        panel.addVerticalStrut()
        panel.addComponent(ImageLabel("/images/stash-helper.png", IMAGE_BORDER_SIZE), true)
        return panel
    }

    private fun createTradeHistoryPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Trade History")
        panel.addLabel("The history window can be used to reopen recent trade.")
        panel.addLabel("Built using the client file, so trades show up even if the app wasn't running.")
        panel.addVerticalStrut()
        panel.addComponent(ImageLabel("/images/history.png", IMAGE_BORDER_SIZE), true)
        return panel
    }

    private fun createChatScannerPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Chat Scanner")
        panel.addLabel("The chat scanner can be used to search for custom phrases in chat.")
        panel.addLabel("Create many presets with custom responses for each entry.")
        panel.addVerticalStrut()
        panel.addComponent(ImageLabel("/images/scanner-messages.png", IMAGE_BORDER_SIZE, true), true)
        return panel
    }

    private fun createSearchPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Searching")
        panel.addLabel("Paste search terms into any window with a search box.")
        panel.addLabel("Create multiple groups to keep things organized!")
        panel.addVerticalStrut()
        panel.addComponent(ImageLabel("/images/search.png", IMAGE_BORDER_SIZE), true)
        return panel
    }

    private fun createGettingStartedPanel(): JPanel {
        val panel = TutorialPanel()
        panel.addHeader("Getting Started")
        panel.addLabel("The trade UI will start working automatically.")
        panel.addLabel("Access additional features from the menubar in the top left, or from the system tray.")
        panel.addLabel("Close this window to get started!").bold()
        return panel
    }

    override fun applyDefaultSizeAndLocation() {
        setMinimumSize(null)
        pack()
        setMinimumSize(getSize())
        POEWindow.centerWindow(this)
    }

    override fun setVisible(visible: Boolean) {
        val wasVisible = isVisible
        super.setVisible(visible)
        showFirstPanel()
//        if (!visible && wasVisible) LaunchPopupManager.tryShowNextPopup()
    }

    companion object {
        private const val IMAGE_BORDER_SIZE = 1

        // Increment version number to show tutorial on launch
        const val TUTORIAL_VERSION: Int = 1
    }
}
