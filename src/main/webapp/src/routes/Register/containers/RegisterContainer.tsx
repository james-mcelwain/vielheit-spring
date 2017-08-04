import { connect } from 'react-redux'
import register from '../modules/register'

import {AppState} from '../../../store/appState'
import Register from '../components/Register'

const mapDispatchToProps = register.getAsyncActions()

const mapStateToProps = (state: AppState) => ({
   register: state.register,
})

export default connect(mapStateToProps, mapDispatchToProps)(Register)
