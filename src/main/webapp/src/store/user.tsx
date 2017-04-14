import User from '../domain/User'
import {store} from '../main'
import {LOGIN_SUCCESS} from '../routes/Login/modules/login'
import {DispatchedAction} from './DispatchedAction'
import {State} from './appState';
import makeConstant from './Action'
import {Action} from './Action'


export type UserState = User

const initialState: UserState | null = Object.assign(function() {
    const user = this.currentUser
    if (!user) {
      store.dispatch(LOGOUT.dispatch())
      throw Error('user() called on null user')
    }

    return user
  }, {
  currentUser: null,
  id() {
    const id = this.currentUser && this.currentUser.id

    if (!id)  {
      store.dispatch(LOGOUT.dispatch())
      throw Error('id() called on null user')
    }
    return id
  },
})

export default function UserReducer(state = initialState, action: DispatchedAction<UserState>): UserState {
  if (action.payload instanceof User && LOGIN_SUCCESS.compare(action)) {
    state =  action.payload
  }

  if (LOGOUT.compare(action)) {
    state = null
  }

  return state
}

export const LOGOUT = new Action('LOGOUT', () => { /*pass*/ })
