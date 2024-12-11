package github.zmilla93.gui.overlays;

public class MenubarOverlay extends AbstractOverlayFrame {

    private static final String TEXT = "Example Menubar";

    public MenubarOverlay() {
        super(new DummyMenubar(TEXT), TEXT);
    }

}
