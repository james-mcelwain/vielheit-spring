import React, {Component} from 'react';
import {AppState} from "./App";
import {observer} from "mobx-react";

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
                logged out :(
            </div>
        }
    }
}