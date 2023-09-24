import {render, screen} from '@testing-library/react'
import '@testing-library/jest-dom'
import React from 'react'
import Dialog from '../src/components/dialog'

describe('IngredientsModal', () => {
  it('renders a heading', () => {
    const mockChildMethod = jest.fn()
    jest.spyOn(React, 'useRef').mockReturnValue({
      current: {
        close: mockChildMethod,
        showModal: mockChildMethod
      }
    })

    jest.mock('react', () => {
      return {
        ...jest.requireActual('react'),
        useRef: () => {
          return {
            current: {close: mockChildMethod}
          }
        }
      }
    })

    render(
      <Dialog
        open={false}
        title={'123'}
        onCancel={() => {}}
        children={<></>}
        buttons={<></>}
      />
    )

    // expect(mockChildMethod).toBeCalled()

    const heading = screen.getByText('123')

    expect(heading).toBeInTheDocument()
  })
})
