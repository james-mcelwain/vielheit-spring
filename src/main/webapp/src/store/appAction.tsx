
import {ActionType} from "./makeConstant"
import {Action} from "redux"

export interface AppAction<S> extends Action {
  type: ActionType
  payload: S[keyof S] | S
}
