package com.example;

import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.arrow.flatbuf.Int;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

import com.google.api.services.bigquery.model.TableSchema;
import com.google.api.services.bigquery.model.TableFieldSchema;
import java.util.Arrays;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.WriteDisposition;
import com.google.api.services.bigquery.model.TableRow;


class MySumFn extends DoFn<Integer, Integer> {
    ValueProvider<Integer> mySumInteger;

    MySumFn(ValueProvider<Integer> sumInt) {
        // Store the value provider
        this.mySumInteger = sumInt;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
       // Get the value of the value provider and add it to
       // the element's value.
       c.output(c.element() + mySumInteger.get());
    }
}

public class App {
public static void main(String[] args) {
  SumIntOptions options =
        PipelineOptionsFactory.fromArgs(args).withValidation()
          .as(SumIntOptions.class);

  Pipeline p = Pipeline.create(options);

  TableSchema schema =
        new TableSchema()
            .setFields(
                Arrays.asList(
                    new TableFieldSchema()
                        .setName("i")
                        .setType("INTEGER")
                        .setMode("REQUIRED")));

  p.apply(Create.of(1, 2, 3))
    // Get the value provider and pass it to MySumFn
   .apply(ParDo.of(new MySumFn(options.getInt())))
   .apply("Write to BigQuery", BigQueryIO.<Integer>write()
        .to(String.format("%s:%s.%s", "gcdeveloper", "mydataset", "numbers"))
        .withSchema(schema)
        .withFormatFunction(
          (Integer elem) -> new TableRow().set("i",elem))
        .withCreateDisposition(CreateDisposition.CREATE_NEVER)
        .withWriteDisposition(WriteDisposition.WRITE_APPEND));
  p.run();
}
}