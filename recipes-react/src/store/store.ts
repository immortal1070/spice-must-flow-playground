import {configureStore} from '@reduxjs/toolkit'
import {recipesServiceApi} from '@/client/recipesServiceApi'

export const store = configureStore({
  reducer: {
    [recipesServiceApi.reducerPath]: recipesServiceApi.reducer
  },
  middleware: (getDefaultMiddleware) => {
    return getDefaultMiddleware().concat(recipesServiceApi.middleware)
  }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
