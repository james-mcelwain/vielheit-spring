import * as React from 'react'
import { Row, Col, Menu, Card, Icon, Alert } from 'antd'
import AbstractionTypeForm from './AbstractionTypeForm'
import { AbstractionType } from '../modules/editor'
import { EditorProps } from '../containers/EditorContainer'

export interface AbstractionTypeEditorProps extends EditorProps {
  types: AbstractionType[]
}

export interface AbstractionTypeEditorState {
  selectedItemIdx: number
  selectedItem: AbstractionType
}

export default class AbstractionTypeEditor extends React.Component<AbstractionTypeEditorProps, AbstractionTypeEditorState> {
  public state: AbstractionTypeEditorState = {
    selectedItemIdx: 0,
    selectedItem: {
      type: '',
      description: '',
    },
  }

  public selectItem(idx: number) {
    this.setState({
      selectedItemIdx: idx,
      selectedItem: idx === -1 ? {type: '', description: '' } : this.props.types[idx],
    })
  }

  public render() {
    const submitAbstractionType = this.props.submitAbstractionType
    const error = this.props.editorState.error

    console.log(error)

    return (
      <Row>
        <Col span={2}>
          <Menu selectedKeys={[this.state.selectedItemIdx + '']}
                onClick={({key}) => this.selectItem(+key)}
                defaultSelectedKeys={['-1']}>
            <Menu.Item key={-1}>
              <Icon type="plus"/>
              <span>New</span>
            </Menu.Item>
            {this.props.editorState.types.map((t, i) => <Menu.Item key={i}><span>{t.type}</span></Menu.Item>)}
          </Menu>
        </Col>
        <Col span={22}>
          <Card title={<b>Abstraction Type</b>} noHovering={true}>
            {error && <Alert style={{ marginBottom: '20px' }} type="error" message={error}/>}
            <AbstractionTypeForm
              model={this.state.selectedItem}
              submitAbstractionType={submitAbstractionType}/>
          </Card>
        </Col>
      </Row>
    )
  }
}
