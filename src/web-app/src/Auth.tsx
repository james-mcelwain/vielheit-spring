import React, {Component} from 'react';
import {State} from "./State";
import {observer} from "mobx-react";
import Login from './Login'

@observer
export default class Auth extends Component<{ state: State }> {

    render(): React.ReactNode {
        const { loggedIn } = this.props.state;

        if (loggedIn) {
            return <div>
                {JSON.stringify(this.props.state.user)}
            </div>
        } else {
            return <div>
                <Login state={this.props.state}/>
            </div>
        }
    }
}