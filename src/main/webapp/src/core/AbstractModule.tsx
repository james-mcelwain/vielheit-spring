import {Reducer} from 'redux'
import {Callable, toCallable} from './Callable'

import {AppAction} from '../store/appAction'
import {State} from '../store/appState'
import {ActionConstant, ActionType, default as makeConstant} from '../store/makeConstant'
import {IterableWeakMap} from './IterableWeakMap'

export abstract class AbstractModule<S extends State> {
  public abstract state: S
  protected actionHandlers: IterableWeakMap<ActionConstant<S>, (state: S, action?: AppAction<S>) => S>
    = new IterableWeakMap<ActionConstant<S>, (state: S, action?: AppAction<S>) => S>()

  public constructor(initialState: S) {
    this.state = initialState
  }

  public toReducer(fn?: Reducer<S>): this & Callable<Reducer<S>> {
    if (fn) {
      return toCallable<this, Reducer<S>>(this, fn)
    }

    return toCallable<this, Reducer<S>>(this, (state: S = this.state, action: AppAction<S>) => {
      const handler = this.actionHandlers.find((k) => k.compare(action))
      return handler ? handler(state, action) : state
    })
  }

  public addAction(actionConstant: ActionConstant<S> | ActionType, fn: (state: S, action?: AppAction<S>) => S): this {
    if (typeof actionConstant === 'string') {
      this.actionHandlers.set(makeConstant(actionConstant), fn)
      return this
    }

    this.actionHandlers.set(actionConstant, fn)
    return this
  }

}
