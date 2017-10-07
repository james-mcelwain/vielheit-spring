import * as React from 'react'
import { Row, Col, Alert } from 'antd'
import './Journal.scss'
import { JournalProps } from '../containers/JournalContainer'

export const Journal = (props: JournalProps) => (
  <div className="journal-container">
    <Row style={{width: '100%'}}>
      <Col span={12}>col-12</Col>
      <Col span={12}>{
        props.journalState.entries ? <Alert type="info" message="No entries!" description="You haven't created any journal entries, how about creating some now?"/> : <Alert type="error" message="ok"/>
      }</Col>
    </Row>
  </div>
)

export default Journal
