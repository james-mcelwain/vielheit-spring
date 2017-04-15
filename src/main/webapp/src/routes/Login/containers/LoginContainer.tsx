import {connect, Dispatch} from 'react-redux'
import loginModule from '../modules/login'

import {AppState} from '../../../store/appState'
import Login from '../components/Login'
import {LoginState} from '../modules/login'

const mapDispatchToProps = loginModule.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
  loginState: state.login,
})

export interface LoginProps {
  loginState: LoginState,
  login: (...args: any[]) => (dispatch: Dispatch<LoginState>, getState: () => AppState) => any
}

export default connect(mapStateToProps, mapDispatchToProps)(Login)
