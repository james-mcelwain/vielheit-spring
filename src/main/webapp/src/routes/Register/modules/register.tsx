import {browserHistory} from 'react-router'
import {Dispatch} from 'redux'
import http from '../../../http'
import {AppState, State} from '../../../store/appState'
import {Action} from '../../../store/Action'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'

export const REGISTER_START = new Action('REGISTER_START', (state, action) => ({...state, error: null, loggingIn: true}))
export const REGISTER_SUCCESS = new Action('REGISTER_SUCCESS', (state, action) => ({...state, loggingIn: false, error: action.payload}))
export const REGISTER_FAIL = new Action('REGISTER_FAIL', (state, action) => ({...state, loggingIn: false, loggedIn: true}))

export interface RegisterUserRequest {
  emailAddress: string
  firstName: string
  lastName: string
  password: string
}

export interface RegisterState extends State {
  loggedIn: boolean,
  loggingIn: boolean,
  error: Error | null
}

class RegisterModule extends AbstractModule<RegisterState> {

  @AsyncDispatch
  public register({emailAddress, firstName, lastName, password}: RegisterUserRequest) {
    return async (dispatch: Dispatch<AppState>, getState: () => AppState) => {
      dispatch(REGISTER_START.dispatch())
      try {
        const response = await http.post(
          'auth/register',
          {emailAddress, firstName, lastName, password},
          {headers: {'Content-Type': 'application/json'}},
        )
        sessionStorage.setItem('token', response.data.token)
        localStorage.setItem('refreshToken', response.data.refreshToken)
        dispatch(REGISTER_SUCCESS.dispatch(response))
        browserHistory.push('/')
      } catch (err) {
        dispatch(REGISTER_FAIL.dispatch(err))
      }
    }
  }
}

export default new RegisterModule({
  error: null,
  loggedIn: false,
  loggingIn: false,
})
  .addAction(REGISTER_START)
  .addAction(REGISTER_SUCCESS)
  .addAction(REGISTER_FAIL)
