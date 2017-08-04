import { connect } from 'react-redux'
import editor, {EditorState} from '../modules/editor'

import {AppState} from '../../../store/appState'
import Editor from '../components/Editor'

const mapDispatchToProps = editor.getAsyncActions()

export interface EditorProps {
  editor: EditorState
}

const mapStateToProps = (state: AppState) => ({
  editor: state.editor,
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
