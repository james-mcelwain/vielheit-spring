import React from 'react'
import Header from '../../components/Header'
import './CoreLayout.scss'
import '../../styles/core.scss'
import SideMenu from '../../components/Menu/Menu'

export const CoreLayout = ({ children }) => (
  <div className='container text-center'>
    <Header />
    <div className="app-container">
      <SideMenu />
      <div className='core-layout__viewport'>
        {children}
      </div>
    </div>
  </div>
)

CoreLayout.propTypes = {
  children: React.PropTypes.element.isRequired
}

export default CoreLayout
