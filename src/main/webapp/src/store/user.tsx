import {LOGIN_SUCCESS} from "../routes/Login/modules/login"
import {AppAction} from "./appAction"
import makeConstant from "./makeConstant"
import User from "../domain/User"
import {State} from "./appState";

export interface UserState extends State {
  currentUser: User | null
  id(): number // panic!
}
const initialState: UserState = {
  currentUser: null,
  id() {
    const id = this.currentUser && this.currentUser.id

    if (!id) throw Error('id() called on null user')
    return id
  }
}
export default function UserReducer(state = initialState, action: AppAction<UserState>): UserState {
  if (action.payload instanceof User && LOGIN_SUCCESS.compare(action)) {
    state.currentUser =  action.payload
  }

  if (LOGOUT.compare(action)) return { currentUser: null, ...state }
  return state
}

export const LOGOUT = makeConstant('LOGOUT')

