import React from 'react'
import { Menu, Icon } from 'antd';
import { browserHistory, Link } from 'react-router'
import { LOGOUT } from '../../store/user'
import { store } from '../../main'

const SubMenu = Menu.SubMenu;

export default class Sider extends React.Component {
  state = {
    current: '1',
    openKeys: [],
  }
  handleClick = (e) => {
    switch (e.key) {
      case "Logout":
        store.dispatch({
          type: LOGOUT
        })
        sessionStorage.clear()
        browserHistory.go('/login')
        break
      case "Profile":
        break
      default:
        throw new Error("INVARIANT")
    }

    this.setState({ current: e.key });
  }
  onOpenChange = (openKeys) => {
    const state = this.state;
    const latestOpenKey = openKeys.find(key => !(state.openKeys.indexOf(key) > -1));
    const latestCloseKey = state.openKeys.find(key => !(openKeys.indexOf(key) > -1));

    let nextOpenKeys = [];
    if (latestOpenKey) {
      nextOpenKeys = this.getAncestorKeys(latestOpenKey).concat(latestOpenKey);
    }
    if (latestCloseKey) {
      nextOpenKeys = this.getAncestorKeys(latestCloseKey);
    }
    this.setState({ openKeys: nextOpenKeys });
  }
  getAncestorKeys = (key) => {
    const map = {
      sub3: ['sub2'],
    };
    return map[key] || [];
  }

  render() {
    return (
      <Menu
        mode="inline"
        openKeys={this.state.openKeys}
        selectedKeys={[this.state.current]}
        style={{ width: 240 }}
        onOpenChange={this.onOpenChange}
        onClick={this.handleClick}
      >
        <SubMenu key="sub1" title={<span><Icon type="mail"/><span>---//</span></span>}>
          <Menu.Item key="Profile"><Link to="/profile">Profile</Link></Menu.Item>
          <Menu.Item key="Logout">Logout</Menu.Item>
        </SubMenu>
      </Menu>
    );
  }
}
