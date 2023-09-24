import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query'

export const recipesServiceApi = createApi({
  baseQuery: fetchBaseQuery({baseUrl: '/'}),
  endpoints: () => ({})
})
