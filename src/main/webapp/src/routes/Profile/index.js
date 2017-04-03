import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path: 'profile',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Profile = require('./containers/ProfileContainer').default
      const profile = require('./modules/profile').default

      injectReducer(store, { key: 'profile', profile })
      cb(null, Profile)
    }, 'profile')
  }
})
