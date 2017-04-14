import {
  combineReducers, Reducer,
} from 'redux'
import application from './application'
import {AppState, State} from './appState'
import AppStore, {AsyncReducerMap} from './store'
import userReducer from './user'
import location from './location'

export const makeRootReducer = (asyncReducers?: AsyncReducerMap): Reducer<AppState> => {
  return combineReducers({
    application,
    location,
    user: userReducer,
    ...asyncReducers,
  }) as Reducer<AppState>
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
