import {State, AppState} from './appState'
import {Action} from './Action'
import {AbstractModule, AsyncDispatch} from '../core/AbstractModule'
import {AppStore} from './store'
import {Location} from 'history'

export const LOCATION_CHANGE = new Action('LOCATION_CHANGE', () => { /*pass*/ })

interface LocationState extends State {
}
class LocationModule extends AbstractModule<LocationState> {
  public updateLocation({dispatch}: AppStore) {
    return (nextLocation = {}) => {
      dispatch(LOCATION_CHANGE.dispatch(nextLocation))
    }
  }
}

export default new LocationModule({})
  .addAction(LOCATION_CHANGE)
  .toReducer()
