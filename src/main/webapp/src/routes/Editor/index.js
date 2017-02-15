import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'editor',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Register = require('./containers/EditorContainer').default
      const editor = require('./modules/editor').default

      injectReducer(store, { key: 'editor',  editor })
      cb(null,Register)
    }, 'editor')
  }
})
