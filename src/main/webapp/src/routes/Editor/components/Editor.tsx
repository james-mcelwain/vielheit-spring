import * as React from 'react'
import './Editor.scss'
import { Layout, Card, Menu, Row, Col, Icon } from 'antd'
import { EditorProps } from '../containers/EditorContainer'
import EditorMenu from './EditorMenu'
import AbstractionTypeForm from './AbstractionTypeForm'

export const Editor = (props: EditorProps) => (
  <div className="editor-container">
    <Layout>
      <Layout.Sider width="0%">
        <EditorMenu/>
      </Layout.Sider>
      <Layout.Content>
        <Row>
          <Col span={2}>
            <Menu defaultSelectedKeys={['1']}>
              <Menu.Item key="1">
                <Icon type="plus"/>
                <span>New</span>
              </Menu.Item>
            </Menu>
            {props.editorState.types.map((t) => <div> ok </div>)}
          </Col>
          <Col span={22}>
            <Card title="Abstraction Type">
              <AbstractionTypeForm {...props}/>
            </Card>
          </Col>
        </Row>
      </Layout.Content>
    </Layout>
  </div>
)

export default Editor
