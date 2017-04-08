import React, {StatelessComponent} from 'react'
import './Editor.scss'
import EditorForm from '../forms/EditorForm'
import { Radio } from 'antd'
import {EditorState} from "../modules/editor"
const RadioButton = Radio.Button
const RadioGroup = Radio.Group

const a = { b() {} }

class Register extends React.Component<{ changeForm: any, editorState: EditorState },{}> {
  private forms: {
    [key: string]: StatelessComponent<any>
  } = {
      ['entry']: EditorForm,
      ['space']: () => <div>space</div>,
      ['time']: () => <div>time</div>,
      ['concept']: () => <div>concept</div>
    }

  onChange({ target: { value } }: { target: { value: string }}) {
    this.props.changeForm(value)
  }

  get Form() {
    return this.forms[this.props.editorState.form]
  }

  render() {
    return (
      <div className="editor-container">
        <RadioGroup onChange={this.onChange.bind(this)} defaultValue="a" size="large">
          <RadioButton value="entry">Entry</RadioButton>
          <RadioButton value="concept">Concept</RadioButton>
          <RadioButton value="space">Space</RadioButton>
          <RadioButton value="time">Time</RadioButton>
        </RadioGroup>
        <this.Form></this.Form>
      </div>
    )
  }
}

export default Register
