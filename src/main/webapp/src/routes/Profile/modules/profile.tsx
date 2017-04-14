// ------------------------------------
// Constants
// ------------------------------------

// ------------------------------------
// Actions
// ------------------------------------
import {DispatchedAction} from '../../../store/DispatchedAction'
import {State} from '../../../store/appState'
export const actions = {
}

export function profile() {
  // pass
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS: {
  [key: string]: (state: ProfileState, action: DispatchedAction<ProfileState>) => void,
} = {
}

// ------------------------------------
// Reducer
// ------------------------------------

export interface ProfileState extends State {}
const initialState: ProfileState = {}

export default function profileReducer(state = initialState, action: DispatchedAction<ProfileState>) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
