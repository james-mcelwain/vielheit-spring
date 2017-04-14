import {browserHistory, PlainRoute} from 'react-router'
import {Dispatch, Reducer} from 'redux'
import User from '../../../domain/User'
import http from '../../../http'
import {AppState} from '../../../store/appState'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'
import {Action} from '../../../store/Action'
import construct = Reflect.construct

export interface LoginUserRequest {
  emailAddress: string
  password: string
}

export interface LoginState {
  loggedIn: boolean
  loggingIn: boolean
  error: null | Error,
}

class LoginModule extends AbstractModule<LoginState> {

  @AsyncDispatch
  public login({emailAddress, password}: LoginUserRequest) {
    return async (dispatch: Dispatch<LoginState>, getState: () => AppState) => {
      dispatch(LOGIN_START.dispatch())
      try {
        const {data: {token, refreshToken, user}} = await http.post(
          'auth/login',
          {emailAddress, password},
          {headers: {'Content-Type': 'application/json'}},
        )

        sessionStorage.setItem('token', token)
        sessionStorage.setItem('refreshToken', refreshToken)
        sessionStorage.setItem('user', JSON.stringify(user))
        dispatch(LOGIN_SUCCESS.dispatch(new User(user)))

        browserHistory.push('/')
      } catch (err) {
        sessionStorage.clear()
        dispatch({
          payload: err,
          type: LOGIN_FAIL(),
        })
      }
    }
  }
}

export const login = new LoginModule({
  error: null,
  loggedIn: false,
  loggingIn: false,
})

export const LOGIN_START = new Action<LoginState>('LOGIN_START', (state) => ({...state, error: null, loggingIn: true}))
export const LOGIN_SUCCESS = new Action<LoginState>('LOGIN_SUCCESS', (state) => ({...state, loggingIn: false, loggedIn: true}))
export const LOGIN_FAIL = new Action<LoginState>('LOGIN_FAIL', (state, payload) => ({
  ...state,
  loggingIn: false,
  error: payload && payload instanceof Error ? payload : null,
}))

export default login
  .addAction(LOGIN_START)
  .addAction(LOGIN_SUCCESS)
  .addAction(LOGIN_FAIL)
