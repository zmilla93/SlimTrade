package com.slimtrade.core.ninja;

import com.slimtrade.core.ninja.responses.NinjaFragmentEntry;
import com.slimtrade.core.ninja.responses.NinjaSimpleEntry;

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
