import {Reducer} from 'redux'
import {Callable, toCallable} from './Callable'
import {AppAction} from '../store/Action'
import {State} from '../store/appState'
import {ActionConstant} from '../store/makeConstant'

export abstract class AbstractModule<S extends State> {
  private state: S;
  private actions: Array<ActionConstant<S>> = []

  public constructor(initialState: S) {
    this.state = initialState
  }

  public toReducer(fn?: Reducer<S>): this & Callable<Reducer<S>> {
    if (fn) {
      return toCallable<this, Reducer<S>>(this, fn)
    }

    return toCallable<this, Reducer<S>>(this, (state: S = this.state, action: AppAction<S>) => {
      const handler = this.actions.find((k) => k.compare(action))
      return handler ? handler.handler(state, action.payload) : state
    })
  }

  public addAction(actionConstant: ActionConstant<S>): this {
    this.actions.push(actionConstant)
    return this
  }
}
