import React from 'react'
import './Header.scss'

function buildHeader() {
  console.log('fire')
  return new Array(Math.round(window.innerWidth / 8.3)).fill('/').join()
}


const Header = React.createClass({
  componentDidMount() {
    this.setState({title: buildHeader()})
    window.addEventListener("resize", () => {
      this.setState({title: buildHeader()})
    })
  },
  render() {
    console.log(this)
    return (
      <div className="header-menu">{this.state && this.state.title}</div>
    )
  },
})

export default Header
