import http from '../../../http'
import { browserHistory } from 'react-router'

// ------------------------------------
// Constants
// ------------------------------------
export const LOGIN_START = 'LOGIN_START'
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS'
export const LOGIN_FAIL = 'LOGIN_FAIL'

// ------------------------------------
// Actions
// ------------------------------------
export const login = ({ emailAddress, password }) =>  {
  return (dispatch, getState) =>
    dispatch({ type: LOGIN_START }) &&
    http.post(
      'auth/login',
      { emailAddress, password },
      { headers: { 'Content-Type': 'application/json' } }
      )
      .then((response) => {
        sessionStorage.setItem("token", response.data.token)
        localStorage.setItem("refreshToken", response.data.refreshToken)
        dispatch({
          type: LOGIN_SUCCESS,
          payload: response
        })
        browserHistory.push('/')
      })
      .catch((err) => {
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
const ACTION_HANDLERS = {
  [LOGIN_START]          : (state, action) => ({ ...state, error: null, loggingIn: true }),
  [LOGIN_FAIL]           : (state, action) => ({ ...state, loggingIn: false, error: action.payload }),
  [LOGIN_SUCCESS]        : (state, action) => ({ ...state, loggingIn: false, loggedIn: true })
}

// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  loggedIn: false,
  loggingIn: false,
  error: null,
}
export default function loginReducer(state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
