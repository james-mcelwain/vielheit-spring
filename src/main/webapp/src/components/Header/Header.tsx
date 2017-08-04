import * as React from 'react'
import './Header.scss'

function buildHeader() {
  return new Array(Math.round(window.innerWidth / 8.3)).fill('/').join('.')
}

class Header extends React.Component<any, any> {
  public componentDidMount() {
    this.setState({title: buildHeader()})
    window.addEventListener('resize', () => {
      this.setState({title: buildHeader()})
    })
  }

  public render() {
    return (
      <div className="header-menu">{this.state && this.state.title}</div>
    )
  }
}

export default Header
