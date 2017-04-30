import * as React from 'react'
import { Select, Spin } from 'antd'
import { debounce } from 'lodash'
import {EditorProps} from '../containers/EditorContainer'
import {EditorModule} from '../modules/editor'
import {AbstractionType} from '../../../domain/AbstractionType'
import {FormComponentProps} from 'antd/lib/form/Form'
const Option = Select.Option

class AbstractionTypesRemoteSelect extends React.Component<EditorProps & EditorModule & FormComponentProps & { handleSelectChange: any }, { fetching: boolean, value: any[] }> {
 public state = {
    value: [],
    fetching: false,
  }

  private lastFetchId = 0
  private fetchUser = debounce(async () => {
    this.lastFetchId += 1
    const fetchId = this.lastFetchId
    this.setState({ fetching: true })
    await this.props.getAbstractionTypes()
    if (fetchId !== this.lastFetchId) {
      return
    }
    this.setState({
      fetching: false,
    })
  }, 100)

  public handleChange(value: any[]) {
    this.setState({
      value,
      fetching: false,
    })
    this.props.handleSelectChange(value)
  }

  public render() {
    const { fetching, value } = this.state
    const { abstractionTypes } = this.props.editor
    return (
      <Select
        mode="multiple"
        labelInValue
        value={value}
        placeholder="Select types"
        notFoundContent={fetching ? <Spin size="small" /> : null}
        filterOption={false}
        onSearch={this.fetchUser.bind(this)}
        onChange={this.handleChange.bind(this)}
        style={{ width: '100%' }}
      >
        {abstractionTypes.map((d) => <Option key={d.id.type}>{d.id.type}</Option>)}
      </Select>
    )
  }
}

export default AbstractionTypesRemoteSelect
