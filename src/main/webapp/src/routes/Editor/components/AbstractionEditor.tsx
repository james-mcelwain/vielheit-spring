import * as React from 'react'
import {} from 'antd'
import AbstractionForm from './AbstractionForm'
import { AbstractionType } from '../../../domain/AbstractionType'
import { Abstraction } from '../modules/editor'

export interface AbstractionEditorProps {
  types: AbstractionType[]
}

export interface AbstractionEditorState {

}

export default class AbstractionEditor extends React.Component<AbstractionEditorProps, AbstractionEditorState> {

  public render() {
    return (
      <AbstractionForm
        types={this.props.types}
        model={{name: '', description: '', type: {type: '', description: ''}}}
        submitAbstraction={(a: Abstraction) => { /**/ }}/>
    )
  }
}
