import * as React from 'react'
import { Select, Spin } from 'antd'
import { Abstraction } from '../modules/editor'

export interface AbstractionMultiSelectProps {
  types: Abstraction[]
  handleSelect(): void
}

export interface AbstractionMultiSelectState {
  types: Abstraction[]
  data: Abstraction[]
  fetching: boolean
}

export default class AbstractionMultiSelect extends React.Component<AbstractionMultiSelectProps, AbstractionMultiSelectState> {
  public state: AbstractionMultiSelectState = {
    types: [],
    data: [],
    fetching: false
  }


  public render() {
    const { types, fetching } = this.state
    const { types: data, handleSelect } = this.props

    return (
      <Select
        mode="multiple"
        labelInValue
        value={types}
        placeholder="Select Abstraction s"
        notFoundContent={fetching ? <Spin size="small" /> : null}
        filterOption={false}
        onChange={handleSelect}
      >
        {data.map(d => <Select.Option>Hi</Select.Option>)}
      </Select>
    )
  }
}
