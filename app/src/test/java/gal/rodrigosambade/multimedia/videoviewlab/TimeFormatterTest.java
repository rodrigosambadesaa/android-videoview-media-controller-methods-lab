package gal.rodrigosambade.multimedia.videoviewlab;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TimeFormatterTest {
    @Test public void formatsMilliseconds() {
        assertEquals("00:00", TimeFormatter.format(0));
        assertEquals("01:05", TimeFormatter.format(65_500));
    }
}
