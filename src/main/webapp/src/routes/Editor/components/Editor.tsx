import {Radio} from 'antd'
import * as React from 'react'
import EntryForm from '../forms/EntryForm'
import EntityForm from '../forms/AbstractionForm'
import EntityTypeForm from '../forms/AbstractionTypeForm'
import {EditorState} from '../modules/editor'
import './Editor.scss'

const RadioButton = Radio.Button
const RadioGroup = Radio.Group

class Editor extends React.Component<{changeForm: any, editor: EditorState}, {}> {
  public render() {
    const { editor: { form }} = this.props

    return (
      <div className="editor-container">
        <RadioGroup onChange={this.onChange.bind(this)} defaultValue="a" size="large">
          <RadioButton value="entry">Entry</RadioButton>
          <RadioButton value="entity-type">Entity Type</RadioButton>
          <RadioButton value="entity">Entity</RadioButton>
        </RadioGroup>
        {form === 'entry' && <EntryForm { ...this.props}/>}
        {form === 'entity-type' && <EntityTypeForm { ...this.props}/>}
        {form === 'entity' && <EntityForm { ...this.props}/>}
      </div>
    )
  }

  private onChange({target: {value}}: {target: {value: string}}) {
    this.props.changeForm(value)
  }
}

export default Editor
