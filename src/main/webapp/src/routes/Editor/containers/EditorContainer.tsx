import {connect, Dispatch} from 'react-redux'
import editorModule, { EditorModule } from '../modules/editor'

import {AppState} from '../../../store/appState'
import Editor from '../components/Editor'
import {EditorState} from '../modules/editor'

const mapDispatchToProps = editorModule.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
  editorState: state.editor,
  editor: editorModule,
})

export interface EditorProps {
  editorState: EditorState,
  editor: EditorModule,
}

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
