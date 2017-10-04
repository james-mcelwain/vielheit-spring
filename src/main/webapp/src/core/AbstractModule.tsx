import {DispatchedAction} from '../store/DispatchedAction'
import {State} from '../store/appState'
import {Dispatch} from 'react-redux'
import {ActionCreator, ActionHandler} from './ActionCreator'
import {ActionType} from './ActionType'
import {message} from 'antd'
import {store} from '../main'
import {Reducer} from 'redux'

/**
 * @AsyncDispatch
 *
 * This annotation is purely for convenience, and allows us to write
 * normal async methods on a module class without having to then wire
 * it to some registration mechanism in the constructor.
 *
 * Modules are singletons, which allows us to do this safely.
 *
 * @param prototype
 * @param {string} name
 * @param {PropertyDescriptor} descriptor
 * @constructor
 */
export function AsyncDispatch(prototype: any, name: string, descriptor: PropertyDescriptor) {
  if (!prototype.asyncActions) {
    prototype.asyncActions = new Map()
  }

  prototype.asyncActions.set(name, prototype[name])
}

/**
 * Base class for all route modules, i.e. serves as a container
 * for actions and reducers for any given route.
 */
export abstract class AbstractModule<S extends State> {
  public asyncActions: AsyncActionMap<S>
  protected actions: Array<ActionCreator<S, any>> = []
  private state: S | null;

  /**
   * @param {S} initialState - the initial state of the page
   */
  public constructor(initialState: S) {
    this.state = initialState
  }

  /**
   * Convenience method to wire into global message dispatch.
   *
   * @param {"success" | "error" | "warning"} type
   * @param {string} msg
   */
  public message(type: 'success' | 'error' | 'warning', msg: string) {
    message[type](msg)
  }

  /**
   * Synchronous action handler registration, which are used to construct
   * the default reducer. Useful for actions that can be expressed as a
   * single line, e.g. flipping booleans or spreading state.
   *
   * @param {ActionType} actionType
   * @param {ActionHandler<S extends State, P>} fn
   * @returns {ActionCreator<S extends State, P>}
   * @constructor
   */
  public Action<P>(actionType: ActionType, fn: ActionHandler<S, P> = (s) => s): ActionCreator<S, P> {
    const action = new ActionCreator<S, P>(actionType, fn)
    this.actions.push(action)
    return action
  }

  /**
   * Returns actions that have been registered with @AsyncAction annotation.
   * (See above).
   *
   * @returns {{[p: string]: AsyncAction<S extends State>}}
   */
  public getAsyncActions() {
    const actions: { [key: string]: AsyncAction<S>} = {}

    for (const [key, value] of this.asyncActions.entries()) {
      actions[key] = value.bind(this)
    }

    return actions
  }

  /**
   * When we wire the store, we have to pass the reducer as a
   * map property which will remove the reducer's `this` context.
   * This is an annoying work around to explicitly bind the reducer
   * to the module's `this` context.
   *
   * @returns {Redux.Reducer<S extends State>}
   */
  public getReducer(): Reducer<S> {
    // return bound instance for injection
    return this.reducer.bind(this)
  }

  /**
   * Default reducer that delegates to actions that have been registered
   * on the module. Can be overridden to provide more complex reduction
   * logic where needed.
   *
   * @param {S} s
   * @param {DispatchedAction<S extends State>} action
   * @returns {S}
   */
  public reducer(s: S, action: DispatchedAction<S>): S {
    if (!s && this.state !== null) {
      s = this.state
    }

    const handler = this.actions.find((a) => a.compare(action))

    return handler ? handler.handler(s, action.payload) : s
  }

  /**
   * Obtaining the current user's id is a very common operation across
   * most modules. We could store the user in `sessionStorage` or on `window`,
   * but this is a convenient work around despite coupling implementation
   * details of a derived class to the base class.
   *
   * @returns {number}
   */
  protected userId() {
    return (store.getState().application.user || { id: null }).id
  }
}

type AsyncAction<S> = (...args: any[]) => (dispatch: Dispatch<S>) => any
export type AsyncActionMap<S> = Map<string, AsyncAction<S>>
AbstractModule.prototype.asyncActions = new Map()
