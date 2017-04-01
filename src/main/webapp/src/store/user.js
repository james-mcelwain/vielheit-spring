import {LOGIN_SUCCESS} from "../routes/Login/modules/login"

export default function userReducer(state = {}, action) {
  if (action.type === LOGIN_SUCCESS) return action.payload
  if (action.type === LOGOUT) return {}
  return state
}

export const LOGOUT = 'LOGOUT'

