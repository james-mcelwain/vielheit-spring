import React from 'react'
import './Profile.scss'

export const Profile = (props) => console.log(props) || (
  <div className="profile-container">
    <div>Name { props.user.fullName }</div>
  </div>
)

Profile.propTypes = {
}

export default Profile
