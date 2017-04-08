import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"
import {global} from "../../main"

export default (store: AppStore) => ({
  path : 'editor',
  getComponent (nextState, cb) {
    global.require.ensure([], (require: any) => {
      const Editor = require('./containers/EditorContainer').default
      const reducer = require('./modules/editor').default

      injectReducer(store, { key: 'editor',  reducer })

      cb(null, Editor)
    }, 'editor')
  }
} as PlainRoute)
