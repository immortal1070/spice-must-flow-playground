import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'

export const recipesServiceApi = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: '/',
    prepareHeaders: (headers) => {
      headers.set('Content-Type', 'application/json')
      return headers
    }
  }),
  endpoints: () => ({})
})
