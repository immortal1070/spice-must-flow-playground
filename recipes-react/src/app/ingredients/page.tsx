'use client'
import React, {Suspense} from 'react'
import Button from '@/components/button'
import useIngredientsPage, {PageActions} from '@/app/ingredients/usePage'
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
      <main className="flex min-h-screen flex-col items-stretch justify-between p-2">
        <div className="z-10 w-full items-center justify-between font-mono text-sm lg:flex">
          <p className="fixed left-0 top-0 flex w-full justify-center from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30">
            Ingredients
          </p>
          <div className="fixed bottom-0 left-0 flex h-48 w-full items-end justify-center bg-gradient-to-t from-white via-white dark:from-black dark:via-black lg:static lg:h-auto lg:w-auto lg:bg-none">
            <div className="inline-block bg-secondary-500 m-2 rounded-full">
              <p className="text-white table-cell align-middle text-center h-10 w-10 p-2">
                Sergei
              </p>
            </div>
          </div>
        </div>
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
      </main>
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
