import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"

export default (store: AppStore) => ({
  path : 'editor',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Editor = require('./containers/EditorContainer').default
      const reducer = require('./modules/editor').default

      injectReducer(store, { key: 'editor',  reducer })

      cb(null, Editor)
    }, 'editor')
  }
} as PlainRoute)
