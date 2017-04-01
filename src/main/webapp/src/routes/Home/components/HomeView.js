import React from 'react'
import './HomeView.scss'
import SideMenu from '../../../components/Menu/Menu'

export const HomeView = () => (
  <div className="home-container">
    <span className="home-menu"><SideMenu/></span>
    <div className="home-title">
      <div className="home-title-text">
        <i><b>the animal is in the world like water in water</b></i>
      </div>
    </div>
  </div>
)
export default HomeView
