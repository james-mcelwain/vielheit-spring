import {browserHistory} from 'react-router'
import {Dispatch} from 'redux'
import http from '../../../http'
import {AppState, State} from '../../../store/appState'
import {AbstractModule, AsyncDispatch} from '../../../core/AbstractModule'


export interface RegisterUserRequest {
  emailAddress: string
  firstName: string
  lastName: string
  password: string
}

export interface RegisterState extends State {
  error: Error | null
}

class Register extends AbstractModule<RegisterState> {
  public REGISTER_START = this.Action('REGISTER_START', (state) => ({...state }))
  public REGISTER_SUCCESS = this.Action('REGISTER_SUCCESS', (state) => ({...state }))
  public REGISTER_FAIL = this.Action<Error>('REGISTER_FAIL', (state, payload) => ({...state, error: payload }))

  @AsyncDispatch
  public register({emailAddress, firstName, lastName, password}: RegisterUserRequest) {
    return async(dispatch: Dispatch<AppState>) => {
      dispatch(this.REGISTER_START.dispatch())
      try {
        const response = await http.post(
          'auth/register',
          {emailAddress, firstName, lastName, password},
          {headers: {'Content-Type': 'application/json'}},
        )
        sessionStorage.setItem('token', response.data.token)
        localStorage.setItem('refreshToken', response.data.refreshToken)
        dispatch(this.REGISTER_SUCCESS.dispatch(response))
        browserHistory.push('/')
      } catch (err) {
        dispatch(this.REGISTER_FAIL.dispatch(err))
      }
    }
  }
}

export default new Register({
  error: null,
})
