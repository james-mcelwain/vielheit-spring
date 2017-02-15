import { connect } from 'react-redux'
import { register } from '../modules/register'

import Register from '../components/Register'

const mapDispatchToProps = {
  register
}

const mapStateToProps = (state) => ({
  registerState: state.register
})

export default connect(mapStateToProps, mapDispatchToProps)(Register)
