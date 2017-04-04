import { LOGIN_SUCCESS } from '../routes/Login/modules/login'
import { LOGOUT } from './user'

export default function (state = { errors: [], loggedIn: false }, action) {
  if (action.type === RESPONSE_ERROR) {
    state.errors.push(action.payload)
  }

  if (action.type === LOGIN_SUCCESS) return { ...state, loggedIn: true }
  if (action.type === LOGOUT) return { ...state, loggedIn: false }

  return state
}

export const RESPONSE_ERROR = 'RESPONSE_ERROR'
