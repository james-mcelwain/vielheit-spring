import React from 'react'
import './Header.scss'

function buildHeader() {
  return new Array(160).fill('/').join()
}

const Header = React.createClass({
  render() {
    return (
      <div className="header-menu">{buildHeader()}</div>
    )
  },
})

export default Header
