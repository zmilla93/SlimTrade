package github.zmilla93.modules.filetailing;

public interface FileTailerListener {

    void init(FileTailer tailer);

    void fileNotFound();

    void fileRotated(); // Not implemented yet

    void onLoad();

    void handle(String line);

}
