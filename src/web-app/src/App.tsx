import React, {Component} from 'react';
import Auth from './Auth'
import './App.css';
import 'antd/dist/antd.css';
import {observer} from "mobx-react";
import {State} from "./State"; // or 'antd/dist/antd.less'

@observer
class App extends Component<{ state: State }> {
    render() {
        return (
            <div className="App">
                <Auth {...this.props}/>
            </div>
        );
    }
}

export default App;
