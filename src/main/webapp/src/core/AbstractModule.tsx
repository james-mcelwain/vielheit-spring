import {Reducer} from 'redux'
import {Callable, toCallable} from './Callable'
import {DispatchedAction} from '../store/DispatchedAction'
import {State, AppState} from '../store/appState'
import {Dispatch} from 'react-redux'
import {Action} from '../store/Action'

export function AsyncDispatch(prototype: any, name: string, descriptor: PropertyDescriptor) {
  prototype.asyncActions[name] = prototype[name]
}

export abstract class AbstractModule<S extends State> {
  private state: S;
  private actions: Array<Action<S>> = []
  private asyncActions: { [key: string]: (...args: any[]) => (dispatch: Dispatch<S>, getState: () => AppState) => any } = {}

  public constructor(initialState: S) {
    this.state = initialState
  }

  public toReducer(fn?: Reducer<S>): this {
    if (fn) {
      return toCallable<this, Reducer<S>>(this, fn)
    }

    return toCallable<this, Reducer<S>>(this, (state: S = this.state, action: DispatchedAction<S>) => {
      const handler = this.actions.find((k) => k.compare(action))
      return handler ? handler(state, action.payload) : state
    })
  }

  public addAction(actionConstant: Action<S>): this {
    this.actions.push(actionConstant)
    return this
  }

  public getAsyncActions() {
    return this.asyncActions
  }
}
