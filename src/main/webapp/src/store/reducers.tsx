import {
  combineReducers, Reducer,
} from 'redux'
import Application from 'core/Application'
import {AppState, State} from './appState'
import AppStore, {AsyncReducerMap} from './store'
import Location from 'core/Location'

export const makeRootReducer = (asyncReducers: AsyncReducerMap): Reducer<AppState> => {
  return combineReducers<AppState>({
    application: Application.getReducer(),
    location: Location.getReducer(),
    ...asyncReducers,
  })
}

export const injectReducer = <S extends State>(store: AppStore, {
  key,
  reducer,
}: { key: string, reducer: Reducer<S> }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) {
    return
  }
  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
