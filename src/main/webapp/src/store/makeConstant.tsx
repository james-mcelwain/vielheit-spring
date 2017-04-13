import {Action as ReduxAction} from 'redux'
import {AppAction} from './Action'
import {Callable, Call} from '../core/Callable'

export type ActionType =  string

export type ActionHandler<S> = (s: S, p: S[keyof S]) => S

export interface ActionConstant<S> {
  (): ActionType
  compare(a: Action): boolean
  toAction(payload?: S[keyof S] | S): AppAction<S>
  toString(): string
  handler(s: S, p: S[keyof S]): S
}

export default function makeConstant<S>(constantString: ActionType, fn: ActionHandler<S>): ActionConstant<S> {
  return Object.assign(() => {
    return constantString
  }, {
    compare(a: Action) { return a.type  === constantString },
    toAction(s?: S[keyof S]) { return  s ? { type: constantString, payload: s } : { type: constantString }},
    toString() { return constantString },
    handler: fn,
  })
}
class Action extends Callable implements ReduxAction {
  public type: ActionType;

  @Call
  public handle(): string {
    return "ok"
  }
}
