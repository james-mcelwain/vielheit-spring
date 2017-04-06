import React from 'react'
import './Editor.scss'
import EditorForm from '../forms/EditorForm'
import { Radio } from 'antd'
const RadioButton = Radio.Button
const RadioGroup = Radio.Group

const a = { b() {} }

class Register extends React.Component {
  constructor(...args) {
    super(...args)

    console.log(this.props)
    this.forms = {
      ['entry']: EditorForm,
      ['space']: () => <div>space</div>,
      ['time']: () => <div>time</div>,
      ['concept']: () => <div>concept</div>
    }
  }

  onChange({ target: { value } }) {
    this.props.changeForm(value)
  }

  get Form() {
    return this.forms[this.props.editorState.form]
  }

  render(props) {
    return (
      <div className="editor-container">
        <RadioGroup onChange={::this.onChange} defaultValue="a" size="large">
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

Register.propTypes = {}

export default Register
