
package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {
  @Test
  public void testByteParsing() {
    Assert.assertEquals(1024, new ByteSize("1KB").getBytes());
    Assert.assertEquals(1572864, new ByteSize("1.5MB").getBytes());
    Assert.assertEquals(1, new ByteSize("1b").getBytes());
    Assert.assertEquals(1073741824, new ByteSize("1GB").getBytes());
  }
}
