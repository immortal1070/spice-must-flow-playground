import React, {useEffect, useState} from 'react'

import {
  IngredientDto,
  IngredientRestSaveCommand,
  useCreateIngredientMutation,
  useDeleteIngredientByIdMutation,
  useFindIngredientsQuery,
  useUpdateIngredientMutation
} from '@/client/ingredientsApi'
import {IngredientFormData} from '@/app/ingredients/ingredientModal'

export enum PageActions {
  view,
  create,
  update,
  delete
}

const useIngredientsPage = () => {
  // page current state
  const [currentAction, setCurrentAction] = useState<PageActions>(
    PageActions.view
  )

  const [currentIngredient, setCurrentIngredient] = useState<
    IngredientDto | undefined
  >(undefined)

  //hooks divided by functionality
  return {
    currentIngredient,
    currentAction,
    ...useSearch(),
    ...useCreate(setCurrentAction),
    ...useUpdate(setCurrentAction, setCurrentIngredient, currentIngredient),
    ...useDelete(setCurrentAction, setCurrentIngredient, currentIngredient)
  }
}

export const useCreate = (setCurrentAction: (action: PageActions) => void) => {
  const [createIngredient] = useCreateIngredientMutation()

  const createOnClick = () => {
    setCurrentAction(PageActions.create)
  }

  const createSubmit = (formData: IngredientFormData) => {
    const ingredientRestSaveCommand = formToRestDto(formData)
    createIngredient({ingredientRestSaveCommand})
    setCurrentAction(PageActions.view)
  }

  const createCancel = () => {
    setCurrentAction(PageActions.view)
  }

  return {
    createOnClick,
    createSubmit,
    createCancel
  }
}

export const useUpdate = (
  setCurrentAction: (action: PageActions) => void,
  setCurrentIngredient: (ingredient: IngredientDto | undefined) => void,
  currentIngredient: IngredientDto | undefined
) => {
  const [updateIngredient] = useUpdateIngredientMutation()

  const updateOnClick = (ingredient: IngredientDto) => {
    setCurrentIngredient(ingredient)
    setCurrentAction(PageActions.update)
  }

  const updateSubmit = (formData: IngredientFormData) => {
    if (currentIngredient) {
      const ingredientRestSaveCommand = formToRestDto(formData)
      updateIngredient({
        id: currentIngredient.id,
        ingredientRestSaveCommand
      })
      setCurrentAction(PageActions.view)
    }
  }

  const updateCancel = () => {
    setCurrentIngredient(undefined)
    setCurrentAction(PageActions.view)
  }

  return {
    updateOnClick,
    updateSubmit,
    updateCancel
  }
}

export const useDelete = (
  setCurrentAction: (action: PageActions) => void,
  setCurrentIngredient: (ingredient: IngredientDto | undefined) => void,
  currentIngredient: IngredientDto | undefined
) => {
  // Delete
  const [
    deleteIngredient, // This is the mutation trigger
    {isLoading: isUpdating} // This is the destructured mutation result TODO add loading logic
  ] = useDeleteIngredientByIdMutation()

  const deleteOnClick = (ingredient: IngredientDto) => {
    setCurrentIngredient(ingredient)
    setCurrentAction(PageActions.delete)
  }

  const onDeleteConfirm = () => {
    if (currentIngredient) {
      deleteIngredient({id: currentIngredient.id})
    }
    setCurrentAction(PageActions.view)
  }

  const onDeleteCancel = () => {
    setCurrentAction(PageActions.view)
  }

  return {
    deleteOnClick,
    onDeleteConfirm,
    onDeleteCancel
  }
}

export const useSearch = () => {
  // Search
  const [searchString, setSearchString] = useState('')
  const [dataLoading, setDataLoading] = useState(true)

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) =>
    setSearchString(event.target.value)

  const {
    data: ingredients,
    isUninitialized,
    isFetching,
    isLoading
  } = useFindIngredientsQuery({
    ...(searchString ? {names: [searchString]} : {})
  })

  useEffect(() => {
    setDataLoading(isUninitialized || isLoading)
  }, [isUninitialized, isLoading])

  return {
    ingredients,
    searchString,
    onSearchChange,
    dataLoading
  }
}

const formToRestDto = (
  formData: IngredientFormData
): IngredientRestSaveCommand => {
  if (!formData.name) {
    throw new Error('name must be present!')
  }
  return {
    name: formData.name
  }
}

export default useIngredientsPage
