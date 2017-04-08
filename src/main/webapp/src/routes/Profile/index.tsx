import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"
import {Reducer} from "redux"
import {ProfileState} from "./modules/profile";

declare const require: any

export default (store: AppStore) => ({
  path: 'profile',
  getComponent (nextState, cb) {
    require.ensure([], (require: any) => {
      const Profile = require('./containers/ProfileContainer').default
      const reducer: Reducer<ProfileState> = require('./modules/profile').default

      injectReducer(store, { key: 'profile', reducer })
      cb(null, Profile)
    }, 'profile')
  }
} as PlainRoute)
