# csv-processor
# sample usage
### genrate both json and xml
```
java -jar build/libs/csv-processor-3.0.jar -srcDir=src/test/resources -targetDir=build/output
```

### genrate json
```
java -jar build/libs/csv-processor-3.0.jar -srcDir=src/test/resources -targetDir=build/output -mode=json
```

### genrate xml
```
java -jar build/libs/csv-processor-3.0.jar -srcDir=src/test/resources -targetDir=build/output -mode=xml
```