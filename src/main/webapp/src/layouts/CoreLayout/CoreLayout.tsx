import * as React from 'react'
import Header from '../../components/Header'
import SideMenu from '../../components/Menu/Menu'
import { store } from '../../main'
import '../../styles/core.scss'
import './CoreLayout.scss'

export const CoreLayout = ({ children }: { children: JSX.Element[] }) => (
  <div className="container text-center">
    <Header />
    <div className="app-container">
      {store.getState().application.loggedIn && <SideMenu />}
      <div className="core-layout__viewport">
        {children}
      </div>
    </div>
  </div>
)

export default CoreLayout
