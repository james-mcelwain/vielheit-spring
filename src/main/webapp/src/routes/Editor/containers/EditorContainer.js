import { connect } from 'react-redux'
import { submit, changeForm } from '../modules/editor'

import Editor from '../components/Editor'

const mapDispatchToProps = {
  submit,
  changeForm
}

const mapStateToProps = (state) => ({
  editorState: state.editor
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
