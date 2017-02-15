import { connect } from 'react-redux'
import { editor } from '../modules/editor'

import Editor from '../components/Editor'

const mapDispatchToProps = {
  editor
}

const mapStateToProps = (state) => ({
  editorState: state.editor
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
