import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'
import { EditorModule } from './modules/editor'

declare const require: any

export default function(store: AppStore): PlainRoute {
  return {
    path: 'editor',
    getComponent(nextState, cb) {
      // tslint:disable-next-line
      require.ensure([], async function(require: any) {
        const Editor = require('./containers/EditorContainer').default
        const module = (require('./modules/editor').default as EditorModule)

        await module.fetchUserData()(store.dispatch)

        injectReducer(store, {key: 'editor', reducer: module.getReducer() })
        cb(null, Editor)
      }, 'editor')
    },
  }
}
