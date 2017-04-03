import React from 'react'
import './Editor.scss'
import EditorForm from '../forms/EditorForm'

export const Register = (props) => (
  <div className="editor-container">
    <h3 className="editor-title">New Entry:</h3>
    <EditorForm {...props} />
  </div>
)

Register.propTypes = {}

export default Register
