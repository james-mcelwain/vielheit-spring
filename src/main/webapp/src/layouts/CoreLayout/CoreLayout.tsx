import React from 'react'
import Header from '../../components/Header'
import './CoreLayout.scss'
import '../../styles/core.scss'
import SideMenu from '../../components/Menu/Menu'
import { store } from '../../main'

export const CoreLayout = ({ children }: { children: JSX.Element[] }) => (
  <div className='container text-center'>
    <Header />
    <div className="app-container">
      {store.getState().application.loggedIn && <SideMenu />}
      <div className='core-layout__viewport'>
        {children}
      </div>
    </div>
  </div>
)

export default CoreLayout
