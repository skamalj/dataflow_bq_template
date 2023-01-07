## Example to create Dataflow Template writing to BQ

Referred from [GCP Documentation](https://cloud.google.com/dataflow/docs/guides/templates/creating-templates)

` Before executing below command update your project in App.java for BigqueryIO and create dataset and table (mydataset.numbers) with column "i" as integer (single column)
`

```
mvn compile exec:java  -Dexec.mainClass=com.example.App \
        -Dexec.args="--runner=DataflowRunner \
        --project=<your project> \
        --stagingLocation=<Staging Bucket Location> \
        --templateLocation=<Bucket location for template> \
        --region=asia-south-1" -e 
```

## Run template
To run the template , select custom template from dataflow console. Then add "int" parameter in "Additional parameters". Any number you provide will be added to 1,2,3 and result will be created in BQ table as 3 rows. Ex. if int is 5, BQ will have 6,7,8. This template is appends only.
