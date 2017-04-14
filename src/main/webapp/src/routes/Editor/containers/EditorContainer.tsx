import { connect } from 'react-redux'
import editor from '../modules/editor'

import {AppState} from '../../../store/appState'
import Editor from '../components/Editor'

const mapDispatchToProps = editor.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
  editor: state.editor,
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
