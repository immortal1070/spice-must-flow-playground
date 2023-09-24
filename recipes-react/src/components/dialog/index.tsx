import React, {ReactNode, useCallback, useEffect, useRef} from 'react'

export enum DialogStyle {
  small,
  big
}

export interface DialogProps {
  open: boolean
  title: string
  onCancel: () => void
  children: ReactNode
  buttons: ReactNode
  style?: DialogStyle
}

export default function Dialog({
  open,
  title,
  onCancel,
  children,
  buttons,
  style = DialogStyle.big
}: DialogProps) {
  const dialogRef = useRef<HTMLDialogElement>(null)

  useEffect(() => {
    if (open) {
      dialogRef.current?.showModal && dialogRef.current.showModal()
    } else {
      dialogRef.current?.close && dialogRef.current.close()
    }
  }, [open])

  const Hr = useCallback(() => <hr className="border-gray-500" />, [])

  let sizeStyle: string
  switch (style) {
    case DialogStyle.small:
      sizeStyle = 'max-w-xl'
      break
    case DialogStyle.big:
      sizeStyle = 'max-w-3xl'
      break
  }

  return (
    <dialog
      onCancel={onCancel}
      ref={dialogRef}
      className={`backdrop:backdrop-blur-sm ${sizeStyle} border bg-inherit text-current shadow-gray-800 text-center overflow-ellipsis overflow-hidden`}
    >
      <div className="m-2 flex flex-col text-start">
        <h2 className="text-xl we my-4 ml-4 font-semibold">{title}</h2>
        <Hr />
        <div className="m-4">{children}</div>
        <Hr />
        <div className="flex justify-end mt-2 mr-2">{buttons}</div>
      </div>
    </dialog>
  )
}
