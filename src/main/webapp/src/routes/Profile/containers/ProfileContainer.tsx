import { connect } from 'react-redux'
import { profile } from '../modules/profile'

import Profile from '../components/Profile'
import {AppState} from "../../../store/appState"

const mapDispatchToProps = {
  profile
}

const mapStateToProps = (state: AppState) => ({
  profileState: state.profile,
  user: state.user
})

export default connect(mapStateToProps, mapDispatchToProps)(Profile)
