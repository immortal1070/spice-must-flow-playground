import {recipesServiceApi as api} from './recipesServiceApi'

const injectedRtkApi = api.injectEndpoints({
  endpoints: (build) => ({
    getRecipeById: build.query<GetRecipeByIdApiResponse, GetRecipeByIdApiArg>({
      query: (queryArg) => ({url: `/v1/recipes/${queryArg.id}`})
    }),
    updateRecipe: build.mutation<UpdateRecipeApiResponse, UpdateRecipeApiArg>({
      query: (queryArg) => ({
        url: `/v1/recipes/${queryArg.id}`,
        method: 'PUT',
        body: queryArg.recipeRestSaveCommand
      })
    }),
    deleteRecipeById: build.mutation<
      DeleteRecipeByIdApiResponse,
      DeleteRecipeByIdApiArg
    >({
      query: (queryArg) => ({
        url: `/v1/recipes/${queryArg.id}`,
        method: 'DELETE'
      })
    }),
    findRecipes: build.query<FindRecipesApiResponse, FindRecipesApiArg>({
      query: (queryArg) => ({
        url: `/v1/recipes`,
        params: {
          ids: queryArg.ids,
          names: queryArg.names,
          content: queryArg.content,
          cookingMinutes: queryArg.cookingMinutes,
          ingredientNames: queryArg.ingredientNames
        }
      })
    }),
    createRecipe: build.mutation<CreateRecipeApiResponse, CreateRecipeApiArg>({
      query: (queryArg) => ({
        url: `/v1/recipes`,
        method: 'POST',
        body: queryArg.recipeRestSaveCommand
      })
    }),
    deleteRecipes: build.mutation<
      DeleteRecipesApiResponse,
      DeleteRecipesApiArg
    >({
      query: (queryArg) => ({
        url: `/v1/recipes`,
        method: 'DELETE',
        params: {ids: queryArg.ids}
      })
    })
  }),
  overrideExisting: false
})
export {injectedRtkApi as recipesApi}
export type GetRecipeByIdApiResponse = /** status 200 OK */ RecipeDto
export type GetRecipeByIdApiArg = {
  id: string
}
export type UpdateRecipeApiResponse = unknown
export type UpdateRecipeApiArg = {
  id: string
  recipeRestSaveCommand: RecipeRestSaveCommand
}
export type DeleteRecipeByIdApiResponse = unknown
export type DeleteRecipeByIdApiArg = {
  id: string
}
export type FindRecipesApiResponse = /** status 200 OK */ RecipeDto[]
export type FindRecipesApiArg = {
  ids?: string[]
  names?: string[]
  content?: string[]
  cookingMinutes?: number[]
  ingredientNames?: string[]
}
export type CreateRecipeApiResponse = /** status 201 Created */ string
export type CreateRecipeApiArg = {
  recipeRestSaveCommand: RecipeRestSaveCommand
}
export type DeleteRecipesApiResponse = unknown
export type DeleteRecipesApiArg = {
  ids: string[]
}
export type RecipeIngredientDto = {
  ingredientId: string
  amount: number
}
export type RecipeDto = {
  id: string
  name: string
  content: string
  cookingMinutes: number
  ingredients: RecipeIngredientDto[]
}
export type RecipeRestSaveCommand = {
  name: string
  content: string
  cookingMinutes: number
  ingredients: RecipeIngredientDto[]
}
export const {
  useGetRecipeByIdQuery,
  useUpdateRecipeMutation,
  useDeleteRecipeByIdMutation,
  useFindRecipesQuery,
  useCreateRecipeMutation,
  useDeleteRecipesMutation
} = injectedRtkApi
