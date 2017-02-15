import http from '../../../http'
import { browserHistory } from 'react-router'

// ------------------------------------
// Constants
// ------------------------------------
export const REGISTER_START = 'REGISTER_START'
export const REGISTER_SUCCESS = 'REGISTER_SUCCESS'
export const REGISTER_FAIL = 'REGISTER_FAIL'

// ------------------------------------
// Actions
// ------------------------------------
export const register = ({ emailAddress, firstName, lastName, password }) =>  {
  return (dispatch, getState) =>
    dispatch({ type: REGISTER_START }) &&
    http.post(
      'auth/register',
      { emailAddress, firstName, lastName, password },
      { headers: { 'Content-Type': 'application/json' } }
      )
      .then((response) => {
        sessionStorage.setItem("token", response.data.token)
        localStorage.setItem("refreshToken", response.data.refreshToken)
        dispatch({
          type: REGISTER_SUCCESS,
          payload: response
        })
        browserHistory.push('/')
      })
      .catch((err) => {
        dispatch({
          type: REGISTER_FAIL,
          payload: err
        })
      })
}

export const actions = {
  register
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [REGISTER_START]          : (state, action) => ({ ...state, error: null, loggingIn: true }),
  [REGISTER_FAIL]           : (state, action) => ({ ...state, loggingIn: false, error: action.payload }),
  [REGISTER_SUCCESS]        : (state, action) => ({ ...state, loggingIn: false, loggedIn: true })
}

// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  loggedIn: false,
  loggingIn: false,
  error: null,
}
export default function registerReducer(state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
