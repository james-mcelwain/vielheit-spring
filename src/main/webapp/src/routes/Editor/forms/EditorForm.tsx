import * as React from 'react'
import SyntheticEvent = React.SyntheticEvent
import {store} from '../../../main'

export default class EditorForm<P, S> extends React.Component<any, any> {
  public handleSubmit<T extends { user: { userId: number } }>(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, resource: T) => {
      if (!err) {
        const user = store.getState().user
        if (user) {
          resource.user = { userId: user.id }
          this.props.submit(resource)
        }
      }
    })
  }
}
