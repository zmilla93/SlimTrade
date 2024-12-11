package github.zmilla93.modules.updater;

public enum UpdateAction {

    NONE, DOWNLOAD, PATCH, CLEAN;

    private final String name;

    UpdateAction() {
        name = name().toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }

}
