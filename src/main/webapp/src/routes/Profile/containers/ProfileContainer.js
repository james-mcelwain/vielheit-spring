import { connect } from 'react-redux'
import { profile } from '../modules/profile'

import Profile from '../components/Profile'

const mapDispatchToProps = {
  profile
}

const mapStateToProps = (state) => ({
  profileState: state.profile,
  user: state.user
})

export default connect(mapStateToProps, mapDispatchToProps)(Profile)
