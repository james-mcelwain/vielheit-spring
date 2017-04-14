import {State, AppState} from './appState'
import {Action} from './Action'
import {AbstractModule, AsyncDispatch} from '../core/AbstractModule'
import {AppStore} from './store'
import {Location} from 'history'
import {Call} from '../core/Callable'
import {Reducer} from 'redux'

export const LOCATION_CHANGE = new Action('LOCATION_CHANGE')

interface LocationState extends State {
}

export default function updateLocation({dispatch}: AppStore) {
    return (nextLocation = {}) => {
      dispatch(LOCATION_CHANGE.dispatch(nextLocation))
    }
  }
