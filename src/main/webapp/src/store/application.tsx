import { LOGIN_SUCCESS } from '../routes/Login/modules/login'
import { LOGOUT } from './user'
import {AppAction} from "./appAction"
import makeConstant from "./makeConstant"
import {State} from "./appState"

export interface ApplicationState extends State {
  error: Error | null,
  loggedIn: boolean
}

export default function (state: ApplicationState = { error: null, loggedIn: false }, action: AppAction<ApplicationState> ) {
  if (action.payload instanceof Error && RESPONSE_ERROR.compare(action)) {
    state.error = action.payload
  }

  if (LOGIN_SUCCESS.compare(action)) return { ...state, loggedIn: true }
  if (LOGOUT.compare(action)) return { ...state, loggedIn: false }

  return state
}

export const RESPONSE_ERROR = makeConstant('RESPONSE_ERROR')
