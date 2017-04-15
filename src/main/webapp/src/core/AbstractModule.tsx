import {Reducer} from 'redux'
import {DispatchedAction} from '../store/DispatchedAction'
import {State, AppState} from '../store/appState'
import {Dispatch} from 'react-redux'
import {ActionCreator, ActionHandler} from './ActionCreator'
import {ActionType} from './ActionType'

export function AsyncDispatch(prototype: any, name: string, descriptor: PropertyDescriptor) {
  if (!prototype.asyncActions) {
    prototype.asyncActions = {}
  }

  prototype.asyncActions[name] = prototype[name]
}

export abstract class AbstractModule<S extends State> {
  public asyncActions: AsyncActionMap<S>
  protected actions: Array<ActionCreator<S, any>> = []
  private state: S | null;

  public constructor(initialState: S) {
    this.state = initialState
  }

  public Action<P>(actionType: ActionType, fn: ActionHandler<S, P> = (s) => s): ActionCreator<S, P> {
    const action = new ActionCreator<S, P>(actionType, fn)
    this.actions.push(action)
    return action
  }

  public getAsyncActions() {
    return this.asyncActions
  }

  public reducer(s: S, action: DispatchedAction<S>): S {
    if (this.state !== null && !s) {
      s = this.state
    }

    const handler = this.actions.find((a) => a.compare(action))

    return handler ? handler.handler(s, action.payload) : s
  }
}

export interface AsyncActionMap<S> { [key: string]: (...args: any[]) => (dispatch: Dispatch<S>, getState: () => AppState) => any }
AbstractModule.prototype.asyncActions = {}
