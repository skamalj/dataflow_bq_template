## Example to create Dataflow Tenplate writing to BQ

Referred from [GCP Documentation](https://cloud.google.com/dataflow/docs/guides/templates/creating-templates)
```
mvn compile exec:java  -Dexec.mainClass=com.example.App \
        -Dexec.args="--runner=DataflowRunner \
        --project=gcdeveloper \
        --stagingLocation=<Staging Bucket Location> \
        --templateLocation=<Bucket location for template> \
        --region=asia-south-1" -e 
```