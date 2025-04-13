
package io.cdap.wrangler.steps.aggregate;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.Row;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
  @Test
  public void testAggregation() throws Exception {
    List<Row> rows = Arrays.asList(
      new Row("data_transfer_size", "1MB").add("response_time", "1s"),
      new Row("data_transfer_size", "2MB").add("response_time", "500ms")
    );

    String[] recipe = new String[] {
      "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
    };

    List<Row> result = TestingRig.execute(recipe, rows);

    Assert.assertEquals(1, result.size());
    Row agg = result.get(0);

    Assert.assertEquals(3.0, (Double) agg.getValue("total_size_mb"), 0.001);
    Assert.assertEquals(1.5, (Double) agg.getValue("total_time_sec"), 0.001);
  }
}
