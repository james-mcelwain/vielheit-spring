import { connect } from 'react-redux'
import { profile } from '../modules/profile'

import {AppState} from '../../../store/appState'
import Profile from '../components/Profile'

const mapDispatchToProps = {
  profile,
}

const mapStateToProps = (state: AppState) => ({
  profileState: state.profile,
  user: state.user,
})

export default connect(mapStateToProps, mapDispatchToProps)(Profile)
