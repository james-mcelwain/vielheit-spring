import {browserHistory, PlainRoute} from 'react-router'
import {Dispatch, Reducer} from 'redux'
import User from '../../../domain/User'
import http from '../../../http'
import {AppAction} from '../../../store/appAction'
import {AppState} from '../../../store/appState'
import makeConstant from '../../../store/makeConstant';
import {AbstractModule} from '../../../util/module'

// ------------------------------------
// Constants
// ------------------------------------
export const LOGIN_START = makeConstant('LOGIN_START')
export const LOGIN_SUCCESS = makeConstant('LOGIN_SUCCESS')
export const LOGIN_FAIL = makeConstant('LOGIN_FAIL')

// ------------------------------------
// Actions
// ------------------------------------

export interface LoginUserRequest {
  emailAddress: string
  password: string
}
export const login = ({emailAddress, password}: LoginUserRequest) => {
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

export const actions = {
  login,
}

export interface LoginState {
  loggedIn: boolean
  loggingIn: boolean
  error: null | Error
}

const initialState = {
  error: null,
  loggedIn: false,
  loggingIn: false,
}

class LoginModule extends AbstractModule<LoginState> {
  public state: LoginState

  protected ACTION_HANDLERS: {[p: string]: (state: LoginState, action?: AppAction<LoginState>) => LoginState} = {
    [LOGIN_START()]: (state) => ({...state, error: null, loggingIn: true}),
    [LOGIN_FAIL()]: (state, action) =>
      ({...state, loggingIn: false, error: action && action.payload instanceof Error ? action.payload : null }),
    [LOGIN_SUCCESS()]: (state) => ({...state, loggingIn: false, loggedIn: true}),
  }
}
const module = new LoginModule(initialState)

export default LoginModule.New<LoginState>(module)
