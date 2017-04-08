import http from '../../../http'
import {browserHistory, PlainRoute} from 'react-router'
import User from '../../../domain/User'
import {AppAction} from "../../../store/appAction"
import {Dispatch} from "redux"
import {AppState} from "../../../store/appState"
import makeConstant from "../../../store/makeConstant";

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
export const login = ({ emailAddress, password }: LoginUserRequest) => {
  return (dispatch: Dispatch<LoginState>, getState: () => AppState) =>
  dispatch({ type: LOGIN_START }) &&
  http.post(
    'auth/login',
    { emailAddress, password },
    { headers: { 'Content-Type': 'application/json' } }
    )
    .then(({ data: { token, refreshToken, user } }) => {
      sessionStorage.setItem('token', token)
      sessionStorage.setItem('refreshToken', refreshToken)
      sessionStorage.setItem('user', JSON.stringify(user))
      dispatch({
        type: LOGIN_SUCCESS,
        payload: new User(user)
      })
      browserHistory.push('/')
    })
    .catch((err) => {
      sessionStorage.clear()
      dispatch({
        type: LOGIN_FAIL,
        payload: err
      })
    })
}

export const actions = {
  login
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS: { [key: string]: (state: LoginState, action: AppAction<LoginState>) => LoginState } = {
  [LOGIN_START()]: (state, action) => ({ ...state, error: null, loggingIn: true } as LoginState),
  [LOGIN_FAIL()]: (state, action) => ({ ...state, loggingIn: false, error: action.payload } as LoginState),
  [LOGIN_SUCCESS()]: (state, action) => ({ ...state, loggingIn: false, loggedIn: true } as LoginState)
}

// ------------------------------------
// Reducer
// ------------------------------------

export interface LoginState {
  loggedIn: boolean
  loggingIn: boolean
  error: null | Error
}
const initialState = {
  loggedIn: false,
  loggingIn: false,
  error: null,
}

export default function loginReducer(state = initialState, action: AppAction<LoginState>) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
