import {
  combineReducers
} from 'redux'
import locationReducer from './location'
import {LOGIN_SUCCESS} from "../routes/Login/modules/login"

export const makeRootReducer = (asyncReducers) => {
  return combineReducers({
    // TODO: cleanup
    user: (state = {}, action) => action.type === LOGIN_SUCCESS ? action.payload : state,
    location: locationReducer,
    ...asyncReducers
  })
}

export const injectReducer = (store, {
  key,
  reducer
}) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) return

  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
