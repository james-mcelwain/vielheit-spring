import User from '../domain/User'
import {store} from '../main'
import {LOGIN_SUCCESS} from '../routes/Login/modules/login'
import {AppAction} from './appAction'
import {State} from './appState';
import makeConstant from './makeConstant'

export interface UserState extends State {
  (): User // panic!
  currentUser: User | null
  id(): number // panic!
}
const initialState: UserState = Object.assign(() => {
    const user = this.currentUser
    if (!user) {
      store.dispatch(LOGOUT.toAction())
      throw Error('user() called on null user')
    }

    return user
  }, {
  currentUser: null,
  id() {
    const id = this.currentUser && this.currentUser.id

    if (!id)  {
      store.dispatch(LOGOUT.toAction())
      throw Error('id() called on null user')
    }
    return id
  },
})
export default function UserReducer(state = initialState, action: AppAction<UserState>): UserState {
  if (action.payload instanceof User && LOGIN_SUCCESS.compare(action)) {
    state.currentUser =  action.payload
  }

  if (LOGOUT.compare(action)) {
    state.currentUser = null
  }

  return state
}

export const LOGOUT = makeConstant<UserState>('LOGOUT')
