import {Action as ReduxAction} from 'redux'
import {DispatchedAction} from './DispatchedAction'
import {ActionType} from '../core/ActionType'
import {Callable, Call} from '../core/Callable'
import {AppState} from './appState'

export type ActionHandler<S> = (s: S, p: S[keyof S] | S) => S

export class Action<S> extends Callable implements ReduxAction {
  public type: ActionType;

  private _handle: Function

  public constructor(type: ActionType, fn: ActionHandler<S>) {
    super()
    this.type = type
    this._handle = fn
  }

  public compare(a: DispatchedAction<S>) {
    return a.type === this.type
  }

  public dispatch(s?: AppState[keyof AppState] | AppState[keyof AppState] | S[keyof S] | S): DispatchedAction<S> {
    return s ? { type: this.type, payload: s } : { type: this.type }
  }

  public toString() {
    return this.type
  }

  @Call
  public handle() {
    return this._handle()
  }
}
