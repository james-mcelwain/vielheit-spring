import { connect } from 'react-redux'
import profileModule from '../modules/profile'

import {AppState} from 'store/appState'
import Profile from '../components/Profile'

const mapDispatchToProps = profileModule.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
  profileState: state.profile,
  application: state.application,
})

export default connect(mapStateToProps, mapDispatchToProps)(Profile)
