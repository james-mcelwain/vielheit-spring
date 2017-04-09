import {Action} from 'redux'
import {AppAction} from './appAction'
export type ActionType =  string

export interface ActionConstant<S> {
  (): ActionType
  compare(a: Action): boolean
  toAction(payload?: S[keyof S] | S): AppAction<S>
}

export default function makeConstant<S>(constantString: ActionType): ActionConstant<S> {
  return Object.assign(() => {
    return constantString
  }, {
    compare(a: Action) { return a.type  === constantString },
    toAction<S>(payload?: S[keyof S] | S) { return  payload ? { type: constantString, payload } : { type: constantString }},
  })
}
