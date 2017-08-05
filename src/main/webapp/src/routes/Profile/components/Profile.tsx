import * as React from 'react'
import './Profile.scss'
import {Input, Card, Row, Col, Button} from 'antd'
import {ApplicationState} from 'core/Application'
import {ProfileState} from '../modules/profile'

export class Profile extends React.Component<{ application: ApplicationState, profileState: ProfileState }, { isEdit: boolean }> {
  public state = {
    isEdit: false
  }

  public render() {
    const {user} = this.props.application

    return user && <div className="profile-container">
      <Card title={
        <Row type="flex" justify="space-between">
          <Col>User Profile</Col>
          <Col>
            {this.state.isEdit || <Button onClick={() => {
              this.setState({isEdit: true})
            }}>Edit</Button>}
          </Col>
        </Row>
      }>
        <div className="profile-info">
          <Input addonBefore="first name" value={user.firstName} disabled={!this.state.isEdit}/>
          <Input addonBefore="last name" value={user.lastName} disabled={!this.state.isEdit}/>
          <Input addonBefore="email" value={user.emailAddress} disabled={!this.state.isEdit}/>
          {this.state.isEdit && <Button type="primary">Save</Button>}
        </div>
      </Card>
    </div>
  }
}

export default Profile
