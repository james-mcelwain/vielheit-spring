import { connect } from 'react-redux'
import { login } from '../modules/login'

import {AppState} from '../../../store/appState'
import Login from '../components/Login'

const mapDispatchToProps = {
  login,
}

const mapStateToProps = (state: AppState) => ({
  loginState: state.login,
})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
