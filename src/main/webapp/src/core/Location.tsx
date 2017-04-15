import {State} from '../store/appState'
import {AbstractModule, AsyncDispatch} from './AbstractModule'
import {AppStore} from '../store/store'

interface LocationState extends State {
}
class Location extends AbstractModule<LocationState> {
  public LOCATION_CHANGE = this.Action('LOCATION_CHANGE')

  @AsyncDispatch
  public updateLocation({dispatch}: AppStore) {
    return (nextLocation = {}) => {
      dispatch(this.LOCATION_CHANGE.dispatch(nextLocation))
    }
  }
}

export default new Location({})
