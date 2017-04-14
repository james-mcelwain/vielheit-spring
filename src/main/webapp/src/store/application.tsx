import { LOGIN_SUCCESS } from '../routes/Login/modules/login'
import {DispatchedAction} from './DispatchedAction'
import {State} from './appState'
import makeConstant from './Action'
import { LOGOUT } from './user'
import {Action} from './Action'

export interface ApplicationState extends State {
  error: Error | null,
  loggedIn: boolean
}

export default function(state: ApplicationState = { error: null, loggedIn: false }, action: DispatchedAction<ApplicationState> ) {
  if (action.payload instanceof Error && RESPONSE_ERROR.compare(action)) {
    state.error = action.payload
  }

  if (LOGIN_SUCCESS.compare(action)) {
   state.loggedIn = true
  }
  if (LOGOUT.compare(action)) {
    state.loggedIn = false
  }

  return state
}

export const RESPONSE_ERROR = new Action('RESPONSE_ERROR')
