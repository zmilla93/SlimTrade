package github.zmilla93.core.ninja;

import github.zmilla93.core.ninja.responses.NinjaFragmentEntry;
import github.zmilla93.core.ninja.responses.NinjaSimpleEntry;

/**
 * Wrappers for poe.ninja api responses.
 */
public class NinjaResponse {

    // Note: This seems like an obvious spot for generics, but the gson parser requires a class type as parameter.

    public static class Simple {
        public NinjaSimpleEntry[] lines;
    }

    public static class Fragment {
        public NinjaFragmentEntry[] lines;
    }

}
