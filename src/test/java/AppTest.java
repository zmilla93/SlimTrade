import org.jnativehook.keyboard.NativeKeyEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void basicTest() {
        // Ensure the default key value for enter didn't change
        assertEquals(28, NativeKeyEvent.VC_ENTER);
    }

}
