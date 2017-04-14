import { browserHistory } from 'react-router'
import {applyMiddleware, compose, createStore, GenericStoreEnhancer, Middleware, StoreEnhancer} from 'redux'
import thunk from 'redux-thunk'
import {AppState} from './appState'
import makeRootReducer from './reducers'
import AppStore from './store'
import location from './location'

declare const module: any
declare const __DEV__: any
declare const require: any
declare const window: any

export default (initialState: AppState) => {
  // ======================================================
  // Middleware Configuration
  // ======================================================
  const middleware: Middleware[] = [thunk]

  // ======================================================
  // Store Enhancers
  // ======================================================
  const enhancers: Array<StoreEnhancer<any>> = []

  let composeEnhancers: (...enhancers: Array<StoreEnhancer<any>>) => StoreEnhancer<any> = compose

  if (__DEV__) {
    const composeWithDevToolsExtension = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    if (typeof composeWithDevToolsExtension === 'function') {
      composeEnhancers = composeWithDevToolsExtension
    }
  }

  // ======================================================
  // Store Instantiation and HMR Setup
  // ======================================================
  const store: AppStore = createStore<AppState>(
    makeRootReducer(),
    initialState,
    composeEnhancers(
      applyMiddleware(...middleware),
      ...enhancers,
    ),
  ) as AppStore
  store.asyncReducers = {}

  // To unsubscribe, invoke `store.unsubscribeHistory()` anytime
  store.unsubscribeHistory = browserHistory.listen(location.updateLocation(store))

  if (module.hot) {
    module.hot.accept('./reducers', () => {
      const reducers = require('./reducers').default
      store.replaceReducer(reducers(store.asyncReducers))
    })
  }

  return store
}
