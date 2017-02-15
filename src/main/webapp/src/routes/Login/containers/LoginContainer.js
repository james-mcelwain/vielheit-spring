import { connect } from 'react-redux'
import { login } from '../modules/login'

import Login from '../components/Login'

const mapDispatchToProps = {
  login
}

const mapStateToProps = (state) => ({
  loginState: state.login,
})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
