import React from 'react'
import './Profile.scss'

export const Profile = (props) => (
  <div className="profile-container">
    <div>Name { user.firstName + user.lastName }</div>
  </div>
)

Profile.propTypes = {
}

export default Profile
