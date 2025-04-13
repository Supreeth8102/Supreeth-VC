
package io.cdap.wrangler.steps.aggregate;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.annotations.Public;
import io.cdap.wrangler.api.executor.ExecutorContext;
import io.cdap.wrangler.api.executor.Store;
import io.cdap.wrangler.api.directive.Directive;
import io.cdap.wrangler.api.directive.DirectiveContext;
import io.cdap.wrangler.api.parser.Text;

import java.util.List;
import java.util.ArrayList;

@Public
public class AggregateStats implements Directive {
  private String sizeSource;
  private String timeSource;
  private String sizeTarget;
  private String timeTarget;
  private Store store;

  @Override
  public UsageDefinition define() {
    return UsageDefinition.builder("aggregate-stats")
      .addRequiredArg("sizeSource", TokenType.COLUMN_NAME)
      .addRequiredArg("timeSource", TokenType.COLUMN_NAME)
      .addRequiredArg("sizeTarget", TokenType.TEXT)
      .addRequiredArg("timeTarget", TokenType.TEXT)
      .build();
  }

  @Override
  public void initialize(DirectiveContext context, List<Argument> args) throws DirectiveParseException {
    sizeSource = ((ColumnName) args.get(0)).value();
    timeSource = ((ColumnName) args.get(1)).value();
    sizeTarget = ((Text) args.get(2)).value();
    timeTarget = ((Text) args.get(3)).value();
    store = context.getExecutorContext().getStore("aggregate-stats");
  }

  @Override
  public List<Row> execute(Row row, ExecutorContext context) throws DirectiveExecutionException {
    Object sizeVal = row.getValue(sizeSource);
    Object timeVal = row.getValue(timeSource);

    long bytes = parseByteValue(sizeVal);
    long millis = parseTimeValue(timeVal);

    store.add(sizeTarget, bytes);
    store.add(timeTarget, millis);

    return new ArrayList<>();
  }

  @Override
  public List<Row> finalize(ExecutorContext context) {
    long totalBytes = store.get(sizeTarget, 0L);
    long totalMillis = store.get(timeTarget, 0L);

    Row output = new Row();
    output.add(sizeTarget, totalBytes / (1024.0 * 1024));
    output.add(timeTarget, totalMillis / 1000.0);

    List<Row> results = new ArrayList<>();
    results.add(output);
    return results;
  }

  private long parseByteValue(Object value) {
    if (value instanceof String) {
      return new io.cdap.wrangler.api.parser.ByteSize((String) value).getBytes();
    }
    return Long.parseLong(value.toString());
  }

  private long parseTimeValue(Object value) {
    if (value instanceof String) {
      return new io.cdap.wrangler.api.parser.TimeDuration((String) value).getMilliseconds();
    }
    return Long.parseLong(value.toString());
  }
}
