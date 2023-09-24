'use client'
import React, {Suspense} from 'react'
import Button from '@/components/button'
import useIngredientsPage, {
  PageActions
} from '@/app/ingredients/useIngredientsPage'
import IngredientDeleteConfirm from '@/app/ingredients/ingredientDeleteConfirm'
import IngredientModal from '@/app/ingredients/ingredientModal'
import IngredientsTableBody from '@/app/ingredients/ingredientsTableBody'
import Loading from '@/components/loading'

export default function Ingredients() {
  const {
    currentIngredient,
    currentAction,
    ingredients,
    searchString,
    onSearchChange,
    dataLoading,
    createOnClick,
    createSubmit,
    createCancel,
    updateOnClick,
    updateSubmit,
    updateCancel,
    deleteOnClick,
    onDeleteConfirm,
    onDeleteCancel
  } = useIngredientsPage()

  return (
    <>
      <div className="flex grow flex-col border-solid border-white border-2 rounded p-2 m-2">
        {/*table navigation:*/}
        <div className="flex justify-between items-center p-2">
          <input
            className="bg-transparent border-gray-500 border-2 p-1"
            name="ingredientsSearch"
            placeholder="Type to search..."
            onChange={onSearchChange}
            value={searchString}
          />
          <Button onClick={createOnClick}>Create</Button>
        </div>
        <hr className="my-2" />
        <div className="flex justify-center grow items-start relative">
          {dataLoading && <Loading />}
          {/*<Loading />*/}
          <table className="table-fixed grow border-separate border-spacing-4">
            <thead>
              <tr>
                <th className="text-left">Name</th>
                <th className="text-left">Id</th>
              </tr>
            </thead>
            <Suspense fallback={<Loading />}>
              <IngredientsTableBody
                ingredients={ingredients}
                updateOnClick={updateOnClick}
                deleteOnClick={deleteOnClick}
              />
            </Suspense>
          </table>
        </div>
      </div>
      <IngredientDeleteConfirm
        open={currentAction == PageActions.delete}
        onOk={onDeleteConfirm}
        onCancel={onDeleteCancel}
        ingredient={currentIngredient}
      />
      <IngredientModal
        title={'Create ingredient'}
        open={currentAction == PageActions.create}
        ingredientFormId="ingredientCreateForm"
        onSubmit={createSubmit}
        onCancel={createCancel}
      />
      <IngredientModal
        title={'Update ingredient'}
        currentIngredient={currentIngredient}
        open={currentAction == PageActions.update}
        ingredientFormId="ingredientUpdateForm"
        onSubmit={updateSubmit}
        onCancel={updateCancel}
      />
    </>
  )
}
