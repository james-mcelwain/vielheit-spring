import * as React from 'react'
import {Component, ComponentLifecycle} from 'react'
import { Provider } from 'react-redux'
import {browserHistory, PlainRoute, Router} from 'react-router'
import {AppStore} from '../store/store'

class AppContainer extends Component<{ store: AppStore, routes: PlainRoute }, {}> implements ComponentLifecycle<any, any> {
  shouldComponentUpdate() {
    return false
  }

  render() {
    const { routes, store } = this.props
    return (
      <Provider store={store}>
        <div style={{ height: '100%' }}>
          <Router history={browserHistory} children={routes} />
        </div>
      </Provider>
    )
  }
}

export default AppContainer
