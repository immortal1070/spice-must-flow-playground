import {recipesServiceApi as api} from './recipesServiceApi'
import {TagDescription} from '@reduxjs/toolkit/src/query/endpointDefinitions'

const ingredientsTag: string = 'Ingredients'

const listTagDescription: TagDescription<string> = {
  type: ingredientsTag,
  id: 'List'
}

const injectedRtkApi = api
  .enhanceEndpoints({
    addTagTypes: [ingredientsTag]
  })
  .injectEndpoints({
    endpoints: (build) => ({
      getIngredientById: build.query<
        GetIngredientByIdApiResponse,
        GetIngredientByIdApiArg
      >({
        query: (queryArg) => ({url: `/v1/ingredients/${queryArg.id}`}),
        providesTags: (ingredient) =>
          ingredient
            ? [
                {
                  type: ingredientsTag,
                  id: ingredient.id
                }
              ]
            : []
      }),
      updateIngredient: build.mutation<
        UpdateIngredientApiResponse,
        UpdateIngredientApiArg
      >({
        query: (queryArg) => ({
          url: `/v1/ingredients/${queryArg.id}`,
          method: 'PUT',
          body: queryArg.ingredientRestSaveCommand
        }),
        invalidatesTags: (result, error, {id}) => [{type: ingredientsTag, id}]
      }),
      deleteIngredientById: build.mutation<
        DeleteIngredientByIdApiResponse,
        DeleteIngredientByIdApiArg
      >({
        query: (queryArg) => ({
          url: `/v1/ingredients/${queryArg.id}`,
          method: 'DELETE'
        }),
        invalidatesTags: (result, error, {id}) => [{type: ingredientsTag, id}]
      }),
      findIngredients: build.query<
        FindIngredientsApiResponse,
        FindIngredientsApiArg
      >({
        query: (queryArg) => ({
          url: `/v1/ingredients`,
          params: (queryArg && {ids: queryArg.ids, names: queryArg.names}) || {}
        }),
        providesTags: (result) =>
          result
            ? [
                ...result.map((ingredient: IngredientDto) => ({
                  type: ingredientsTag,
                  id: ingredient.id
                })),
                listTagDescription
              ]
            : // an error occurred, but we still want to refetch this query
              [listTagDescription]
      }),
      createIngredient: build.mutation<
        CreateIngredientApiResponse,
        CreateIngredientApiArg
      >({
        query: (queryArg) => ({
          url: `/v1/ingredients`,
          method: 'POST',
          body: queryArg.ingredientRestSaveCommand
        }),
        invalidatesTags: [listTagDescription]
      }),
      deleteIngredients: build.mutation<
        DeleteIngredientsApiResponse,
        DeleteIngredientsApiArg
      >({
        query: (queryArg) => ({
          url: `/v1/ingredients`,
          method: 'DELETE',
          params: {ids: queryArg.ids}
        }),
        invalidatesTags: (result, error, deleteArgs) =>
          deleteArgs.ids.map((id) => {
            return {
              type: ingredientsTag,
              id
            }
          })
      })
    }),
    overrideExisting: false
  })
export {injectedRtkApi as recipesApi}
export type GetIngredientByIdApiResponse = /** status 200 OK */ IngredientDto
export type GetIngredientByIdApiArg = {
  id: string
}
export type UpdateIngredientApiResponse = unknown
export type UpdateIngredientApiArg = {
  id: string
  ingredientRestSaveCommand: IngredientRestSaveCommand
}
export type DeleteIngredientByIdApiResponse = unknown
export type DeleteIngredientByIdApiArg = {
  id: string
}
export type FindIngredientsApiResponse = /** status 200 OK */ IngredientDto[]
export type FindIngredientsApiArg = {
  ids?: string[]
  names?: string[]
}
export type CreateIngredientApiResponse = /** status 201 Created */ string
export type CreateIngredientApiArg = {
  ingredientRestSaveCommand: IngredientRestSaveCommand
}
export type DeleteIngredientsApiResponse = unknown
export type DeleteIngredientsApiArg = {
  ids: string[]
}
export type IngredientDto = {
  id: string
  name: string
}
export type IngredientRestSaveCommand = {
  name: string
}
export const {
  useGetIngredientByIdQuery,
  useUpdateIngredientMutation,
  useDeleteIngredientByIdMutation,
  useFindIngredientsQuery,
  useCreateIngredientMutation,
  useDeleteIngredientsMutation
} = injectedRtkApi
