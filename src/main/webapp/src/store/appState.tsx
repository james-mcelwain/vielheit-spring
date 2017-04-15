import {RegisterState} from '../routes/Register/modules/register'
import {ApplicationState} from '../core/Application'

export interface AppState {
  [key: string]: State | null
  register: RegisterState
  application: ApplicationState
}

export interface State {

}

