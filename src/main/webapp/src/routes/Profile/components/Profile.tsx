import * as React from 'react'
import {UserState} from '../../../store/user'
import {ProfileState} from '../modules/profile'
import './Profile.scss'

export const Profile = (props: { user: UserState, profileState: ProfileState }) => (
  <div className="profile-container">
    <div>Name { props.user().fullName() }</div>
  </div>
)

export default Profile
