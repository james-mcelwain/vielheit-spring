// ------------------------------------
// Constants
// ------------------------------------
import {AppAction} from './appAction'
import {State} from './appState'
import makeConstant from './makeConstant'
import {AppStore} from './store'

export const LOCATION_CHANGE = makeConstant('LOCATION_CHANGE')

// ------------------------------------
// Actions
// ------------------------------------
export function locationChange(location =  {}) : AppAction<LocationState> {
  return {
    type    : LOCATION_CHANGE(),
    payload : location,
  }
}

// ------------------------------------
// Specialized Action Creator
// ------------------------------------
export const updateLocation = ({ dispatch }: AppStore) => {
  return (nextLocation: any) => {
    dispatch(locationChange(nextLocation))
  }
}

// ------------------------------------
// Reducer
// ------------------------------------

interface LocationState extends State {}
const initialState: LocationState = {}

export default function locationReducer(state = initialState, action: AppAction<LocationState>) {
  return LOCATION_CHANGE.compare(action)
    ? action.payload
    : state
}
