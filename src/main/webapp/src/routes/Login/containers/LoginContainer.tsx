import { connect } from 'react-redux'
import loginModule from '../modules/login'

import {AppState} from '../../../store/appState'
import Login from '../components/Login'

const mapDispatchToProps = {
  login: loginModule.login,
}

const mapStateToProps = (state: AppState) => ({
  loginState: state.login,
})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
