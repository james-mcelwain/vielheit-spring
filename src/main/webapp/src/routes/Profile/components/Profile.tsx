import * as React from 'react'
import './Profile.scss'
import {ProfileState} from "../modules/profile"
import {UserState} from "../../../store/user"

export const Profile = (props: { user: UserState, profileState: ProfileState }) => (
  <div className="profile-container">
    <div>Name { props.user().fullName() }</div>
  </div>
)

export default Profile
