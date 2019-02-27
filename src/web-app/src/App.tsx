import React, {Component} from 'react';
import Auth from './Auth'
import './App.css';
import 'antd/dist/antd.css';
import {observer} from "mobx-react";
import {action, observable} from "mobx";
import http from "./http";

export class AppState {
    @observable
    public loggedIn = false;

    @action
    public async logIn(username: String, password: String) {
        try {
            const token = await http.post("/user/auth", {
                username,
                password
            })
        } catch(error) {

        }
    }
}

@observer
class App extends Component<{ appState: AppState }> {
    render() {
        return (
            <div className="App">
                <Auth {...this.props}/>
            </div>
        );
    }
}

export default App;
