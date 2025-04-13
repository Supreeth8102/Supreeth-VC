
package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {
  @Test
  public void testTimeParsing() {
    Assert.assertEquals(150, new TimeDuration("150ms").getMilliseconds());
    Assert.assertEquals(2000, new TimeDuration("2s").getMilliseconds());
    Assert.assertEquals(120000, new TimeDuration("2m").getMilliseconds());
    Assert.assertEquals(7200000, new TimeDuration("2h").getMilliseconds());
  }
}
