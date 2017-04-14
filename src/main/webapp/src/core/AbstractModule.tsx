import {Reducer} from 'redux'
import {Callable, toCallable, Call} from './Callable'
import {DispatchedAction} from '../store/DispatchedAction'
import {State, AppState} from '../store/appState'
import {Dispatch} from 'react-redux'
import {Action} from '../store/Action'

export function AsyncDispatch(prototype: any, name: string, descriptor: PropertyDescriptor) {
  if (!prototype.asyncActions) {
    prototype.asyncActions = {}
  }

  prototype.asyncActions[name] = prototype[name]
}

export abstract class AbstractModule<S extends State> extends Callable {
  public asyncActions: AsyncActionMap<S>
  protected actions: Array<Action<S>> = []
  private state: S;

  public constructor(initialState: S) {
    super()
    this.state = initialState
    this.bind(this)
  }

  public addAction(actionConstant: Action<S>): this {
    this.actions.push(actionConstant)
    return this
  }

  public getAsyncActions() {
    return this.asyncActions
  }

  @Call
  public reducer(s: S = this.state, action: DispatchedAction<S>): S {
    const handler = this.actions.find((a) => a.compare(action))

    return handler ? handler(s, action.payload) : s
  }
}

interface AsyncActionMap<S> { [key: string]: (...args: any[]) => (dispatch: Dispatch<S>, getState: () => AppState) => any }
AbstractModule.prototype.asyncActions = {}

