/** @type {import("@rtk-query/codegen-openapi").ConfigFile} */

const config = {
  schemaFile: 'http://localhost:8080/v3/api-docs',
  apiFile: './emptyApi.ts',
  apiImport: 'emptySplitApi',
  outputFile: './output/recipesApi.ts',
  exportName: 'recipesApi',
  hooks: true,
  tag: true,
  outputFiles: {
    './output/recipesApi.ts': {
      filterEndpoints: (_, {path}) => path.includes('/recipes')
    },
    './output/ingredientsApi.ts': {
      filterEndpoints: (_, {path}) => path.includes('/ingredients')
    }
  }
}

module.exports = config
