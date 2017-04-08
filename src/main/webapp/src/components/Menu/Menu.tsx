import React, {SyntheticEvent, Component } from 'react'
import { Menu, Icon } from 'antd';
import { Link } from 'react-router'
import { LOGOUT } from '../../store/user'
import { store } from '../../main'

const SubMenu = Menu.SubMenu;

export default class Sider extends Component<{}, { openKeys: string[], current: string }> {
  state: {
    current: string,
    openKeys: string[]
  } = {
    current: '1',
    openKeys: [],
  }
  handleClick = (e: any) => {
    switch (e.key) {
      case "Logout":
        store.dispatch({
          type: LOGOUT
        })
        sessionStorage.clear()
        // browserHistory.go('/login')
        break
      case "Profile":
        break
      default:
        throw new Error("INVARIANT")
    }

    this.setState({ current: e.key });
  }
  onOpenChange = (openKeys: string[]) => {
    const state = this.state;
    const latestOpenKey = openKeys.find((key: string) => !state.openKeys.includes(key));
    const latestCloseKey = state.openKeys.find(key => !(openKeys.includes(key)));

    let nextOpenKeys: string[] = [];
    if (latestOpenKey) {
      nextOpenKeys = this.getAncestorKeys(latestOpenKey).concat(latestOpenKey);
    }
    if (latestCloseKey) {
      nextOpenKeys = this.getAncestorKeys(latestCloseKey);
    }
    this.setState({ openKeys: nextOpenKeys });
  }
  getAncestorKeys = (key: string) => {
    const map: { [key: string]: string[] } = {
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
          <Menu.Item key="Editor"><Link to="/editor">Editor</Link></Menu.Item>
          <Menu.Item key="Profile"><Link to="/profile">Profile</Link></Menu.Item>
          <Menu.Item key="Logout">Logout</Menu.Item>
        </SubMenu>
      </Menu>
    );
  }
}
