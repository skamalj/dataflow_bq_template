## Example to create Dataflow Template writing to BQ

Referred from [GCP Documentation](https://cloud.google.com/dataflow/docs/guides/templates/creating-templates)

` Before executing below command update your project in App.java for BigqueryIO and create dataset and table with column "i" as integer (single column)
`

```
mvn compile exec:java  -Dexec.mainClass=com.example.App \
        -Dexec.args="--runner=DataflowRunner \
        --project=<your project> \
        --stagingLocation=<Staging Bucket Location> \
        --templateLocation=<Bucket location for template> \
        --region=asia-south-1" -e 
```
