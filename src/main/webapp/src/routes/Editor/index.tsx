import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'
import {LoginModule, LoginState} from '../Login/modules/login'
import {EditorModule} from './modules/editor'

declare const require: any

export default (store: AppStore): PlainRoute => ({
  path : 'editor',
  getComponent(nextState, cb) {
    require.ensure([], async function(require: any) {
      const Editor = require('./containers/EditorContainer').default
      const editor: EditorModule = require('./modules/editor').default
      injectReducer<LoginState>(store, { key: 'editor',  reducer: editor.getReducer() })

      const fetchFn = editor.getEntityTypes()
      await fetchFn(store.dispatch.bind(store), store.getState.bind(store))

      cb(null, Editor)
    }, 'editor')
  },
})
