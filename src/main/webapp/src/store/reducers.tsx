import {
  combineReducers, Reducer,
} from 'redux'
import application from './application'
import {AppState, State} from './appState'
import AppStore, {AsyncReducerMap} from './store'
import userReducer from './user'
import location from './location'
import {LOCATION_CHANGE} from './location'

export const makeRootReducer = (asyncReducers?: AsyncReducerMap): Reducer<AppState> => {
  return combineReducers<AppState>({
    application,
    location: LOCATION_CHANGE.handle.bind(LOCATION_CHANGE),
    user: userReducer,
    ...asyncReducers,
  })
}

export const injectReducer = (store: AppStore, {
  key,
  reducer,
}: { key: string, reducer: Reducer<State> }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) {
    return
  }
  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
