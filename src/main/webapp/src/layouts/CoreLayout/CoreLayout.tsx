import * as React from 'react'
import '../../styles/core.scss'
import './CoreLayout.scss'
import {store} from 'main'
import {Layout, Menu, Breadcrumb, Button} from 'antd'
import {Link, browserHistory} from 'react-router'
const {Header, Footer, Content} = Layout

export const CoreLayout = ({children}: { children: JSX.Element[] }) => {
  const loggedIn = store.getState().application.loggedIn
  return (
    <Layout className="layout">
      <Header>
        <div className="logo"/>
        <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={['2']}
          style={{lineHeight: '64px'}}
        >
          <Menu.Item key="1">{loggedIn && <Link to="/editor">Editor</Link>}</Menu.Item>
          <Menu.Item key="2">{loggedIn && <Link to="/profile">Profile</Link>}</Menu.Item>
          <Menu.Item key="3">{loggedIn && <Link to="/journal">Journal</Link>}</Menu.Item>
          <Menu.Item key="99" style={{float: 'right'}}>{loggedIn && <Button onClick={() => {
            sessionStorage.clear()
            store.dispatch({
              type: 'LOGOUT',
            })
            browserHistory.push('/login')
          }} type="primary">Logout</Button>}</Menu.Item>
        </Menu>
      </Header>
      <Content style={{padding: '0 50px'}}>
        <Breadcrumb style={{margin: '12px 0'}}>
          {`vielheit${browserHistory.getCurrentLocation().pathname}`.split('/').map((path: string, i) => {
            return <Breadcrumb.Item key={i}>{path}</Breadcrumb.Item>
          })}
        </Breadcrumb>
        <div style={{background: '#fff', padding: 24, minHeight: 280}}>{children}</div>
      </Content>
      <Footer style={{textAlign: 'center'}}>
        the animal is in the world like water in water
      </Footer>
    </Layout>
  )
}

export default CoreLayout
