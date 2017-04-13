import {browserHistory, PlainRoute} from 'react-router'
import {Dispatch, Reducer} from 'redux'
import User from '../../../domain/User'
import http from '../../../http'
import {AppAction} from '../../../store/Action'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/makeConstant';
import {AbstractModule} from '../../../core/AbstractModule'
import {ActionConstant} from '../../../store/makeConstant'



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
  public login({emailAddress, password}: LoginUserRequest) {
    return this.asyncAction(async (dispatch: Dispatch<LoginState>, getState: () => AppState) => {
      dispatch({type: LOGIN_START()})
      try {
        const {data: {token, refreshToken, user}} = await http.post(
          'auth/login',
          {emailAddress, password},
          {headers: {'Content-Type': 'application/json'}},
        )

        sessionStorage.setItem('token', token)
        sessionStorage.setItem('refreshToken', refreshToken)
        sessionStorage.setItem('user', JSON.stringify(user))
        dispatch({
          payload: new User(user),
          type: LOGIN_SUCCESS(),
        })
        browserHistory.push('/')
      } catch (err) {
        sessionStorage.clear()
        dispatch({
          payload: err,
          type: LOGIN_FAIL(),
        })
      }
    })
  }
}

export const module = new LoginModule({
  error: null,
  loggedIn: false,
  loggingIn: false,
})

export const LOGIN_START = makeConstant<LoginState>('LOGIN_START', (state) => ({...state, error: null, loggingIn: true}))
export const LOGIN_SUCCESS = makeConstant<LoginState>('LOGIN_SUCCESS', (state) => ({...state, loggingIn: false, loggedIn: true}))
export const LOGIN_FAIL = makeConstant<LoginState>('LOGIN_FAIL', (state, payload) => ({
  ...state,
  loggingIn: false,
  error: payload && payload instanceof Error ? payload : null,
}))

export default module
  .addAction(LOGIN_START)
  .addAction(LOGIN_SUCCESS)
  .addAction(LOGIN_FAIL)
  .toReducer()
