package io.github.terslenk.azap.utils;

import io.github.terslenk.azap.Azap;

public class Setup {
    public static void setup(Azap azap) {
        AddonItems.setup(azap);
        Category.setup(azap);
    }
}
