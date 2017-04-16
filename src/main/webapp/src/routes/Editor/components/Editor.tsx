import {Radio} from 'antd'
import * as React from 'react'
import EntryForm from '../forms/EntryForm'
import EntityForm from '../forms/EntityForm'
import EntityTypeForm from '../forms/EntityTypeForm'
import {EditorState} from '../modules/editor'
import './Editor.scss'
const RadioButton = Radio.Button
const RadioGroup = Radio.Group

class Editor extends React.Component<{changeForm: any, editor: EditorState}, {}> {
  private forms: {
    [key: string]: React.StatelessComponent<any>,
  } = {
    ['entry']: EntryForm,
    ['entity-type']: EntityTypeForm,
    ['entity']: EntityForm
  }

  public render() {
    return (
      <div className="editor-container">
        <RadioGroup onChange={this.onChange.bind(this)} defaultValue="a" size="large">
          <RadioButton value="entry">Entry</RadioButton>
          <RadioButton value="entity-type">Entity Type</RadioButton>
          <RadioButton value="entity">Entity</RadioButton>
        </RadioGroup>
        <this.Form {...this.props}/>
      </div>
    )
  }

  private onChange({target: {value}}: {target: {value: string}}) {
    this.props.changeForm(value)
  }

  private get Form() {
    return this.forms[this.props.editor.form]
  }
}

export default Editor
