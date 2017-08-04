import {DispatchedAction} from '../store/DispatchedAction'
import {State} from '../store/appState'
import {Dispatch} from 'react-redux'
import {ActionCreator, ActionHandler} from './ActionCreator'
import {ActionType} from './ActionType'
import {message} from 'antd'
import {store} from '../main'
import {Reducer} from 'redux'

export function AsyncDispatch(prototype: any, name: string, descriptor: PropertyDescriptor) {
  if (!prototype.asyncActions) {
    prototype.asyncActions = new Map()
  }

  prototype.asyncActions.set(name, prototype[name])
}

export abstract class AbstractModule<S extends State> {
  public asyncActions: AsyncActionMap<S>
  protected actions: Array<ActionCreator<S, any>> = []
  private state: S | null;

  public constructor(initialState: S) {
    this.state = initialState
  }

  public message(type: 'success' | 'error' | 'warning', msg: string) {
    message[type](msg)
  }

  public Action<P>(actionType: ActionType, fn: ActionHandler<S, P> = (s) => s): ActionCreator<S, P> {
    const action = new ActionCreator<S, P>(actionType, fn)
    this.actions.push(action)
    return action
  }

  public getAsyncActions() {
    const actions: { [key: string]: AsyncAction<S>} = {}

    for (const [key, value] of this.asyncActions.entries()) {
      actions[key] = value.bind(this)
    }

    return actions
  }

  public getReducer(): Reducer<S> {
    // return bound instance for injection
    return this.reducer.bind(this)
  }

  public reducer(s: S, action: DispatchedAction<S>): S {
    if (!s && this.state !== null) {
      s = this.state
    }

    const handler = this.actions.find((a) => a.compare(action))

    return handler ? handler.handler(s, action.payload) : s
  }

  protected userId() {
    return (store.getState().application.user || { id: null }).id
  }
}

type AsyncAction<S> = (...args: any[]) => (dispatch: Dispatch<S>) => any
export type AsyncActionMap<S> = Map<string, AsyncAction<S>>
AbstractModule.prototype.asyncActions = new Map()
