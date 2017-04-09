import { connect } from 'react-redux'
import {register, RegisterState} from '../modules/register'

import {ApplicationState} from '../../../store/application'
import {AppState} from '../../../store/appState'
import Register from '../components/Register'

const mapDispatchToProps = {
  register,
}

const mapStateToProps = (state: AppState) => ({
  registerState: state.register,
})

export default connect(mapStateToProps, mapDispatchToProps)(Register)
