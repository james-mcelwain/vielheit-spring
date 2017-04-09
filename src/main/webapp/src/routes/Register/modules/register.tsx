import { browserHistory } from 'react-router'
import {Dispatch} from 'redux'
import http from '../../../http'
import {AppAction} from '../../../store/appAction'
import {AppState, State} from '../../../store/appState'
import makeConstant, {ActionType} from '../../../store/makeConstant'

// ------------------------------------
// Constants
// ------------------------------------
export const REGISTER_START = makeConstant('REGISTER_START')
export const REGISTER_SUCCESS = makeConstant('REGISTER_SUCCESS')
export const REGISTER_FAIL = makeConstant('REGISTER_FAIL')

// ------------------------------------
// Actions
// ------------------------------------
export interface RegisterUserRequest {
  emailAddress: string
  firstName: string
  lastName: string
  password: string
}

export const register = ({ emailAddress, firstName, lastName, password }: RegisterUserRequest) =>  {
  return (dispatch: Dispatch<AppState>, getState: () => AppState) =>
    dispatch({ type: REGISTER_START }) &&
    http.post(
      'auth/register',
      { emailAddress, firstName, lastName, password },
      { headers: { 'Content-Type': 'application/json' } },
      )
      .then((response) => {
        sessionStorage.setItem('token', response.data.token)
        localStorage.setItem('refreshToken', response.data.refreshToken)
        dispatch({
          type: REGISTER_SUCCESS,
          payload: response,
        })
        browserHistory.push('/')
      })
      .catch((err) => {
        dispatch({
          type: REGISTER_FAIL,
          payload: err,
        })
      })
}

export const actions = {
  register,
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS: { [k: string]: (state: RegisterState, action: AppAction<RegisterState>) => RegisterState } = {
  [REGISTER_START()]          : (state, action) => ({ ...state, error: null, loggingIn: true } as RegisterState),
  [REGISTER_FAIL()]           : (state, action) => ({ ...state, loggingIn: false, error: action.payload } as RegisterState),
  [REGISTER_SUCCESS()]        : (state, action) => ({ ...state, loggingIn: false, loggedIn: true } as RegisterState),
}

// ------------------------------------
// Reducer
// ------------------------------------
export interface RegisterState extends State {
  loggedIn: boolean,
  loggingIn: boolean,
  error: Error | null
}
const initialState: RegisterState = {
  loggedIn: false,
  loggingIn: false,
  error: null,
}
export default function registerReducer(state = initialState, action: AppAction<RegisterState>) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
