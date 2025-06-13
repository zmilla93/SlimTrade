package github.zmilla93.gui.windows

import github.zmilla93.App
import github.zmilla93.core.managers.HotkeyManager
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.Platform
import github.zmilla93.gui.components.StyledLabel
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.gui.managers.LaunchPopupManager.donationPopup
import github.zmilla93.gui.options.*
import github.zmilla93.gui.options.display.DisplayOptionPanel
import github.zmilla93.gui.options.poe.PathOfExileOptionPanel
import github.zmilla93.gui.options.searching.StashSearchOptionPanel
import github.zmilla93.gui.options.stash.StashOptionPanel
import github.zmilla93.modules.saving.ISaveListener
import github.zmilla93.modules.updater.ZLogger
import java.awt.*
import java.util.*
import javax.swing.Box
import javax.swing.JButton
import javax.swing.JList
import javax.swing.JPanel

class OptionsWindow : CustomDialog("Options"), ISaveListener, IDefaultSizeAndLocation {
    private val cardLayout = CardLayout()
    private val cardPanel = JPanel(cardLayout)

    private val incomingMacroPanel: AbstractMacroOptionPanel
    private val outgoingMacroPanel: AbstractMacroOptionPanel

    @JvmField
    val ignorePanel: IgnoreItemOptionPanel = IgnoreItemOptionPanel()

    @JvmField
    val hotkeyPanel: HotkeyOptionPanel = HotkeyOptionPanel()
    private val donationPanel = OptionListPanel("Donate", DonationPanel())
    private val optionsList: JList<OptionListPanel?>

    private val donateButton = JButton("Donate")
    private val updateButton = JButton("Install Update")
    private val saveButton = JButton("Save")
    private val revertButton = JButton("Revert Changes")

    //    public static final String debugPanel = "Stash Tabs";
    init {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE)
        incomingMacroPanel = IncomingMacroPanel()
        outgoingMacroPanel = OutgoingMacroPanel()

        // Panels
        val general = OptionListPanel("General", GeneralOptionPanel())
        val display = OptionListPanel("Display", DisplayOptionPanel())
        val audio = OptionListPanel("Audio", AudioOptionPanel())
        val stash = OptionListPanel("Stash Tabs", StashOptionPanel())
        val pathOfExile = OptionListPanel("Path of Exile", PathOfExileOptionPanel())
        val linux = OptionListPanel("Linux", LinuxOptionsPanel())
        val information = OptionListPanel("Information", InformationOptionPanel())
        val incomingMacros = OptionListPanel("Incoming Macros", incomingMacroPanel)
        val outgoingMacros = OptionListPanel("Outgoing Macros", outgoingMacroPanel)
        val hotkeys = OptionListPanel("Hotkeys", hotkeyPanel)
        val ignoreItems = OptionListPanel("Ignore Items", ignorePanel)
        val cheatSheets = OptionListPanel("Cheat Sheets", CheatSheetsOptionPanel())
        val stashSearch = OptionListPanel("POE Searching", StashSearchOptionPanel())
        val kalguurHelper = OptionListPanel("Kalguur Helper", KalguurOptionPanel())
        val debug = OptionListPanel("Debug", DebugOptionPanel())
        val linuxOrNull = if (Platform.current == Platform.LINUX) linux else null
        var panelList = arrayOf(
            general, display, audio, hotkeys, pathOfExile, linuxOrNull,
            OptionListPanel("Trading"),
            incomingMacros, outgoingMacros, stash, ignoreItems,
            OptionListPanel("Tools"),
            cheatSheets, stashSearch,
            OptionListPanel(),
            information, donationPanel
        )
        if (App.debug) {
            val newList = arrayOfNulls<OptionListPanel>(panelList.size + 1)
            System.arraycopy(panelList, 0, newList, 0, panelList.size)
            newList[newList.size - 1] = debug
            panelList = newList
        }
        optionsList = JList<OptionListPanel?>(panelList)
        optionsList.setCellRenderer(OptionListPanelCellRenderer())
        optionsList.setSelectionModel(OptionListSelectionModel(panelList))
        val sidebar = createSidebar()

        // Save & Revert Panel
        val saveRevertPanel = JPanel(BorderLayout())
        val saveRevertInnerPanel = JPanel(GridBagLayout())
        var gc = GridBagConstraints()
        gc.anchor = GridBagConstraints.EAST
        gc.weightx = 0.0
        gc.fill = GridBagConstraints.NONE
        gc.gridx = 0
        gc.insets = Insets(5, 0, 5, 20)
        saveRevertInnerPanel.add(revertButton, gc)
        gc.gridx++
        saveRevertInnerPanel.add(saveButton, gc)
        saveRevertPanel.add(saveRevertInnerPanel, BorderLayout.EAST)
        val displayPanel = JPanel(GridBagLayout())
        gc = GridBagConstraints()
        gc.insets = Insets(1, 1, 1, 1)

        for (panel in panelList) {
            if (panel == null || panel.isSeparator) continue
            cardPanel.add(panel.panel, panel.title)
        }
        // FIXME : Temp border for donation panel
        cardPanel.add(donationPanel.panel, donationPanel.title)
        displayPanel.add(cardPanel, gc)
        contentPanel.setLayout(BorderLayout())
        contentPanel.add(sidebar, BorderLayout.WEST)
        contentPanel.add(saveRevertPanel, BorderLayout.SOUTH)
        contentPanel.add(cardPanel, BorderLayout.CENTER)

        // Finalize
        minimumSize = Dimension(500, 400)
        pack()
        SaveManager.settingsSaveFile.registerSavableContainer(this)
        SaveManager.settingsSaveFile.addListener(this)
        addListeners()
        showDebugPanel()
    }

    private fun addListeners() {
        saveButton.addActionListener {
            SaveManager.settingsSaveFile.saveToDisk()
            SaveManager.ignoreSaveFile.saveToDisk()
            if (Platform.current == Platform.LINUX) SaveManager.linuxSaveFile.saveToDisk()
            HotkeyManager.loadHotkeys()
            revalidate()
        }
        revertButton.addActionListener { SaveManager.settingsSaveFile.revertChanges() }
        donateButton.addActionListener { showDonationPanel() }
        updateButton.addActionListener { App.updateManager.runUpdateProcessFromSwing() }
        optionsList.addListSelectionListener { showPanel(optionsList.getSelectedValue()) }
    }

    private fun showDebugPanel() {
        if (App.debugOptionPanelName != null) {
            ZLogger.log("Setting debug option panel to '" + App.debugOptionPanelName + "'")
            val model = optionsList.getModel()
            for (i in 0..<model.size) {
                val panel = model.getElementAt(i)
                if (panel == null) continue
                val panelTitle = panel.title?.lowercase(Locale.getDefault())
                if (panelTitle != null && panelTitle == App.debugOptionPanelName) {
                    optionsList.setSelectedIndex(i)
                    break
                }
            }
        }
    }

    private fun createSidebar(): JPanel {
        val sidebar = JPanel(BorderLayout())
        // Top Button Panel
        val topButtonPanel = JPanel(BorderLayout())
        optionsList.setSelectedIndex(0)
        //        if (App.debug) optionsList.setSelectedIndex(panelList.length - 1);
        topButtonPanel.add(Box.createVerticalStrut(4), BorderLayout.NORTH)
        topButtonPanel.add(optionsList, BorderLayout.CENTER)

        // Bottom Button Panel
        val bottomButtonPanel = JPanel(GridBagLayout())
        val gc = GridBagConstraints()
        gc.gridx = 0
        gc.gridy = 0
        var appName = App.getAppInfo().fullName
        if (App.debug) appName += "-DEV"
        bottomButtonPanel.add(StyledLabel(appName).bold(), gc)
        gc.gridy++
        gc.weightx = 1.0
        gc.fill = GridBagConstraints.BOTH
        bottomButtonPanel.add(updateButton, gc)
        gc.gridy++
        bottomButtonPanel.add(donateButton, gc)
        gc.gridy++
        //        bottomButtonPanel.add(new JButton("Check for Updates"), gc);
        sidebar.add(topButtonPanel, BorderLayout.NORTH)
        sidebar.add(bottomButtonPanel, BorderLayout.SOUTH)
        updateButton.setVisible(false)

        return sidebar
    }

    private fun showPanel(panel: OptionListPanel?) {
        if (panel == null) return
        cardLayout.show(cardPanel, panel.title)
    }

    fun reloadExampleTrades() {
        incomingMacroPanel.reloadExampleTrade()
        outgoingMacroPanel.reloadExampleTrade()
    }

    fun showDonationPanel() {
        FrameManager.optionsWindow.setVisible(true)
        optionsList.clearSelection()
        optionsList.setSelectedValue(donationPanel, true)
        showPanel(donationPanel)
        (donationPanel.panel as DonationPanel).patreonButton.requestFocus()
        donationPopup.markAsSeen()
    }

    fun showUpdateButton() {
        updateButton.setVisible(true)
    }

    override fun onSave() {
        SaveManager.settingsSaveFile.data.buildMacroCache()
        reloadExampleTrades()
    }

    override fun applyDefaultSizeAndLocation() {
        setSize(1000, 650)
        POEWindow.centerWindow(this)
    }
}

