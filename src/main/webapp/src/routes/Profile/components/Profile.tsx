import * as React from 'react'
import {ProfileState} from '../modules/profile'
import './Profile.scss'
import User from '../../../domain/User'

export const Profile = (props: { user: User , profileState: ProfileState }) => (
  <div className="profile-container">
    <div>Name { props.user && props.user.fullName() }</div>
  </div>
)

export default Profile
