import React, {Component} from 'react';
import Auth from './Auth'
import './App.css';
import 'antd/dist/antd.css';
import {observer} from "mobx-react";
import {AppState} from "./AppState"; // or 'antd/dist/antd.less'

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
