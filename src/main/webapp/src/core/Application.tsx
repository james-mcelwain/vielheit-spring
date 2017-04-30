import {State} from '../store/appState'
import {AbstractModule} from './AbstractModule'
import User from '../domain/User'
import {DispatchedAction} from '../store/DispatchedAction'

export interface ApplicationState extends State {
  error: Error | null,
  loggedIn: boolean,
  user: User | null,
  bootstrapped: boolean
}

class Application extends AbstractModule<ApplicationState> {
  public RESPONSE_ERROR = this.Action<Error>('RESPONSE_ERROR', (state, payload) => {
    this.message('error', 'An error occured! ' + payload)
    return {error: payload, ...state}
  })
  public LOGOUT = this.Action('LOGOUT', (state) => ({ ...state, loggedIn: false, user: null }))
  public LOGIN = this.Action<User>('LOGIN', (state, payload) => ({ ...state, loggedIn: true, user: payload  }))
  public BOOTSTRAP = this.Action('BOOTSTRAP', (s) => {
    this.toBootstrap.forEach((fn) => fn())

    s.bootstrapped = true
    return s
  })
  private toBootstrap: any[] = []

  public onBootstrap(fn: Function) {
    this.toBootstrap.push(fn)
  }

  public reducer(s: ApplicationState, action: DispatchedAction<ApplicationState>): ApplicationState {
    s = super.reducer(s, action)
    if (action.type === 'LOGIN_SUCCESS') {
      s = {...s, loggedIn: true, user: (action.payload as User) }
    }
    return s
  }
}

const App = new Application({
  error: null,
  loggedIn: false,
  user: null,
  bootstrapped: false,
})

App.onBootstrap(() => {
    const $cover = document.querySelector('#cover')
    if ($cover) {
      setTimeout(() => {
        $cover.classList.toggle('fade')
      }, 200)
    }
})
export default App;
