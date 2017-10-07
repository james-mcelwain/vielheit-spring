import * as React from 'react'
import './Editor.scss'
import { Layout, Card, Menu, Row, Col, Icon } from 'antd'
import { EditorProps } from '../containers/EditorContainer'
import EditorMenu from './EditorMenu'
import AbstractionTypeForm from './AbstractionTypeForm'

export const Editor = (props: EditorProps) => {
  const selectedItem = props.editorState.selectedItem + ""

  return (
    <div className="editor-container">
      <Layout>
        <Layout.Sider width="0%">
          <EditorMenu/>
        </Layout.Sider>
        <Layout.Content>
          <Row>
            <Col span={2}>
              <Menu selectedKeys={[selectedItem]}
                    onClick={({key}) => props.editor.selectItem(+key)}
                    defaultSelectedKeys={["0"]}>
                <Menu.Item key={0}>
                  <Icon type="plus"/>
                  <span>New</span>
                </Menu.Item>
                {props.editorState.types.map((t, i) => <Menu.Item key={i + 1}><span>{t.type}</span></Menu.Item>)}
              </Menu>
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
}

export default Editor
