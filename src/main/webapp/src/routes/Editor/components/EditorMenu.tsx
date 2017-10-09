import * as React from 'react'
import { Menu, Icon } from 'antd'

const Item = Menu.Item
const SubMenu = Menu.SubMenu

export interface EditorMenuProps {
  selectEditor(name: string): void
}

export default class EditorMenu extends React.Component<EditorMenuProps, {}> {
  public render() {
    return (
      <Menu
        onSelect={({ key }) => this.props.selectEditor(key)}
        defaultSelectedKeys={['1']}
        defaultOpenKeys={['sub1']}
        mode="inline"
        theme="dark"
        inlineCollapsed={true}>
        <Menu.Item key="Entry">
          <Icon type="book"/>
          <span>Entries</span>
        </Menu.Item>
        <Menu.Item key="Abstraction">
          <Icon type="share-alt"/>
          <span>Abstractions</span>
        </Menu.Item>
        <Menu.Item key="AbstractionType">
          <Icon type="cloud"/>
          <span>Types</span>
        </Menu.Item>
      </Menu>
    )
  }
}

