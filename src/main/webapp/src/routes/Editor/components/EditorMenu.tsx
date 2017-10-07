import * as React from 'react'
import { Menu, Icon } from 'antd'

const Item = Menu.Item
const SubMenu = Menu.SubMenu

export default () => (<Menu
  defaultSelectedKeys={['1']}
  defaultOpenKeys={['sub1']}
  mode="inline"
  theme="dark"
  inlineCollapsed={true}>
  <Menu.Item key="1">
    <Icon type="book" />
    <span>Entries</span>
  </Menu.Item>
  <Menu.Item key="2">
    <Icon type="cloud" />
    <span>Abstractions</span>
  </Menu.Item>
  <Menu.Item key="3">
    <Icon type="share-alt" />
    <span>Types</span>
  </Menu.Item>
</Menu>)
