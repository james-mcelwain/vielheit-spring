import React from 'react';
import ReactDOM from 'react-dom';
import App, {AppState} from './App';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App appState={new AppState()}/>, div);
  ReactDOM.unmountComponentAtNode(div);
});
