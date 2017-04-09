
import {Action} from 'redux'
import {ActionType} from './makeConstant'

export interface AppAction<S> extends Action {
  type: ActionType
  payload: S[keyof S] | S
}
