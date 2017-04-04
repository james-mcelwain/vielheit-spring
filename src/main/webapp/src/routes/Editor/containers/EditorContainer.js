import { connect } from 'react-redux'
import { submit } from '../modules/editor'

import Editor from '../components/Editor'

const mapDispatchToProps = {
  submit
}


const mapStateToProps = (state) => ({
  editorState: state.editor
})

export default connect(mapStateToProps, mapDispatchToProps)(Editor)
