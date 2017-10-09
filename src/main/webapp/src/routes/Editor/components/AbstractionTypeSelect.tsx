import * as React from 'react'
import { Select } from 'antd'
import { AbstractionType } from '../modules/editor'

export interface AbstractionTypeSelectProps {
  types: AbstractionType[]
}

export interface AbstractionTypeSelectState {

}

export default class AbstractionTypeSelect extends React.Component<AbstractionTypeSelectProps, AbstractionTypeSelectState> {
  public render() {
    const { types } = this.props

    return (
      <Select
        showSearch
        placeholder="Select an abstraction type"
        optionFilterProp="name"
        filterOption={(input, opt) => console.log(input, opt) || true}
      >
        {types.map((t) => <Select.Option key={t.id}>{t.type}</Select.Option>)}
      </Select>
    )
  }
}
