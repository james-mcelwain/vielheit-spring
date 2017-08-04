import {PlainRoute} from 'react-router'
import {injectReducer} from '../../store/reducers'
import {AppStore} from '../../store/store'
import {EditorModule, EditorState} from './modules/editor'

declare const require: any

export default (store: AppStore): PlainRoute => ({
  path : 'editor',
  getComponent(nextState, cb) {
    require.ensure([], async function(require: any) {
      const Editor = require('./containers/EditorContainer').default
      const editor: EditorModule = require('./modules/editor').default
      injectReducer<EditorState>(store, { key: 'editor',  reducer: editor.getReducer() })
      cb(null, Editor)
    }, 'editor')
  },
})
