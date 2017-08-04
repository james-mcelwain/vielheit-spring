import { browserHistory } from 'react-router'
import {applyMiddleware, compose, createStore, GenericStoreEnhancer, Middleware, StoreEnhancer} from 'redux'
import thunk from 'redux-thunk'
import {AppState} from './appState'
import makeRootReducer from './reducers'
import AppStore from './store'
import Location from '../core/Location'
import Application from 'core/Application'

declare const module: any
declare const __DEV__: any
declare const require: any
declare const window: any

export default (initialState: AppState) => {
  const middleware: Middleware[] = [thunk]

  const enhancers: Array<StoreEnhancer<any>> = []

  let composeEnhancers: (...enhancers: Array<StoreEnhancer<any>>) => StoreEnhancer<any> = compose

  if (__DEV__) {
    const composeWithDevToolsExtension = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    if (typeof composeWithDevToolsExtension === 'function') {
      composeEnhancers = composeWithDevToolsExtension
    }
  }

  const store: AppStore = createStore<AppState>(
    makeRootReducer({}),
    initialState,
    composeEnhancers(
      applyMiddleware(...middleware),
      ...enhancers,
    ),
  ) as AppStore
  store.asyncReducers = {}

  store.unsubscribeHistory = browserHistory.listen(Location.updateLocation(store))

  if (module.hot) {
    module.hot.accept('./reducers', () => {
      const reducers = require('./reducers').default
      store.replaceReducer(reducers(store.asyncReducers))
    })
  }

  store.dispatch(Application.BOOTSTRAP.dispatch())
  return store
}
