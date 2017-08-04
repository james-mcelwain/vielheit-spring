
import {Action} from 'redux'
import {ActionType} from '../core/ActionType'
import {AppState} from './appState'

export interface DispatchedAction<S> extends Action {
  type: ActionType
  payload?: AppState[keyof AppState] | S[keyof S] | S
}
