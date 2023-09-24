One can generate clients using https://openapi-generator.tech/ 

Note - left here for demo reasons, in this project only RTK query generator is used

```cli
npx @openapitools/openapi-generator-cli generate -c ./openapi/config.yaml -g typescript-fetch -i ./openapi/recipes-openapi.json -o ./openapi/output
```
