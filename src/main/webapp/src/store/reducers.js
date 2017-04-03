import {
  combineReducers
} from 'redux'
import locationReducer from './location'
import userReducer from './user'
import applicationReducer from './application'

export const makeRootReducer = (asyncReducers) => {
  return combineReducers({
    user: userReducer,
    application: applicationReducer,
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
