import {Reducer, Store} from "redux"
import {AppState, State} from "./appState"
import {UnregisterCallback} from "history"

export interface AsyncReducerMap {
  [key: string]: Reducer<State>
}

export interface AppStore extends Store<AppState> {
  asyncReducers: AsyncReducerMap
  unsubscribeHistory: UnregisterCallback
}

export default AppStore
