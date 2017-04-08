import { connect } from 'react-redux'
import {register, RegisterState} from '../modules/register'

import Register from '../components/Register'
import {ApplicationState} from "../../../store/application"
import {AppState} from "../../../store/appState"

const mapDispatchToProps = {
  register
}

const mapStateToProps = (state: AppState) => ({
  registerState: state.register
})

export default connect(mapStateToProps, mapDispatchToProps)(Register)
