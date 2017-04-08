import React from 'react'
import './Profile.scss'
import {ProfileState} from "../modules/profile"
import User from "../../../domain/User"
import {UserState} from "../../../store/user"

export const Profile = (props: { user: UserState, profileState: ProfileState }) => (
  <div className="profile-container">
    <div>Name { props.user.currentUser && props.user.currentUser.fullName() }</div>
  </div>
)

export default Profile
