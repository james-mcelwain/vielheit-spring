import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'

declare const require: any

export default (store: AppStore): PlainRoute => ({
  path : 'editor',
  getComponent(nextState, cb) {
    require.ensure([], function(require: any) {
      const Editor = require('./containers/EditorContainer').default
      const reducer = require('./modules/editor').default

      injectReducer(store, { key: 'editor',  reducer })

      cb(null, Editor)
    }, 'editor')
  },
})
