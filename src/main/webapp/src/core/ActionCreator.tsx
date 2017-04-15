import {Action as ReduxAction} from 'redux'
import {DispatchedAction} from '../store/DispatchedAction'
import {ActionType} from './ActionType'
import {Callable, Call} from './type-utils/Callable'
import {AppState, State} from '../store/appState'

export type ActionHandler<S, P> = (s: S, p: P) => S

export class ActionCreator<S extends State, P> implements ReduxAction {
  public type: ActionType;
  public handler: ActionHandler<S, P>

  private _handle: Function

  public constructor(type: ActionType, fn: ActionHandler<S, P> = (s) => s) {
    this.type = type
    this.handler = fn
  }

  public compare(a: DispatchedAction<S>) {
    return a.type === this.type
  }

  public dispatch(payload?: P): DispatchedAction<S> {
    return payload ? { type: this.type, payload } : { type: this.type }
  }

  public toString() {
    return this.type
  }
}
