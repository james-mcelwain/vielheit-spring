import React from 'react'
import './HomeView.scss'
import SideMenu from '../../../components/Menu/Menu'

export const HomeView = () => (
  <div className="home-container">
    <SideMenu classname="home-menu"/>
    <div className="home-title">
      <div className="home-title-text" style={{}}>
        <i><b>the animal is in the world like water in water</b></i>
      </div>
    </div>
  </div>
)
export default HomeView
