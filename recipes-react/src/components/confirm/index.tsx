import React, {ReactNode} from 'react'
import Dialog from '@/components/dialog'
import Button, {ButtonType} from '@/components/button'

interface ConfirmProps {
  open: boolean
  title: string
  onSubmit: () => void
  onCancel: () => void
  children: ReactNode
}

export default function Confirm({
  children,
  onSubmit,
  onCancel,
  ...restProps
}: ConfirmProps) {
  return (
    <Dialog
      {...restProps}
      onCancel={onCancel}
      buttons={
        <>
          <Button onClick={onCancel} type={ButtonType.secondary}>
            Cancel
          </Button>
          {/* TODO add padding in dialog */}
          <div className="w-2"></div>
          <Button onClick={onSubmit}>OK</Button>
        </>
      }
    >
      <span className="overflow-ellipsis break-words">{children}</span>
    </Dialog>
  )
}
