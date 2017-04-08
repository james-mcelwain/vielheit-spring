import {applyMiddleware, compose, createStore, GenericStoreEnhancer, Middleware, StoreEnhancer} from 'redux'
import thunk from 'redux-thunk'
import { browserHistory } from 'react-router'
import makeRootReducer from './reducers'
import { updateLocation } from './location'
import AppStore from "./store"
import {AppState} from "./appState"

declare const global: any
declare const module: any

export default (initialState: AppState) => {
  // ======================================================
  // Middleware Configuration
  // ======================================================
  const middleware: Middleware[] = [thunk]

  // ======================================================
  // Store Enhancers
  // ======================================================
  const enhancers: Array<StoreEnhancer<any>> = []

  let composeEnhancers: (...enhancers: StoreEnhancer<any>[]) => StoreEnhancer<any> = compose

  if (global.__DEV__) {
    const composeWithDevToolsExtension = global.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
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
      ...enhancers
    )
  ) as AppStore
  store.asyncReducers = {}

  // To unsubscribe, invoke `store.unsubscribeHistory()` anytime
  store.unsubscribeHistory = browserHistory.listen(updateLocation(store))

  if (module.hot) {
    module.hot.accept('./reducers', () => {
      const reducers = global.require('./reducers').default
      store.replaceReducer(reducers(store.asyncReducers))
    })
  }

  return store
}
