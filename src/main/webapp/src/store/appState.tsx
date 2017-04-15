import {RegisterState} from '../routes/Register/modules/register'
import {ApplicationState} from '../core/Application'
import {LoginState} from '../routes/Login/modules/login'

export interface AppState {
  [key: string]: State | null
  register: RegisterState
  application: ApplicationState
  login: LoginState
}

export interface State {

}

