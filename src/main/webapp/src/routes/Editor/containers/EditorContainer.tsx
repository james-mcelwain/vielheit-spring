import { connect } from 'react-redux'
import { submit, changeForm } from '../modules/editor'

import Editor from '../components/Editor'
import {AppState} from "../../../store/appState"

const mapDispatchToProps = {
  submit,
  changeForm
}

const mapStateToProps = (state: AppState) => ({
  editorState: state.editor
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
