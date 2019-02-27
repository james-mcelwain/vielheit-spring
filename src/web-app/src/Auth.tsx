import React, {Component} from 'react';
import {AppState} from "./AppState";
import {observer} from "mobx-react";
import Login from './Login'

@observer
export default class Auth extends Component<{ appState: AppState }> {

    render(): React.ReactNode {
        const { loggedIn } = this.props.appState;

        if (loggedIn) {
            return <div>
                Logged in!
            </div>
        } else {
            return <div>
                <Login state={this.props.appState}/>
            </div>
        }
    }
}