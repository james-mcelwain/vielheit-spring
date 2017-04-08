import {
  combineReducers, Reducer
} from 'redux'
import locationReducer from './location'
import userReducer from './user'
import applicationReducer from './application'
import AppStore, {AsyncReducerMap} from "./store"
import {AppState, State} from "./appState"

export const makeRootReducer = (asyncReducers?: AsyncReducerMap): Reducer<AppState> => {
  return combineReducers({
    user: userReducer,
    application: applicationReducer,
    location: locationReducer,
    ...asyncReducers
  }) as Reducer<AppState>
}

export const injectReducer = (store: AppStore, {
  key,
  reducer
}: { key: string, reducer: Reducer<State> }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) return
  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
