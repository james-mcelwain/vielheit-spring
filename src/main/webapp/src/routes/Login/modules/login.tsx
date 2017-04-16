import {browserHistory, PlainRoute} from 'react-router'
import {Dispatch, Reducer} from 'redux'
import User from '../../../domain/User'
import http from '../../../http'
import {AppState} from '../../../store/appState'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'
import {ActionCreator} from '../../../core/ActionCreator'
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

export class LoginModule extends AbstractModule<LoginState> {
  public LOGIN_START = this.Action('LOGIN_START', (state) => ({...state, error: null, loggingIn: true}))
  public LOGIN_SUCCESS = this.Action('LOGIN_SUCCESS', (state) => ({
    ...state,
    loggingIn: false,
    loggedIn: true,
  }))
  public LOGIN_FAIL = this.Action<Error>('LOGIN_FAIL', (state, payload) => ({
    ...state,
    loggingIn: false,
    error: payload,
  }))

  @AsyncDispatch
  public login({emailAddress, password}: LoginUserRequest) {
    return async (dispatch: Dispatch<LoginState>) => {
      dispatch(this.LOGIN_START.dispatch())
      try {
        const {data: {token, refreshToken, user}} = await http.post(
          'auth/login',
          {emailAddress, password},
          {headers: {'Content-Type': 'application/json'}},
        )

        sessionStorage.setItem('token', token)
        sessionStorage.setItem('refreshToken', refreshToken)
        sessionStorage.setItem('user', JSON.stringify(user))
        dispatch(this.LOGIN_SUCCESS.dispatch(new User(user)))

        browserHistory.push('/')
      } catch (err) {
        sessionStorage.clear()
        dispatch(this.LOGIN_FAIL.dispatch(err))
      }
    }
  }
}

export default new LoginModule({
  error: null,
  loggedIn: false,
  loggingIn: false,
})
