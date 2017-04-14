import {RegisterState} from '../routes/Register/modules/register'
import {ApplicationState} from './application'
import {UserState} from './user'

export interface AppState {
  [key: string]: State | null
  register: RegisterState
  application: ApplicationState
  user: UserState | null
}

export interface State {

}
