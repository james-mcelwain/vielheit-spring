import {browserHistory, PlainRoute} from 'react-router'
import {Dispatch, Reducer} from 'redux'
import User from '../../../domain/User'
import http from '../../../http'
import {AppAction} from '../../../store/appAction'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/makeConstant';
import {AbstractModule} from '../../../core/AbstractModule'

export const LOGIN_START = makeConstant('LOGIN_START')
export const LOGIN_SUCCESS = makeConstant('LOGIN_SUCCESS')
export const LOGIN_FAIL = makeConstant('LOGIN_FAIL')

export interface LoginUserRequest {
  emailAddress: string
  password: string
}

export interface LoginState {
  loggedIn: boolean
  loggingIn: boolean
  error: null | Error
}

class LoginModule extends AbstractModule<LoginState> {
  public state: LoginState

  public login({emailAddress, password}: LoginUserRequest) {
    return (dispatch: Dispatch<LoginState>, getState: () => AppState) =>
    dispatch({type: LOGIN_START()}) &&
    http.post(
      'auth/login',
      {emailAddress, password},
      {headers: {'Content-Type': 'application/json'}},
    )
      .then(({data: {token, refreshToken, user}}) => {
        sessionStorage.setItem('token', token)
        sessionStorage.setItem('refreshToken', refreshToken)
        sessionStorage.setItem('user', JSON.stringify(user))
        dispatch({
          payload: new User(user),
          type: LOGIN_SUCCESS(),
        })
        browserHistory.push('/')
      })
      .catch((err) => {
        sessionStorage.clear()
        dispatch({
          payload: err,
          type: LOGIN_FAIL(),
        })
      })
  }
}

export default new LoginModule({
  error: null,
  loggedIn: false,
  loggingIn: false,
})
  .addAction(LOGIN_START, (state) => ({...state, error: null, loggingIn: true}))
  .addAction(LOGIN_FAIL, (state, action) => ({
    ...state,
    loggingIn: false,
    error: action && action.payload instanceof Error ? action.payload : null,
  }))
  .addAction(LOGIN_SUCCESS, (state) => ({...state, loggingIn: false, loggedIn: true}))
  .toReducer()
