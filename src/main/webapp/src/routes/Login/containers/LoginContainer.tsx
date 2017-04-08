import { connect } from 'react-redux'
import { login } from '../modules/login'

import Login from '../components/Login'
import {AppState} from "../../../store/appState"

const mapDispatchToProps = {
  login
}

const mapStateToProps = (state: AppState) => ({
  loginState: state.login,
})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
