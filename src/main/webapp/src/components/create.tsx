import * as React from 'react'
import {Button, Modal, Collapse} from 'antd'
import {getWidth} from 'dom'

const Panel = Collapse.Panel

interface CreateState {
  visible: boolean
  dirty: boolean
}

export default class Create extends React.Component<{}, CreateState> {
  public constructor() {
    super()
    this.setState({
      visible: false,
    })
  }

  public cancel() {
    this.setState({
      visible: false,
    })
  }

  public render() {
    const isVisible = this.state && this.state.visible
    const isDirty = this.state && this.state.dirty

    const cancel = () => this.cancel()
    const toggle = () => this.setState({visible: !isVisible})

    return <div>
      <Button onClick={toggle}>Create</Button>
      <Modal
        title={null}
        width={getWidth() * 0.8}
        closable={false}
        onCancel={cancel}
        okText="ok"
        cancelText="cancel"
        visible={isVisible}>

        <Collapse accordion={true} bordered={false} defaultActiveKey={['3']}>
          <Panel header="Abstraction Type" key="1">
          </Panel>
          <Panel header="Abstraction" key="2">
          </Panel>
          <Panel header="Entry" key="3">
          </Panel>
        </Collapse>
      </Modal>
    </div>
  }
}
