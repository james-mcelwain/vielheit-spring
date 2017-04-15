import { Icon, Menu } from 'antd';
import * as React from 'react'
import { Link } from 'react-router'
import { store } from 'main'
import Application from 'core/Application'

const SubMenu = Menu.SubMenu;

export default class Sider extends React.Component<{}, { openKeys: string[], current: string }> {
  public state: {
    current: string,
    openKeys: string[],
  } = {
    current: '1',
    openKeys: [],
  }

  public render() {
    return (
      <Menu
        mode="inline"
        openKeys={this.state.openKeys}
        selectedKeys={[this.state.current]}
        style={{ width: 240 }}
        onOpenChange={this.onOpenChange}
        onClick={this.handleClick}
      >
        <SubMenu key="sub1" title={<span><Icon type="mail"/></span>}>
          <Menu.Item key="Editor"><Link to="/editor">Editor</Link></Menu.Item>
          <Menu.Item key="Profile"><Link to="/profile">Profile</Link></Menu.Item>
          <Menu.Item key="Logout">Logout</Menu.Item>
        </SubMenu>
      </Menu>
    );
  }

  private handleClick = (e: any) => {
    switch (e.key) {
      case 'Logout':
        store.dispatch(Application.LOGOUT.dispatch())
        sessionStorage.clear()
        // browserHistory.go('/login')
        break
      case 'Profile':
        break
      case 'Editor':
        break
      default:
        throw new Error('NO KEY')
    }

    this.setState({ current: e.key });
  }

  private onOpenChange = (openKeys: string[]) => {
    const state = this.state;
    const latestOpenKey = openKeys.find((key: string) => !state.openKeys.includes(key));
    const latestCloseKey = state.openKeys.find((key) => !(openKeys.includes(key)));

    let nextOpenKeys: string[] = [];
    if (latestOpenKey) {
      nextOpenKeys = this.getAncestorKeys(latestOpenKey).concat(latestOpenKey);
    }
    if (latestCloseKey) {
      nextOpenKeys = this.getAncestorKeys(latestCloseKey);
    }
    this.setState({ openKeys: nextOpenKeys });
  }

  private getAncestorKeys = (key: string) => {
    const map: { [key: string]: string[] } = {
      sub3: ['sub2'],
    };
    return map[key] || [];
  }
}
