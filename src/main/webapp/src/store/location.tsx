// ------------------------------------
// Constants
// ------------------------------------
import {State} from "./appState"
import {AppAction} from "./appAction"
import {AppStore} from "./store"
import {Location} from 'history'
import makeConstant from "./makeConstant"

export const LOCATION_CHANGE = makeConstant('LOCATION_CHANGE')

// ------------------------------------
// Actions
// ------------------------------------
export function locationChange (location =  {}) : AppAction<LocationState> {
  return {
    type    : LOCATION_CHANGE(),
    payload : location
  }
}

// ------------------------------------
// Specialized Action Creator
// ------------------------------------
export const updateLocation = ({ dispatch }: AppStore) => {
  return (nextLocation: Location) => dispatch(locationChange(nextLocation))
}

// ------------------------------------
// Reducer
// ------------------------------------

interface LocationState extends State {}
const initialState: LocationState = {}

export default function locationReducer (state = initialState, action: AppAction<LocationState>) {
  return LOCATION_CHANGE.compare(action)
    ? action.payload
    : state
}
