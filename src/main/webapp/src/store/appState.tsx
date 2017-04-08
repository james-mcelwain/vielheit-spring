import {RegisterState} from "../routes/Register/modules/register"
import {ApplicationState} from "./application"
import {UserState} from "./user"

export interface AppState {
  [key: string]: State
  register: RegisterState
  application: ApplicationState
  user: UserState
}

export interface State {

}
