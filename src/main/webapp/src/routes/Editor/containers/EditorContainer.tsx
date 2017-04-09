import { connect } from 'react-redux'
import { changeForm, submit } from '../modules/editor'

import {AppState} from '../../../store/appState'
import Editor from '../components/Editor'

const mapDispatchToProps = {
  submit,
  changeForm,
}

const mapStateToProps = (state: AppState) => ({
  editorState: state.editor,
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
