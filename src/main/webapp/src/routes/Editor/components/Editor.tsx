import * as React from 'react'
import './Editor.scss'
import EntryForm from '../forms/EntryForm'
import ConceptForm from '../forms/ConceptForm'
import TimeForm from '../forms/TimeForm'
import SpaceForm from '../forms/SpaceForm'
import { Radio } from 'antd'
import {EditorState} from "../modules/editor"
const RadioButton = Radio.Button
const RadioGroup = Radio.Group

class Register extends React.Component<{ changeForm: any, editorState: EditorState },{}> {
  private forms: {
    [key: string]: React.StatelessComponent<any>
  } = {
      ['entry']: EntryForm,
      ['space']: SpaceForm,
      ['time']: TimeForm,
      ['concept']: ConceptForm,
    }

  onChange({ target: { value } }: { target: { value: string }}) {
    this.props.changeForm(value)
  }

  get Form() {
    return this.forms[this.props.editorState.form]
  }

  render(...props: any[]) {
    return (
      <div className="editor-container">
        <RadioGroup onChange={this.onChange.bind(this)} defaultValue="a" size="large">
          <RadioButton value="entry">Entry</RadioButton>
          <RadioButton value="concept">Concept</RadioButton>
          <RadioButton value="space">Space</RadioButton>
          <RadioButton value="time">Time</RadioButton>
        </RadioGroup>
        <this.Form {...props}/>
      </div>
    )
  }
}

export default Register
