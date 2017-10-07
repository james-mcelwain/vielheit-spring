import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'

declare const require: any

export default function(store: AppStore): PlainRoute {
  return {
    path: 'journal',
    getComponent(nextState, cb) {
      // tslint:disable-next-line
      require.ensure([], async function(require: any) {
        const Journal = require('./containers/JournalContainer').default
        const module = (require('./modules/journal').default as any)

        console.log(module)

        await module.getEntries()(store.dispatch.bind(store))
        injectReducer(store, {key: 'journal', reducer: module.getReducer() })
        cb(null, Journal)
      }, 'journal')
    },
  }
}
