import {Reducer} from 'redux'
import {Callable, toCallable} from './callable'

import {AppAction} from '../store/appAction'
import {State} from '../store/appState'
import {ActionConstant, ActionType, default as makeConstant} from '../store/makeConstant'

export abstract class AbstractModule<C extends AbstractModule<C, S>, S extends State> {
  public abstract state: S
  protected actionHandlers: {[key: string]: (state: S, action?: AppAction<S>) => S} = {}
  protected actionConstants: {[key: string]: ActionConstant<S>} = {}

  public constructor(initialState: S) {
    this.state = initialState
  }

  public toReducer(fn?: Reducer<S>): C & Callable<Reducer<S>> {
    if (fn) {
      return toCallable<C, Reducer<S>>(this as any as C, fn)
    }

    return toCallable<C, Reducer<S>>(this as any as C, (state: S = this.state, action: AppAction<S>) => {
      const handler = this.actionHandlers[action.type]
      return handler ? handler(state, action) : state
    })
  }

  public addAction(actionConstant: ActionConstant<S> | ActionType, fn: (state: S, action?: AppAction<S>) => S): AbstractModule<C, S> {
    if (typeof actionConstant === 'string') {
      this.actionConstants[actionConstant] = makeConstant(actionConstant)
      this.actionHandlers[actionConstant] = fn
      return this
    }

    this.actionConstants[actionConstant()] = actionConstant
    this.actionHandlers[actionConstant()] = fn
    return this
  }

}
