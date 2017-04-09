import {AppAction} from '../store/appAction'
import {State} from '../store/appState'
import {Callable, ToCallabe, toCallable} from './callable'
import {Reducer} from 'redux'

export abstract class AbstractModule<S extends State> {
  public static New<T extends State>(m: AbstractModule<T>, fn?: Reducer<T>): AbstractModule<T> & Callable<Reducer<T>> {
    if (fn) {
      return toCallable<AbstractModule<T>, Reducer<T>>(m, fn)
    }

    return toCallable<AbstractModule<T>, Reducer<T>>(m, (state: T = m.state, action: AppAction<T>) => {
        const handler = m.ACTION_HANDLERS[action.type]
        return handler ? handler(state, action) : state
    })
  }

  public abstract state: S

  protected abstract ACTION_HANDLERS: { [key: string]: (state: S, action?: AppAction<S>) => S }

  public constructor(initialState: S) {
    this.state = initialState
  }
}
