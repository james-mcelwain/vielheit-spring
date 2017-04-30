import {State} from '../store/appState'
import {AbstractModule} from './AbstractModule'
import User from '../domain/User'
import {DispatchedAction} from '../store/DispatchedAction'

export interface ApplicationState extends State {
  error: Error | null,
  loggedIn: boolean,
  user: User | null
}

class Application extends AbstractModule<ApplicationState> {
  public RESPONSE_ERROR = this.Action<Error>('RESPONSE_ERROR', (state, payload) => ({error: payload, ...state}))
  public LOGOUT = this.Action('LOGOUT', (state) => ({ ...state, loggedIn: false, user: null }))
  public LOGIN = this.Action<User>('LOGIN', (state, payload) => ({ ...state, loggedIn: true, user: payload  }))

  public reducer(s: ApplicationState, action: DispatchedAction<ApplicationState>): ApplicationState {
    s = super.reducer(s, action)
    if (action.type === 'LOGIN_SUCCESS') {
      s = {...s, loggedIn: true, user: (action.payload as User) }
    }
    return s
  }
}

export default new Application({
  error: null,
  loggedIn: false,
  user: null,
})
